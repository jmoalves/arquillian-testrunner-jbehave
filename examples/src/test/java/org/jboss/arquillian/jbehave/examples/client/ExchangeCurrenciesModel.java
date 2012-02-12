package org.jboss.arquillian.jbehave.examples.client;

import java.math.BigDecimal;
import java.util.Currency;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.jbehave.domain.CurrencyExchangeService;

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
