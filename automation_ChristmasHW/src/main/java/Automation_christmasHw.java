import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.Assert;
//import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Automation_christmasHw {

    protected static WebDriver driver;

    // method for clicking the element
    public void click_element(By by) {
        driver.findElement(by).click();
    }

    //method for send keys
    public void sendkeys(By by, String value) {
        driver.findElement(by).sendKeys(value);
    }

    @BeforeTest
    public void openBrowser() {

        System.setProperty("webdriver.chrome.driver", "C:\\Src\\automation_ChristmasHW\\src\\main\\java\\BrowserDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //driver.manage().window().fullscreen();
    }

    public String dateStamp() {
        DateFormat date = new SimpleDateFormat("mmddyymmss");
        Date date1 = new Date();
        String date2 = date.format(date1);

        return date2;
    }

    //@AfterTest
    public void closeBrowser() {
        driver.close();
    }


    @Test
    public void userShouldBeAbleToRegisterSuccessfully() {

        // openBrowser();
        //click on register button
        click_element(By.className("ico-register"));
        // click on gender button
        click_element(By.id("gender-male"));
        //enter First name
        sendkeys(By.id("FirstName"), "sandip");
        //enter last name
        sendkeys(By.id("LastName"), "patel");
        //enter DOB by method select
        driver.manage().timeouts().implicitlyWait(50,TimeUnit.SECONDS);
        Select birthDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
        birthDay.selectByValue("5");
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        month.selectByVisibleText("May");
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
        year.selectByValue("1995");
        //enter mail address with date stamp method
        sendkeys(By.id("Email"), "sandippatel2018" + dateStamp() + "@gmail.com");
        //enter company name
        sendkeys(By.id("Company"), "jskr");
        //Enter password with send keys method
        sendkeys(By.id("Password"), "sandip123");
        sendkeys(By.id("ConfirmPassword"), "sandip123");
        click_element(By.id("register-button"));
        String actualResult = driver.findElement(By.xpath("//div[contains(text(),'Your registration completed')]")).getText();
        String expectedResult = "Your registration completed";

        Assert.assertEquals("Test case failed", actualResult, expectedResult);


        System.out.println("fully worked");
        //click on continue button
        click_element(By.xpath("//input[@name='register-continue']"));

    }

    @Test
    public void registerUserShouldBeAbleToSendProductEmailSuccessfully() {

        userShouldBeAbleToRegisterSuccessfully();
        driver.navigate().refresh();

        //click on the product you wish to refere to friend
        click_element(By.xpath("//a[contains(text(),'Apple MacBook Pro 13-inch')]"));
        //enter friends email address
        click_element(By.className("email-a-friend"));
        sendkeys(By.id("FriendEmail"), "bhavinpatel" + dateStamp() + "@gmail.com");
        //enter presonal message
        sendkeys(By.id("PersonalMessage"), "This is the good laptop for you");
        click_element(By.xpath("//input[@name='send-email']"));
        String actual = driver.findElement(By.xpath("//div[contains(text(),'Your message has been sent.')]")).getText();
        String expectedResult = "Your message has been sent.";

        Assert.assertEquals("Test case failed", actual, expectedResult);
        click_element(By.xpath("//a[contains(text(),\"Log out\")]"));
        driver.navigate().refresh();
    }

    @Test
    public void unRegisteredUseShouldNotBeAbleToSendEmail() {
        //click on any product

        click_element(By.xpath("//img[@alt=\"Picture of Apple MacBook Pro 13-inch\"]"));
        // click_element(By.xpath("//a[@class=\"product\"]"));
        //click_element(By.xpath("//a[@class=\"product\"]"));
        System.out.println("Test : open product detail");
        //click to email friend
        click_element(By.className("email-a-friend"));
//enter friends email address, edit date stamp to generate new mail address
        sendkeys(By.id("FriendEmail"), "bhavin" + dateStamp() + "patel@gmail.com");
        //users email address
        sendkeys(By.id("YourEmailAddress"), "sandippatel36@gmail.com");
        //string message
        sendkeys(By.id("PersonalMessage"), "this is good product");
        //
        click_element(By.xpath("//input[@name='send-email']"));
        System.out.println("Test : send Email button");
        // comparing string for assert statement
        String actualResult = driver.findElement(By.xpath("//li[contains(text(),'Only registered customers can use email a friend feature')]")).getText();
        System.out.println("Test : acutal result linktext path");
        String expectedResults = "Only registered customers can use email a friend feature";

        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        Assert.assertEquals("test case failed", actualResult, expectedResults);
        // refresh page
        click_element(By.xpath("//img[@alt=\"nopCommerce demo store\"]"));

    }

    @Test
    public void userNeedToAcceptTC() {
        //click on mackbook
        click_element(By.xpath("//div[2][@class='item-grid']/div[2]/div/div[2]/div[3]/div[2]/input[1]"));
        //click on add to cart button
        click_element(By.xpath("//input[@id='add-to-cart-button-4']"));
        //click on shopping cart pop up
        click_element(By.xpath("//a[contains(text(),'shopping cart')]"));
        //try to check out
        click_element(By.id("checkout"));
        //creating string for Assert - getting the text back from the pop up
        String expectedCheckoutNotification = driver.findElement(By.id("terms-of-service-warning-box")).getText();
        String actualCheckoutnotification = "Please accept the terms of service before the next step.";
        // assert.assertEquals(message, expected, actual)
        Assert.assertEquals("Test case failed- checkout without acception T& C", expectedCheckoutNotification, actualCheckoutnotification);

        click_element(By.xpath("//button[@class=\"ui-button ui-corner-all ui-widget ui-button-icon-only ui-dialog-titlebar-close\"]"));

        // refresh page
        click_element(By.xpath("//img[@alt=\"nopCommerce demo store\"]"));
    }


    @Test
    public void register_User_Should_Be_able_to_Buy_Product_Successfully() {

        //calling method to register the user
        userShouldBeAbleToRegisterSuccessfully();
        driver.navigate().refresh();
        //Click on add to cart for HTC phone
        click_element(By.xpath("//div[2][@class='item-grid']/div[3]/div/div[2]/div[3]//input[@value='Add to cart']"));
        // click on shopping cart
        click_element(By.xpath("//a[contains(text(),'shopping cart')]"));
        // click on t&c
        click_element(By.id("termsofservice"));
        //click on check out
        click_element(By.id("checkout"));
        //select India from drop down menu

        new Select(driver.findElement(By.id("BillingNewAddress_CountryId"))).selectByVisibleText("India");
        //enter city name
        sendkeys(By.id("BillingNewAddress_City"), "Ahmedabad");
        //enter house name
        sendkeys(By.id("BillingNewAddress_Address1"), "Harinaman");
        //enter zip code
        sendkeys(By.id("BillingNewAddress_ZipPostalCode"), "380016");
        // Enter phone number
        sendkeys(By.id("BillingNewAddress_PhoneNumber"), "+919998522389");

        click_element(By.xpath("//div[@id='billing-buttons-container']/input[@class]"));
        click_element(By.id("shippingoption_1"));
        click_element(By.xpath("//input[@class='button-1 shipping-method-next-step-button']"));
        click_element(By.id("paymentmethod_1"));
        click_element(By.xpath("//input[@class='button-1 payment-method-next-step-button']"));
        //  new Select(driver.findElement(By.id("CreditCardType"))).selectByVisibleText("visa");
        sendkeys(By.id("CardholderName"), "sandip");
        sendkeys(By.id("CardNumber"), "4111111111111111");
        new Select(driver.findElement(By.id("ExpireMonth"))).selectByValue("11");
        new Select(driver.findElement(By.id("ExpireYear"))).selectByValue("2020");
        sendkeys(By.id("CardCode"), "737");
        click_element(By.xpath("//input[@class='button-1 payment-info-next-step-button']"));
        //confirming step-2 on checkout page
        click_element(By.xpath("//input[@class='button-1 confirm-order-next-step-button']"));

        String expected_Order_Completed_Message = driver.findElement(By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]")).getText();
        String actual_Order_complete_message = "Your order has been successfully processed!";
        // assert the string
        Assert.assertEquals("test case failed", expected_Order_Completed_Message, actual_Order_complete_message);
        driver.navigate().refresh();
        click_element(By.xpath("//a[contains(text(),\"Log out\")]"));

    }


    @Test
    public void user_Should_Be_Able_To_Sort_By_Price_High_to_Low() {

        //click on category camera & photo
        click_element(By.xpath("//img[@title='Show products in category Electronics']"));

        //click on Camera category page form Home
        click_element(By.xpath("//img[@title='Show products in category Camera & photo']"));
// select High to low in sort by menu

        new Select(driver.findElement(By.id("products-orderby"))).selectByVisibleText("Price: High to Low");
        System.out.println("value High to Low is sorted");
        //get string value of the first product
        String priceOf_FirstProduct = driver.findElement(By.xpath("//div[1][@class='item-box']//span[@class='price actual-price']")).getText();
        //get string value of the last product
        String priceOf_LastProduct = driver.findElement(By.xpath("//div[3][@class='item-box']//span[@class='price actual-price']")).getText();
        // $1300,00 needs to be trim to remove dollar sign and last 3 digits ".00"
        // use index concept i.e. index 0 = first digit "1"
        String trimvalueFp = priceOf_FirstProduct.substring(1, priceOf_FirstProduct.length() - 3);

// follow the same for the last product
        String trimvalueLp = priceOf_LastProduct.substring(1, priceOf_LastProduct.length() - 3);
        System.out.println("Value of First product in list" + trimvalueFp);
        System.out.println("Value of the last product in list" + trimvalueLp);

// initialissing the final value for if loop
        String trimvalueFp1 = "";


        for (int i = 0; i <= trimvalueFp.length() - 1; i++) {
//  took trimvalue.lenght()-1 because need to run the loop
            char c = trimvalueFp.charAt(i);
// to remove coma from the string
            if (c != ',') {

                trimvalueFp1 = trimvalueFp1 + c;

            }
        }
        System.out.println(trimvalueFp1);
        //change the string by predefine method in integer value
        int final_value_of_Firstproduct_in_int = Integer.parseInt(trimvalueFp1);


        int final_value_of_Lastproduct_in_int = Integer.parseInt(trimvalueLp);
        // if value to use assert statement
        if (final_value_of_Firstproduct_in_int > final_value_of_Lastproduct_in_int) {
            Assert.assertNotEquals(final_value_of_Firstproduct_in_int, final_value_of_Lastproduct_in_int);

        }

        click_element(By.xpath("//img[@alt=\"nopCommerce demo store\"]"));

        driver.quit();


    }


}



