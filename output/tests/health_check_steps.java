package net.engineeringdigest.journalApp.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.engineeringdigest.journalApp.JournalAppApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot节.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@ SpringerBootTest
public class HealthCheckSteps {

  private static SpringApplication application;

  @Before
  public static void before() {
    application = new SpringApplication(JournalAppApplication.class);
    application.run();
  }

  @After
  public static void after() {
    application.close();
  }

  @Given("The application is running on {string}")
  public void theApplicationIsRunningOn(String baseUrl) {
    baseURI = baseUrl;
  }

  @When("I call the health check endpoint at {string}")
  public void iCallTheHealthCheckEndpointAt(String endpoint) {
    when().get(endpoint);
  }

  @Then("I should receive a status code of {int}")
  public void iShouldReceiveAStatusCodeOf(int expectedStatusCode) {
    then().statusCode(expectedStatusCode);
  }

  @Then("The response body should be {string}")
  public void theResponseBodyShouldBe(String expectedBody) {
    then().body(is(expectedBody));
  }

  @Given("The application is not running")
  public void theApplicationIsNotRunning() {
    // Stop the application
    application.close();
  }
}