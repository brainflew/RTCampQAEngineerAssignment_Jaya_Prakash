package rtCampQAPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RtCampQALoginPage {

	WebDriver driver;
	
//	using xpaths instead of id for consistency
	@FindBy(xpath="//input[@id='user-name']")
	private WebElement userName;
	
	@FindBy(xpath="//input[@id='password']")
	private WebElement password;
	
	@FindBy(xpath="//input[@id='login-button']")
	private WebElement loginButton;
	
	@FindBy(xpath = "//div[@id='login_credentials']")
	private static List<WebElement> acceptedUserNames;
	
	@FindBy(xpath = "//div[@class='login_password']")
	private static List<WebElement> acceptedPasswords;
	
	
	
	public RtCampQALoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public static String getStandardUserName() {
		String userName = "";
		
		for(WebElement acceptedUserName: acceptedUserNames){
			
			String usernames = acceptedUserName.getText();
			String[] splitUsernames = usernames.split("are:");
			String[] splitUsernamesFinal = splitUsernames[1].split("locked");
			
			for (String splitUsername:splitUsernamesFinal) {
//				System.out.println(splitUsername);
				if(splitUsername.contains("standard_user")) {
					System.out.println(splitUsername);
					userName = splitUsername;
					break;
				}
			}			
		}		
		return userName;
		
	}
	
	public static String getStandardPassword() {
		
		String passWord = "";
		for(WebElement acceptedPassword: acceptedPasswords){
			String passwords = acceptedPassword.getText();
			String[] splitpasswords = passwords.split(":");
			for (String splitPassword:splitpasswords) {
				if(splitPassword.contains("secret_sauce")) {
					passWord = splitPassword;
				}
			}
			
		}		
		return passWord;
		
	}
	
	public void login() {
		String standardUserName = getStandardUserName();
		String passWord = getStandardPassword();
		System.out.println(standardUserName);
		userName.sendKeys(standardUserName);
		password.sendKeys(passWord);
		loginButton.click();
	}	
}
