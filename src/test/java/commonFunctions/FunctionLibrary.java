package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	//method for launch browser
	@SuppressWarnings("deprecation")
	public static WebDriver startBrowser()throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			//driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		return driver;
	}
	//method for Url
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}	
	//method for wait for webelement
	public static void waitForElement(String LocaterType,String LocaterValue,String TestData)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocaterType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocaterValue)));
		}
		if(LocaterType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocaterValue)));
		}
		if(LocaterType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocaterValue)));
		}
	}
	// method for any textbox/typeAction
	public static void typeAction(String LocaterType,String LocaterValue,String TestData)
	{
		if(LocaterType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocaterValue)).clear();
			driver.findElement(By.xpath(LocaterValue)).sendKeys(TestData);
		}
		if(LocaterType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocaterValue)).clear();
			driver.findElement(By.name(LocaterValue)).sendKeys(TestData);
		}
		if(LocaterType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocaterValue)).clear();
			driver.findElement(By.id(LocaterValue)).sendKeys(TestData);
		}
	}
	//methods for any click buttons, checkboxs, radiobuttons, links, images
	public static void clickAction(String LocaterType,String LocaterValue)
	{
		if(LocaterType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocaterValue)).click();
		}
		if(LocaterType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocaterValue)).click();
		}
		if(LocaterType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocaterValue)).sendKeys(Keys.ENTER);
		}
	}
	//methods for validate title
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title,"Title is not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//methods for close browser
	public static void closeBrowser()
	{
		driver.quit();
	}
	//methods for date generate
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD hh_mm");
		return df.format(date);
	}
	//methods for select listbox
	public static void dropDownAction(String LocaterType,String LocaterValue,String TestData)
	{
		if(LocaterType.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(LocaterValue)));
			element.selectByIndex(value);
		}
		if(LocaterType.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(LocaterValue)));
			element.selectByIndex(value);
		}
		if(LocaterType.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(LocaterValue)));
			element.selectByIndex(value);
		}
	}
	//method for capture stock number into notepad
	public static void captureStock(String LocatorType,String LocatorValue) throws Throwable
	{
		String stockNum ="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			stockNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			stockNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			stockNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		//create notepad file into capture data folder
		FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush();
		bw.close();
	}
	//method to verify stock table
	public static void stockTable() throws Throwable
	{
		//read stock number from above notepad
		FileReader fr = new FileReader("./CaptureData/StockNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("Search_Panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(conpro.getProperty("Search_button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_data+"   "+Act_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_data, "Stock Number is not Matching");
		}catch(AssertionError a){
			System.out.println(a.getMessage());
		}
	}
	//methods for capture Supplier number in notepad
	public static void captureSupplier(String LocaterType,String LocaterValue) throws Throwable
	{
		String supplierNum ="";
		if(LocaterType.equalsIgnoreCase("xpath"))
		{
			supplierNum = driver.findElement(By.xpath(LocaterValue)).getAttribute("value");
		}
		if(LocaterType.equalsIgnoreCase("name"))
		{
			supplierNum = driver.findElement(By.name(LocaterValue)).getAttribute("value");
		}
		if(LocaterType.equalsIgnoreCase("id"))
		{
			supplierNum = driver.findElement(By.id(LocaterValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/SupplierNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(supplierNum);
		bw.flush();
		bw.close();
	}
	//method to verify supplier number from table
	public static void supplierTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/SupplierNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("Search_Panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).sendKeys(Exp_Data);
		    driver.findElement(By.xpath(conpro.getProperty("Search_button"))).click();
			Thread.sleep(4000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Exp_Data+"   "+Act_Data,true);
			try {
				 Assert.assertEquals(Act_Data, Exp_Data, "Supplier number is not matching");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}
	//method for Capture Customer number into notepad
	public static void captureCustomer(String LocaterType,String LocaterValue) throws Throwable
	{
		String customerNum ="";
		if(LocaterType.equalsIgnoreCase("xpath"))
		{
			customerNum=driver.findElement(By.xpath(LocaterValue)).getAttribute("value");
		}
		if(LocaterType.equalsIgnoreCase("name"))
		{
			customerNum = driver.findElement(By.name(LocaterValue)).getAttribute("value");
		}
		if(LocaterType.equalsIgnoreCase("id"))
		{
			customerNum = driver.findElement(By.id(LocaterValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(customerNum);
		bw.flush();
		bw.close();
	}
	//method to verify customer number from table
	public static void customerTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/CustomerNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("Search_Panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("Search_Textbox"))).sendKeys(Exp_Data);
		    driver.findElement(By.xpath(conpro.getProperty("Search_button"))).click();
			Thread.sleep(4000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
			Reporter.log(Exp_Data+"   "+Act_Data,true);
			try {
				 Assert.assertEquals(Act_Data, Exp_Data, "Customer number is not matching");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		
	}
}


