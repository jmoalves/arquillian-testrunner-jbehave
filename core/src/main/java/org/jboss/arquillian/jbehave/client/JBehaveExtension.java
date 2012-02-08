package org.jboss.arquillian.jbehave.client;

import org.jboss.arquillian.core.spi.LoadableExtension;

public class JBehaveExtension implements LoadableExtension
{

   @Override
   public void register(ExtensionBuilder builder)
   {
      builder.observer(ViewResourcesUnpacker.class);
   }

}
