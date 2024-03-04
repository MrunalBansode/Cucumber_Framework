
package stepDefinitions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Assert;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

public class Steps {
	WebDriver driver;
	HomePage hp;
	LoginPage lp;
	MyAccountPage macc;
	
	List<HashMap<String, String>> datamap; //Data driver
	
	Logger logger;
	ResourceBundle rb;
	String br;
	
	@Before
	public void setup()
	{
	
		logger = LogManager.getLogger(this.getClass());  //logging
		rb = ResourceBundle.getBundle("config");  //load config.properties file
		br = rb.getString("browser");
	//	macc = new MyAccountPage(driver);
		
	}

	@After
	public void tearDown(Scenario scenario) {
		System.out.println("Scenario status ====> "+ scenario.getStatus() );
		if(scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
		}
		driver.quit();
	}
	
	@Given("User Launch browser")
	public void user_launch_browser() {
		if(br.equals("chrome"))
		{
			driver= new ChromeDriver();
		}
		else if (br.equals("edge"))
		{
			driver = new EdgeDriver();
		}
		else if (br.equals("firefox"))
		{
			driver = new FirefoxDriver();
		}
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}

	@Given("opens URL {string}")
	public void opens_url(String url) {
	    driver.get(url);
	    driver.manage().window().maximize();
	}

	@When("User navigate to MyAccount menu")
	public void user_navigate_to_my_account_menu() {
	    hp = new HomePage(driver);
	    hp.clickMyAccount();
	    logger.info("Clicked on My account");
	}

	@When("click on login")
	public void click_on_login() {
	   hp.clickLogin();
	   logger.info("Clicked on Login");
	}
	
	@When("User enter Email as {string} and Password as {string}")
	public void user_enter_email_as_and_password_as(String email, String password) {
	   lp = new LoginPage(driver);
	   
	lp.setEmailadd(email);
	   logger.info("Provided Email");
	   lp.setPassword(password);
	   logger.info("Provided password");
	}

	@When("Click on Login button")
	public void click_on_login_button() {
		lp.clicklogin();
		logger.info("Clicked on Login button");
	}

	@Then("User navigates to MyAccount Page")
	public void user_navigates_to_my_account_page() {
		macc = new MyAccountPage(driver);
		boolean targetpage = macc.isMyAccountPageExists();
		if(targetpage)
		{
			logger.info("Login successful");
		}
		else
		{
			logger.error("Login Failed");
			Assert.assertTrue(false);
		}	
	
	}	
		
	
		 //*******   Data Driven test method    **************

		@Then("check User navigates to MyAccount Page by passing Email and Password with excel row {string}")
		public void check_user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_row(String rows)
		{
	        datamap=DataReader.data(System.getProperty("user.dir")+"\\testData\\opencart_Login_data.xlsx", "Sheet1");

	        int index=Integer.parseInt(rows)-1;
	        String email= datamap.get(index).get("email");
	        String pwd= datamap.get(index).get("password");
	        String exp_res= datamap.get(index).get("result");

	        lp=new LoginPage(driver);
	        lp.setEmailadd(email);
	        lp.setPassword(pwd);

	        lp.clicklogin();
	        try
	        {
	            boolean targetpage=macc.isMyAccountPageExists();

	            if(exp_res.equals("Valid"))
	            {
	                if(targetpage==true)
	                {
	                    MyAccountPage myaccpage=new MyAccountPage(driver);
	                    myaccpage.clickLogout();
	                    Assert.assertTrue(true);
	                }
	                else
	                {
	                    Assert.assertTrue(false);
	                }
	            }

	            if(exp_res.equals("Invalid"))
	            {
	                if(targetpage==true)
	                {
	                    macc.clickLogout();
	                    Assert.assertTrue(false);
	                }
	                else
	                {
	                    Assert.assertTrue(true);
	                }
	            }


	        }
	        catch(Exception e)
	        {

	            Assert.assertTrue(false);
	        }
	        driver.close();
	    }

	}

