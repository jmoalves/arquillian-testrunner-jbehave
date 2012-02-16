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

import java.math.BigDecimal;
import java.util.Currency;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.jbehave.domain.CurrencyExchangeService;

/**
 * The model class for the ExchangeCurrencies page.
 * 
 * @author Vineet Reynolds
 *
 */
@Named
@RequestScoped
public class ExchangeCurrenciesModel
{
   @Inject
   private CurrencyExchangeService exchangeService;

   String fromCurrencyCode;

   String toCurrencyCode;

   String amount;

   private String quote;

   public String getFromCurrencyCode()
   {
      return fromCurrencyCode;
   }

   public void setFromCurrencyCode(String fromCurrencyCode)
   {
      this.fromCurrencyCode = fromCurrencyCode;
   }

   public String getToCurrencyCode()
   {
      return toCurrencyCode;
   }

   public void setToCurrencyCode(String toCurrencyCode)
   {
      this.toCurrencyCode = toCurrencyCode;
   }

   public String getAmount()
   {
      return amount;
   }

   public void setAmount(String amount)
   {
      this.amount = amount;
   }

   public String getQuote()
   {
      return quote;
   }

   public void setQuote(String quote)
   {
      this.quote = quote;
   }

   public String obtainQuote()
   {
      quote = exchangeService.getQuote(Currency.getInstance(fromCurrencyCode), new BigDecimal(amount),
            Currency.getInstance(toCurrencyCode)).toPlainString();
      return "";
   }
}
