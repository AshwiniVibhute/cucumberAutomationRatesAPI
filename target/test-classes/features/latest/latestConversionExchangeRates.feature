Feature: Verify the different get operation for latest foreign exchange rates

  @RegressionTest
  Scenario: Get the latest foreign exchange reference rates.
    Given I have rates API "https://api.ratesapi.io/api" URL  
    When I hit API using HTTP get method with "/latest" as endpoint
    Then It should give 200 HTTP status code in response
    And Response should be in JSON format
    And It should give latest foreign exchange rates against base parameter "EUR" 
    And JSON response body contains the yesterday's date in "yyyy-MM-dd" format
    And Currency count should be equal to 32 

  @RegressionTest
  Scenario: Request specific exchange rates by setting the symbols parameter.
    Given I have rates API "https://api.ratesapi.io/api" URL
    When I hit API using HTTP get method with "/latest" as endpoint and query params as symbols equal to "USD,GBP" 
    Then It should give 200 HTTP status code in response
    And Response should be in JSON format
    And It should give latest foreign exchange rates for only "USD,GBP" against "EUR" 
    And JSON response body contains the yesterday's date in "yyyy-MM-dd" format 
    And Currency count should be equal to number of symbols 

  @RegressionTest
  Scenario: Rates are quoted against the Euro by default. Quote against a different currency by setting the base parameter in your request.
    Given I have rates API "https://api.ratesapi.io/api" URL
    When I hit API using HTTP get method with "/latest" as endpoint and query params base as "USD" 
    Then It should give 200 HTTP status code in response
    And Response should be in JSON format
    And It should give latest foreign exchange rates against base parameter "USD" 
    And JSON response body contains the yesterday's date in "yyyy-MM-dd" format
    And Currency count should be equal to 33

  @RegressionTest
  Scenario: Request specific exchange rates by setting the base parameter and symbols parameter in your request.
  	Given I have rates API "https://api.ratesapi.io/api" URL
    When I hit API using HTTP get method with "/latest" as endpoint and query params as symbols equal to "GBP" and base as "USD"  
    Then It should give 200 HTTP status code in response
    And Response should be in JSON format
    And It should give latest foreign exchange rates for only "GBP" against "USD" 
    And JSON response body contains the yesterday's date in "yyyy-MM-dd" format
    And Currency count should be equal to number of symbols 
