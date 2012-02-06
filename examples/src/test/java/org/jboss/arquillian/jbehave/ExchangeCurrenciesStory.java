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
package org.jboss.arquillian.jbehave;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;

import javax.inject.Inject;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * A JBehave story, that uses the Arquillian JUnit test runner to execute the story.
 * 
 * @author Vineet Reynolds
 *
 */
@RunWith(Arquillian.class)
public class ExchangeCurrenciesStory extends JUnitStory
{
   
   @Inject
   private CurrencyExchangeService exchangeService;

   @Deployment
   public static JavaArchive createDeployment()
   {
      JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
            .addPackage("org.jboss.arquillian.jbehave")
            .addAsResource("org/jboss/arquillian/jbehave/exchange_currencies_story")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
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
            .useStoryPathResolver(new UnderscoredCamelCaseResolver(""))
            .useStoryReporterBuilder(new StoryReporterBuilder()
                  .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                  .withDefaultFormats()
                  .withFormats(CONSOLE, TXT, HTML)
                  .withFailureTrace(true));
      useConfiguration(configuration);
      addSteps(new InstanceStepsFactory(configuration, new ExchangeCurrenciesSteps(exchangeService)).createCandidateSteps());
   }

}