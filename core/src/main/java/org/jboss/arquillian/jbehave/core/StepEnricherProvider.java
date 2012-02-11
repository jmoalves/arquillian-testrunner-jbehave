package org.jboss.arquillian.jbehave.core;

import java.util.Collection;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.test.spi.TestEnricher;
import org.jboss.arquillian.test.spi.event.suite.Before;

public class StepEnricherProvider
{
   @Inject
   private Instance<ServiceLoader> serviceLoader;
   
   private static Collection<TestEnricher> enrichers;
   
   public void enrich(@Observes Before event) throws Exception
   {
      Collection<TestEnricher> testEnrichers = serviceLoader.get().all(TestEnricher.class);
      enrichers = testEnrichers;
   }
   
   public static Collection<TestEnricher> getEnrichers()
   {
      return enrichers;
   }
   
}
