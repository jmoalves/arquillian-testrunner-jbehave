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
import java.net.URL;
import java.util.Currency;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The class representing the ExchangeCurrencies page in the browser. 
 * Implements the Selenium/WebDriver Page object pattern.
 * 
 * @author Vineet Reynolds
 *
 */
public class ExchangeCurrenciesPage
{
	
   private static final Logger LOG = Logger.getLogger(ExchangeCurrenciesPage.class.getName());
   
   public static class PageUtilities
   {
      public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator)
      {
         return new ExpectedCondition<WebElement>()
         {
            public WebElement apply(WebDriver driver)
            {
               WebElement toReturn = driver.findElement(locator);
               if (toReturn.isDisplayed())
               {
                  return toReturn;
               }
               return null;
            }
         };
      }
   }

   private WebDriver driver;

   private URL contextPath;

   private static final String PAGE_NAME = "exchangeCurrencies.xhtml";

   public ExchangeCurrenciesPage(WebDriver driver, URL contextRoot)
   {
      this.driver = driver;
      this.contextPath = contextRoot;
      Wait<WebDriver> wait = new WebDriverWait(driver, 15);
      wait.until(PageUtilities.visibilityOfElementLocated(By.id("inputForm")));
      String currentUrl = driver.getCurrentUrl();
      currentUrl = currentUrl.replaceAll(";jsessionid=[-a-zA-Z._0-9]+", ""); // get rid of jsessionid, if present
	if (!currentUrl.equals(contextPath.toString())
            && !currentUrl.equals(contextPath + PAGE_NAME))
      {
         LOG.severe("Expected: " + contextPath + " or " + contextPath + PAGE_NAME + " but we got " + currentUrl);
         throw new IllegalStateException("This is not the Index page.");
      }
   }

   public ExchangeCurrenciesPage submitDetailsForQuote(Currency fromCurrency, BigDecimal amount, Currency toCurrency)
   {
      WebElement fromCurrencyInputBox = driver.findElement(By.id("inputForm:fromCurrency"));
      WebElement toCurrencyInputBox = driver.findElement(By.id("inputForm:toCurrency"));
      WebElement amountInputBox = driver.findElement(By.id("inputForm:amount"));
      WebElement submitButton = driver.findElement(By.id("inputForm:obtainQuoteAction"));

      fromCurrencyInputBox.sendKeys(fromCurrency.getCurrencyCode());
      toCurrencyInputBox.sendKeys(toCurrency.getCurrencyCode());
      amountInputBox.sendKeys(amount.toPlainString());
      submitButton.click();
      return new ExchangeCurrenciesPage(driver, contextPath);
   }

   public BigDecimal getQuoteFromPage()
   {
      Wait<WebDriver> wait = new WebDriverWait(driver, 15);
      wait.until(PageUtilities.visibilityOfElementLocated(By.id("inputForm:quote")));
      WebElement quote = driver.findElement(By.id("inputForm:quote"));
      return new BigDecimal(quote.getText());
   }

}
