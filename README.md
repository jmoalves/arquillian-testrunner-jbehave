Arquillian JBehave TestRunner
=============================

## What is it?

The Arquillian JBehave TestRunner allows for in-container execution of your JBehave stories.
It enables developers to use Arquillian in Behavior-Driven Development
allowing for the possibility of using real objects in the execution of stories.   

## Example ##

### Write your story

    Story: Obtain quotes for exchanging currencies

    Scenario: Exchange United States Dollars for Japanese Yen

    When converting 5 USD to JPY
    Then return a quote of 375


    Scenario: Exchange United States Dollars for Euros

    When converting 130 USD to EUR
    Then return a quote of 100


    Scenario: Exchange United States Dollars for Pound Sterling

    When converting 15 USD to GBP
    Then return a quote of 10

### Map the Steps to Java

    public class ExchangeCurrenciesSteps
    {

       private BigDecimal result;

       @EJB
       private CurrencyExchangeService exchangeService; // Injected, by Arquillian 

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

### Configure the Story

    @RunWith(Arquillian.class)
    public class ExchangeCurrencies extends JUnitStory
    {
    
        @Deployment
        public static WebArchive createDeployment()
        {
            WebArchive archive = ShrinkWrap.create(WebArchive.class)
                .addPackage("org.jboss.arquillian.jbehave.domain")
                .addPackage("org.jboss.arquillian.jbehave.examples.container")
                .addAsResource("org/jboss/arquillian/jbehave/examples/container/exchange_currencies.story")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
            archive.addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class)
                .artifact("com.google.guava:guava:11.0.1")
                .resolveAs(JavaArchive.class));
            return archive;
        }
    
        public ExchangeCurrencies()
        {
             /* Configure JBehave to use the Guava SameThreadExecutorService.
                This enables the ArquillianInstanceStepsFactory to access
                the ThreadLocal contexts and datastores.
              */
            configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
        }
    
        @Override
        public Configuration configuration()
        {
            Configuration configuration = new MostUsefulConfiguration()
                .useStoryPathResolver(new UnderscoredCamelCaseResolver())
                .useStoryReporterBuilder(new StoryReporterBuilder()
                    .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                    .withDefaultFormats()
                    .withFormats(CONSOLE, TXT, HTML, XML)
                    .withFailureTrace(true));
            return configuration;
        }
    
        @Override
        public InjectableStepsFactory stepsFactory()
        {
            return new ArquillianInstanceStepsFactory(configuration(), new ExchangeCurrenciesSteps());
        }
    
    }

### Run it

Use Arquillian as usual. The story can be executed from your IDE or from a Maven build.

## More Info

[Arquillian](http://jboss.org/arquillian/)

[JBehave](http://jbehave.org/)