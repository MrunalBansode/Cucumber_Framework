package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) 
	{
		super(driver);
    }
	
	@FindBy(xpath = "//input[@id='input-email']") WebElement txt_Email;
	
	@FindBy(xpath = "//input[@id='input-password']") WebElement txt_Password;
	
	@FindBy(xpath = "//input[@value='Login']") WebElement btn_login;
	
	public void setEmailadd(String email) {
		txt_Email.sendKeys(email);
		}
	
	public void setPassword(String password) {
		txt_Password.sendKeys(password);
	}
	
	public void clicklogin() {
		btn_login.click();
	}
	
	

}
