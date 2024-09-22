package rtCampQATests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rtCampQAPages.RtCampQAAllItemsPage;
import rtCampQAPages.RtCampQACartPage;
import rtCampQAPages.RtCampQACheckOutPage;
import rtCampQAPages.RtCampQALoginPage;

public class RtCampQARegressionTest {

	WebDriver driver;

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
	}

	@Test(priority = 1)
	public void TwoIetmsTest() {
		driver.get("https://www.saucedemo.com/");
		RtCampQALoginPage loginToSite = new RtCampQALoginPage(driver);
		loginToSite.login();

		RtCampQAAllItemsPage drop = new RtCampQAAllItemsPage(driver);
		drop.verifySortedItemListByName("za"); // Verify the sorting order displayed for Z-A on the “All Items” page.
		drop.verifySortedItemListByPrice("hilo"); // Verify the price order (high-low) displayed on the “All Items”
													// page.

		String itemOne = drop.addItemsToCart("Sauce Labs Bike Light");
		String itemTwo = drop.addItemsToCart("Sauce Labs Onesie");
		drop.goToCart();
		RtCampQACartPage cartPage = new RtCampQACartPage(driver);

		cartPage.verifyItemsInCart(itemOne);
		cartPage.verifyItemsInCart(itemTwo);
		cartPage.checkOut();

		RtCampQACheckOutPage checkOutPage = new RtCampQACheckOutPage(driver);
		checkOutPage.checkOutDetails("Test", "One", "100000");
		checkOutPage.nextPage();

		cartPage.verifyItemsInCart(itemOne);
		cartPage.verifyItemsInCart(itemTwo);
		checkOutPage.verifyTotalPriceBeforeTax(17.98);
		checkOutPage.clickFinish();
		checkOutPage.verifyCheckOutComplete();
		checkOutPage.backToHome();

	}
	
	@Test(priority = 2) 
	public void FourItemsTest() {

		driver.get("https://www.saucedemo.com/");

		RtCampQALoginPage loginToSite = new RtCampQALoginPage(driver);
		loginToSite.login();

		RtCampQAAllItemsPage drop = new RtCampQAAllItemsPage(driver);
		drop.verifySortedItemListByName("za"); // Verify the sorting order displayed for Z-A on the “All Items” page.
		drop.verifySortedItemListByPrice("hilo"); // Verify the price order (high-low) displayed on the “All Items”
													// page.

		String itemOne = drop.addItemsToCart("Sauce Labs Fleece Jacket");
		String itemTwo = drop.addItemsToCart("Sauce Labs Bolt T-Shirt");
		String itemThree = drop.addItemsToCart("Sauce Labs Backpack");
		String itemFour = drop.addItemsToCart("Test.allTheThings() T-Shirt (Red)");
		drop.goToCart();
		RtCampQACartPage cartPage = new RtCampQACartPage(driver);

		cartPage.verifyItemsInCart(itemOne);
		cartPage.verifyItemsInCart(itemTwo);
		cartPage.verifyItemsInCart(itemThree);
		cartPage.verifyItemsInCart(itemFour);
		cartPage.checkOut();

		RtCampQACheckOutPage checkOutPage = new RtCampQACheckOutPage(driver);
		checkOutPage.checkOutDetails("Test", "Two", "9590");
		checkOutPage.nextPage();

		cartPage.verifyItemsInCart(itemOne);
		cartPage.verifyItemsInCart(itemTwo);
		cartPage.verifyItemsInCart(itemThree);
		cartPage.verifyItemsInCart(itemFour);
		checkOutPage.verifyTotalPriceBeforeTax(111.96);
		checkOutPage.clickFinish();
		checkOutPage.verifyCheckOutComplete();
		checkOutPage.backToHome();

	}

	@Test(priority = 3) //Negative scenario , making this the least priority so others can can pass
	public void NegativeCase() {
		
		driver.get("https://www.saucedemo.com/");
		
		RtCampQALoginPage loginToSite = new RtCampQALoginPage(driver);
		loginToSite.login();
		
		RtCampQAAllItemsPage drop = new RtCampQAAllItemsPage(driver);
		drop.verifySortedItemListByName("za"); // Verify the sorting order displayed for Z-A on the “All Items” page.
		drop.verifySortedItemListByPrice("hilo"); // Verify the price order (high-low) displayed on the “All Items”
		// page.
		
		String itemOne = drop.addItemsToCart("Sauce Labs Fleece Jacket");
		String itemTwo = drop.addItemsToCart("Sauce Labs Bolt T-Shirt");
		String itemThree = drop.addItemsToCart("Sauce Labs Backpack");
		drop.goToCart();
		RtCampQACartPage cartPage = new RtCampQACartPage(driver);
		
		cartPage.verifyItemsInCart(itemOne);
		cartPage.verifyItemsInCart(itemTwo);
		cartPage.verifyItemsInCart(itemThree);
		cartPage.checkOut();
		
		RtCampQACheckOutPage checkOutPage = new RtCampQACheckOutPage(driver);
		checkOutPage.checkOutDetails("Test", "Two", "9590");
		checkOutPage.nextPage();
		
		cartPage.verifyItemsInCart(itemOne);
		cartPage.verifyItemsInCart(itemTwo);
		cartPage.verifyItemsInCart(itemThree);
		checkOutPage.verifyTotalPriceBeforeTax(0.00);
		checkOutPage.clickFinish();
		checkOutPage.verifyCheckOutComplete();
		checkOutPage.backToHome();
		
	}
	

//	@AfterTest
//	public void cleanUp() {
//		driver.quit();
//	}

}
