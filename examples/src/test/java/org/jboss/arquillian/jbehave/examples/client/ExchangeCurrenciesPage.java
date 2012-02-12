package org.jboss.arquillian.jbehave.examples.client;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Currency;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExchangeCurrenciesPage
{
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
      if (!driver.getCurrentUrl().equals(contextPath.toString())
            && !driver.getCurrentUrl().equals(contextPath + PAGE_NAME))
      {
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
