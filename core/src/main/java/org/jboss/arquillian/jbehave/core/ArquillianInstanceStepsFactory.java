package org.jboss.arquillian.jbehave.core;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jboss.arquillian.test.spi.TestEnricher;

public class ArquillianInstanceStepsFactory extends InstanceStepsFactory
{

   public ArquillianInstanceStepsFactory(Configuration configuration, List<Object> stepsInstances)
   {
      super(configuration, enrich(stepsInstances));
   }

   public ArquillianInstanceStepsFactory(Configuration configuration, Object... stepsInstances)
   {
      super(configuration, enrich(asList(stepsInstances)));
   }

   /**
    * We've got to enrich the instances when creating the {@link InstanceStepsFactory}
    * and not when invoking the <code>org.jbehave.core.steps.InstanceStepsFactory.createInstanceOfType(Class<?>)</code>
    * as a new thread is supposedly created to execute the stories.
    * 
    * Creating new threads eventually erases the contexts used by the Arquillian injectors, resuling in failed enrichment.  
    * 
    * @param stepsInstances The list of step instances to enrich
    * @return The list of enrich instances
    */
   private static List<Object> enrich(List<Object> stepsInstances)
   {
      for (Object stepInstance : stepsInstances)
      {
         Collection<TestEnricher> stepEnrichers = StepEnricherProvider.getEnrichers();
         for (TestEnricher stepEnricher : stepEnrichers)
         {
            stepEnricher.enrich(stepInstance);
         }
      }
      return stepsInstances;
   }

}
