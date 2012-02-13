/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.jbehave.examples.client;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.io.File;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.jbehave.core.ArquillianInstanceStepsFactory;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.google.common.util.concurrent.MoreExecutors;

/**
 * A JBehave story, that uses the Arquillian JUnit test runner to execute the story.
 * 
 * @author Vineet Reynolds
 *
 */
@RunWith(Arquillian.class)
@RunAsClient
public class ExchangeCurrencies extends JUnitStory
{
   
   //Hack for enabling @Drone injection into Steps.
   @Drone
   WebDriver driver;
   
   @Deployment
   public static WebArchive createDeployment()
   {
      WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage("org.jboss.arquillian.jbehave.domain")
            .addClass(ExchangeCurrenciesModel.class)
            .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
            .addAsWebResource(new File("src/main/webapp/exchangeCurrencies.xhtml"))
            .addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"))
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
      return archive;
   }

   /**
    * Setup the {@link Embedder}. This must execute before
    * the @Test annotated <code>run()</code> method of {@link JUnitStory} is executed. 
    */
   @Before
   public void setup()
   {
      Configuration configuration = new MostUsefulConfiguration()
            .useStoryPathResolver(new UnderscoredCamelCaseResolver())
            .useStoryReporterBuilder(new StoryReporterBuilder()
                  .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                  .withDefaultFormats()
                  .withFormats(CONSOLE, TXT, HTML, XML)
                  .withFailureTrace(true));
      useConfiguration(configuration);
      addSteps(new ArquillianInstanceStepsFactory(configuration, new ExchangeCurrenciesSteps()).createCandidateSteps());
      
      //configure JBehave to use the Guava SameThreadExecutorService.
      configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
   }

}