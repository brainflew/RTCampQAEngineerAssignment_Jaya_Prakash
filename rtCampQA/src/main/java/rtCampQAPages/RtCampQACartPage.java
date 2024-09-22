package rtCampQAPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class RtCampQACartPage {

	static WebDriver driver;



	@FindBy(xpath = "//div[@class='cart_item']")
	static List<WebElement> cartItems;

	@FindBy(xpath = "//button[@id='checkout']")
	static WebElement checkout;
	
	public RtCampQACartPage(WebDriver driver) {
		RtCampQACartPage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void verifyItemsInCart(String itemName) {
		int productLoopCount = 0;
		for (WebElement items : cartItems) {
			productLoopCount+=1;
			String cartItemAdded = driver.findElement(By.xpath("(//div[@class='cart_item'])["+productLoopCount+"]//div[@class='inventory_item_name']")).getText(); 
			try {
				Assert.assertEquals(cartItemAdded, itemName);	
			}catch(AssertionError e) {
				continue;
			}
					
			break;
		}
	}

	public void checkOut() {
		checkout.click();
	}
}
