package stepDefinitions.past;

import java.util.LinkedHashMap;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PastConverstionExchangeRates {

	private static Response response;
	private static int currencyCount;

	@Given("I have rates API {string} URL")
	public void i_have_rates_API_URL(String string) {
		RestAssured.baseURI = string;
	}

	@When("I hit API using HTTP get method with past date {string} as endpoint")
	public void i_hit_API_using_HTTP_get_method_with_as_endpoint(String string) {
		RequestSpecification request = RestAssured.given();
		response = request.get(string);
	}

	@Then("It should give {int} HTTP status code in response")
	public void it_should_give_HTTP_status_code_in_response(Integer int1) {
		Assert.assertEquals((int) int1, response.getStatusCode());
	}

	@Then("Response should be in JSON format")
	public void response_should_be_in_JSON_format() {
		String contentType = response.header("Content-Type");
		Assert.assertEquals("application/json", contentType);
	}

	@Then("It should give latest foreign exchange rates against {string}")
	public void it_should_give_latest_foreign_exchange_rates_against(String string) {
		JsonPath jsonObj = response.jsonPath();
		Assert.assertEquals(string, jsonObj.get("base"));
	}

	@Then("JSON response body contains {string} date in {string} format")
	public void json_response_body_contains_date_in_format(String string, String string2) {
		JsonPath jsonObj = response.jsonPath();
		Object dateObj = jsonObj.get("date");
		Assert.assertEquals(true, dateObj != null);
		Assert.assertEquals(string, dateObj.toString());
	}

	@Then("Currency count should be equal to {int}")
	public void currency_count_should_be_equal_to(Integer int1) {
		JsonPath jsonObj = response.jsonPath();
		LinkedHashMap<String, String> rates = (LinkedHashMap<String, String>) jsonObj.get("rates");
		Assert.assertEquals((int) int1, rates.size());
	}

	@When("I hit API using HTTP get method with past date {string} as endpoint and query params as symbols equal to {string}")
	public void i_hit_API_using_HTTP_get_method_with_as_endpoint_and_query_params_as_symbols_equal_to(String string,
			String string2) {
		RequestSpecification request = RestAssured.given();
		response = request.queryParam("symbols", string2).get(string);
		String[] currencies = string2.split(",");
		currencyCount = currencies.length;
	}

	@Then("Currency count should be equal to number of symbols")
	public void currency_count_should_be_equal_to_number_of_symbols() {
		JsonPath jsonObj = response.jsonPath();
		LinkedHashMap<String, String> rates = (LinkedHashMap<String, String>) jsonObj.get("rates");
		Assert.assertEquals(currencyCount, rates.size());
	}

	@When("I hit API using HTTP get method with past date {string} as endpoint and query params base as {string}")
	public void i_hit_API_using_HTTP_get_method_with_as_endpoint_and_query_params_base_as(String string,
			String string2) {
		RequestSpecification request = RestAssured.given();
		response = request.queryParam("base", string2).get(string);
	}

	@When("I hit API using HTTP get method with past date {string} as endpoint and query params as symbols equal to {string} and base as {string}")
	public void i_hit_API_using_HTTP_get_method_with_as_endpoint_and_query_params_as_symbols_equal_to_and_base_as(
			String string, String string2, String string3) {
		RequestSpecification request = RestAssured.given();
		response = request.queryParam("base", string3).queryParam("symbols", string2).get(string);
		String[] currencies = string2.split(",");
		currencyCount = currencies.length;
	}

	@Then("It should give latest foreign exchange rates against base parameter {string}")
	public void it_should_give_latest_foreign_exchange_rates_against_base_parameter(String string) {
		JsonPath jsonObj = response.jsonPath();
		String base = jsonObj.get("base").toString();
		Assert.assertEquals(string, base);
	}

	@Then("It should give latest foreign exchange rates for only {string} against {string}")
	public void it_should_give_latest_foreign_exchange_rates_for_only_against(String string, String string2) {
		JsonPath jsonObj = response.jsonPath();
		LinkedHashMap<String, String> rates = (LinkedHashMap<String, String>) jsonObj.get("rates");
		String base = jsonObj.get("base").toString();
		if (string.contains(",")) {
			String[] symbols = string.split(",");
			Assert.assertEquals(rates.size(), symbols.length);
		} else {
			Assert.assertEquals(rates.size(), 1);
			Assert.assertEquals(string2, base);
		}
	}

}
