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
package org.jboss.arquillian.jbehave.examples.container;

import java.math.BigDecimal;
import java.util.Currency;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jboss.arquillian.jbehave.domain.CurrencyExchangeService;

/**
 * The annotated steps for the {@link ExchangeCurrenciesInContainer} story.
 * 
 * @author Vineet Reynolds
 *
 */
public class ExchangeInContainerCurrenciesSteps
{
   private BigDecimal result;

   @EJB
   private CurrencyExchangeService exchangeService;

   public ExchangeInContainerCurrenciesSteps()
   {

   }

   @When("converting $amount $fromCurrencyCode to $toCurrencyCode")
   public void obtainQuote(BigDecimal amount, String fromCurrencyCode, String toCurrencyCode)
   {
      Currency fromCurrency = Currency.getInstance(fromCurrencyCode);
      Currency toCurrency = Currency.getInstance(toCurrencyCode);
      result = exchangeService.getQuote(fromCurrency, amount, toCurrency);
   }

   @Then("return a quote of $quote")
   public void theQuoteShouldBe(String quote)
   {
      Assert.assertEquals(0, result.compareTo(new BigDecimal(quote)));
   }
}
