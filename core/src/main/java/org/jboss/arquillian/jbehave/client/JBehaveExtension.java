package org.jboss.arquillian.jbehave.client;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.jbehave.core.StepEnricherProvider;

public class JBehaveExtension implements LoadableExtension
{

   @Override
   public void register(ExtensionBuilder builder)
   {
      builder.service(AuxiliaryArchiveAppender.class, JBehaveCoreDeploymentAppender.class)
             .observer(ViewResourcesUnpacker.class)
             .observer(StepEnricherProvider.class);
   }

}
