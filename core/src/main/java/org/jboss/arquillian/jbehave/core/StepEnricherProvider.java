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

   private static final ThreadLocal<Collection<TestEnricher>> enrichers = new ThreadLocal<Collection<TestEnricher>>();

   /**
    * Observe the {@link Before} event to obtain references to the {@link TestEnricher} instances.
    * Once the enrichers have been obtained, they're stored in a ThreadLocal instance for future reference.
    * @param event The event to observe
    * @throws Exception The exception thrown by the observer on failure.
    */
   public void enrich(@Observes Before event) throws Exception
   {
      Collection<TestEnricher> testEnrichers = serviceLoader.get().all(TestEnricher.class);
      enrichers.set(testEnrichers);
   }

   public static Collection<TestEnricher> getEnrichers()
   {
      return enrichers.get();
   }

}
