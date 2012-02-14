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
      super(configuration, stepsInstances);
   }

   public ArquillianInstanceStepsFactory(Configuration configuration, Object... stepsInstances)
   {
      super(configuration, asList(stepsInstances));
   }
   
   @Override
   public Object createInstanceOfType(Class<?> type)
   {
      Object instance = super.createInstanceOfType(type);
      Collection<TestEnricher> stepEnrichers = StepEnricherProvider.getEnrichers();
      for (TestEnricher stepEnricher : stepEnrichers)
      {
         stepEnricher.enrich(instance);
      }
      return instance;
   }

}
