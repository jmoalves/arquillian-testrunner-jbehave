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
package org.jboss.arquillian.jbehave.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 * An implementation of the {@link CurrencyExchangeService}
 * that uses a static set of exchange rates.
 * 
 * @author Vineet Reynolds
 *
 */
@Named("exchangeService")
@Stateless
@Local(CurrencyExchangeService.class)
public class StaticCurrencyExchangeService implements CurrencyExchangeService
{
   
   static Map<String, BigDecimal> rates;
   
   static
   {
      rates = new HashMap<String, BigDecimal>();
      rates.put("EURUSD", new BigDecimal("1.3"));
      rates.put("GBPUSD", new BigDecimal("1.5"));
      rates.put("USDJPY", new BigDecimal("75"));
   }

   @Override
   public BigDecimal getQuote(Currency fromCurrency, BigDecimal amount, Currency toCurrency)
   {
      String exchangePair = fromCurrency.getCurrencyCode() + toCurrency.getCurrencyCode();
      BigDecimal exchangeRate = rates.get(exchangePair);
      if(exchangeRate == null)
      {
         String reverseExchangePair = toCurrency.getCurrencyCode() + fromCurrency.getCurrencyCode();
         exchangeRate = rates.get(reverseExchangePair);
         return amount.divide(exchangeRate, 5, RoundingMode.HALF_EVEN);
      }
      return exchangeRate.multiply(amount);
   }

}
