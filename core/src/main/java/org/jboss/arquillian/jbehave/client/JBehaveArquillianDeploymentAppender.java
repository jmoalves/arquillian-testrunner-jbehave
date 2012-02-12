package org.jboss.arquillian.jbehave.client;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.jbehave.container.JBehaveContainerExtension;
import org.jboss.arquillian.jbehave.core.ArquillianInstanceStepsFactory;
import org.jboss.arquillian.jbehave.core.StepEnricherProvider;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class JBehaveArquillianDeploymentAppender implements AuxiliaryArchiveAppender
{

   @Override
   public Archive<?> createAuxiliaryArchive()
   {
      JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "arquillian-jbehave-helper.jar");
      archive.addClasses(JBehaveContainerExtension.class, ArquillianInstanceStepsFactory.class, StepEnricherProvider.class);
      archive.addAsServiceProvider(RemoteLoadableExtension.class, JBehaveContainerExtension.class);
      archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
      return archive;
   }

}
