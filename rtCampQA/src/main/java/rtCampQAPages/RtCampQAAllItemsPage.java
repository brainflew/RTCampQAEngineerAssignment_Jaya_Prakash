package rtCampQAPages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class RtCampQAAllItemsPage {

	static WebDriver driver;
	@FindBy(xpath = "//select[@class='product_sort_container']")
	static WebElement productSortDropDown;

	@FindBy(xpath = "//div[@id='shopping_cart_container']")
	static WebElement goToCart;
	
	@FindBy(xpath = "//div[@class='inventory_item_name']")
	static List<WebElement> itemListByName;

	@FindBy(xpath = "//div[@class='inventory_item_price']")
	static List<WebElement> itemListByPrice;

	@FindBy(xpath = "//div[@class='inventory_item']")
	static List<WebElement> Allitems;
	
	public RtCampQAAllItemsPage(WebDriver driver) {
		RtCampQAAllItemsPage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectDropDown(String selectValue) {
		Select drop = new Select(productSortDropDown);
		drop.selectByValue(selectValue);
	}

	public void verifySortedItemListByName(String selectValue) {

		selectDropDown(selectValue);
		List<String> itemNamesZ_A = new ArrayList<>();
		for (WebElement item : itemListByName) {
			itemNamesZ_A.add(item.getText());
		}
		List<String> sortedItems = new java.util.ArrayList<>(itemNamesZ_A);
		Collections.sort(sortedItems, Collections.reverseOrder()); // sorting it to a-z then back to z-a
		Assert.assertEquals(itemNamesZ_A, sortedItems);
	}

	public void verifySortedItemListByPrice(String selectValue) {

		selectDropDown(selectValue);
		List<Double> itemNamesHi_Lo = new ArrayList<>();
		for (WebElement item : itemListByPrice) {
			itemNamesHi_Lo.add(Double.parseDouble((item.getText().replace("$", ""))));
		}
		List<Double> sortedItems = new java.util.ArrayList<>(itemNamesHi_Lo);
		Collections.sort(sortedItems, Collections.reverseOrder()); // sorting it to low to high then back reverse
		Assert.assertEquals(itemNamesHi_Lo, sortedItems);
	}

	public String addItemsToCart(String itemName) {
		int productLoopCount = 0;
		for (WebElement items : Allitems) {
			productLoopCount+=1;
			WebElement item = driver.findElement(By.xpath("(//div[@class='inventory_item'])["+productLoopCount+"]"));
			if(driver.findElement(By.xpath("(//div[@class='inventory_item'])["+productLoopCount+"]//div[@class='inventory_item_name ']")).getText().equals(itemName)) {
				System.out.println(item.findElement(By.xpath("//div[normalize-space()='"+itemName+"']")).getText());
				System.out.println(productLoopCount);
				item.findElement(By.xpath("(//div[@class='inventory_item_label']/following-sibling::div//button)["+productLoopCount+"]")).click();
				break;
			}			
		}
		return itemName;
	}
	
	public void goToCart() {
		goToCart.click();
	}
}
