package rtCampQAPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy; 
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class RtCampQACheckOutPage{


	WebDriver driver;

	@FindBy(xpath = "//input[@id='first-name']")
	static WebElement firstName;

	@FindBy(xpath = "//input[@id='last-name']")
	static WebElement lastName;

	@FindBy(xpath = "//input[@id='postal-code']")
	static WebElement postalCode;
	
	@FindBy(xpath = "//input[@id='continue']")
	static WebElement nextPage;
	
	@FindBy(xpath = "//button[@id='finish']")
	static WebElement finish;
	
	@FindBy(xpath = "//div[@class='summary_subtotal_label']")
	static WebElement totalPriceBeforeTax;

	@FindBy(xpath = "//h2")
	static WebElement checkoutCompleteHeader;
	
	@FindBy(xpath = "//span[@class='title']")
	static WebElement title;
	
	@FindBy(xpath = "//button[@id='back-to-products']")
	static WebElement backToHome;
	
	@FindBy(xpath = "//div[@class='cart_item']")
	static List<WebElement> cartItems;
	
	
	
	public RtCampQACheckOutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void checkOutDetails(String fname, String lname, String postCode) {
		firstName.sendKeys(fname);
		lastName.sendKeys(lname);
		postalCode.sendKeys(postCode);
	}
	
	public void verifyTotalPriceBeforeTax(double productPrice) {

			int productLoopCount = 0;
			double productTotal = 0.0;
			for (WebElement items : cartItems) {
				productLoopCount+=1;
				double cartItemAdded = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='cart_item'])["+productLoopCount+"]//div[@class='inventory_item_price']")).getText().replace("$", "")); 
				productTotal+=cartItemAdded;				
			}
			
//			converting values to string for consistency
			System.out.println(productTotal);
			Assert.assertEquals(Double.toString(productTotal), Double.toString(productPrice));
			String[] prices = totalPriceBeforeTax.getText().split("[$]");
			Assert.assertEquals(Double.toString(productTotal), prices[1]);
			Assert.assertEquals(Double.toString(productPrice), prices[1]);
			
	}
	
	public void nextPage() {
		nextPage.click();
	}
	
	public void clickFinish() {
		finish.click();
	}
	
	public void verifyCheckOutComplete() {
		Assert.assertEquals("Thank you for your order!", checkoutCompleteHeader.getText());
		Assert.assertEquals("Checkout: Complete!", title.getText());
	}

	public void backToHome() {
		backToHome.click();
		Assert.assertEquals("Products",title.getText());
	}
}
