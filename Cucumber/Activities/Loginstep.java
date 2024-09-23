package stepdefinitions;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Loginstep extends baseclass{
	
	@BeforeAll
	// before/after class cannot be inherited
	public static void setup() {

		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		wait=new WebDriverWait(driver, Duration.ofSeconds(100));
	}

	@AfterAll 

	public static void close() {
		driver.close();
	}

	@Given("the user is in login page")

	public void openpage() {
		driver.get("https://v1.training-support.net/selenium/login-form");
		
		
		Assertions.assertEquals("Login Form", driver.getTitle());
	}

	@When("the user provides username and password")
	public void Entercredentials() {

		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("password");
	}
		
		
@When("the user provides {string} and {string}")

		public void Credentialsfile (String username, String password) {

			WebElement usernamefield= driver.findElement(By.id("username"));
			WebElement passwordfield= driver.findElement(By.id("password"));
			usernamefield.clear();
			passwordfield.clear();
			
			usernamefield.sendKeys("admin");
			passwordfield.sendKeys("password");
}

 @And("clicks the submit button")

     public void submitbutton() {
	   driver.findElement(By.xpath("//button[@type='submit']")).click();
	
	}

	@Then("user gets confirmation message and expected text")

	public void confirmmessage() {

 String confirmMessage = driver.findElement(By.id("action-confirmation")).getText();

        System.out.println("Login message is: " + confirmMessage);
 
        Assertions.assertEquals(confirmMessage, "Welcome Back, admin");
	}
	
	@Then("ser gets confirmation message and expected text is {String}")

	public void confirmmessagewithargu(String expected) {

 String confirmMessage = driver.findElement(By.id("action-confirmation")).getText();

        System.out.println("Login message is: " + confirmMessage);
 
        Assertions.assertEquals(expected, confirmMessage);
	}
	
	
	
}


