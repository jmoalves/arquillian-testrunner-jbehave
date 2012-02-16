Story: Obtain quotes for exchanging currencies

Scenario: Exchange United States Dollars for Japanese Yen

Given A user is at the Exchange Currencies Page
When converting 5 USD to JPY
Then return a quote of 375


Scenario: Exchange United States Dollars for Euros

Given A user is at the Exchange Currencies Page
When converting 130 USD to EUR
Then return a quote of 100


Scenario: Exchange United States Dollars for Pound Sterling

Given A user is at the Exchange Currencies Page
When converting 15 USD to GBP
Then return a quote of 10
