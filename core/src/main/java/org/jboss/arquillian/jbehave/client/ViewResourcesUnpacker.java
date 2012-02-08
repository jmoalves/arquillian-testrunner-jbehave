package org.jboss.arquillian.jbehave.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.StrictFilter;

public class ViewResourcesUnpacker
{
   private String targetDirectory = "target/jbehave/view";
   
   public void extractResources(@Observes BeforeSuite event)
   {
      File[] siteResources = DependencyResolvers.use(MavenDependencyResolver.class)
         .loadMetadataFromPom("pom.xml")
         .artifact("org.jbehave.site:jbehave-site-resources:zip:3.1.1")
         .artifact("org.jbehave:jbehave-core:zip:resources:3.5.4")
         .resolveAsFiles(new StrictFilter());
      File destination = new File(targetDirectory);
      for(File resource: siteResources)
      {
         try
         {
            unpack(resource, destination);
         }
         catch (ZipException zipEx)
         {
            throw new RuntimeException(zipEx);
         }
         catch (IOException ioEx)
         {
            throw new RuntimeException(ioEx);
         }
      }
      
   }

   private void unpack(File resource, File destination) throws ZipException, IOException
   {
      destination.mkdirs();
      
      ZipFile zip = new ZipFile(resource);
      Enumeration<? extends ZipEntry> entries = zip.entries();
      while(entries.hasMoreElements())
      {
         ZipEntry currentEntry = entries.nextElement();
         File entryFile = new File(destination, currentEntry.getName());
         File parentFile = entryFile.getParentFile();
         parentFile.mkdirs();
         
         if(currentEntry.isDirectory())
         {
            entryFile.mkdirs();
         }
         else
         {
            BufferedInputStream bis = new BufferedInputStream(zip.getInputStream(currentEntry));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(entryFile));
            
            byte[] buffer = new byte[1024];
            int readBytes;
            while((readBytes = bis.read(buffer)) != -1)
            {
               out.write(buffer, 0 , readBytes);
            }
            
            bis.close();
            out.close();
         }
      }
   }

}
