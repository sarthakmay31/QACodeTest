package testcases;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
public class QACodeTest {

	static ExtentTest test;
	static ExtentReports report;
	static WebDriver driver;
	static JavascriptExecutor js;

	@BeforeClass
	public static void startTest()
	{
		report = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
		test = report.startTest("ExtentDemo");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		js = (JavascriptExecutor) driver;
	}



	@Test
	public void qa_code_test() throws InterruptedException
	{
		driver.manage().window().maximize();
		driver.get("https://www.dbs.com.sg/personal/default.page");
		if(driver.getTitle().equals("Bank Accounts, Cards, Loans, Financial Planning | DBS Singapore"))
		{

			WebElement CardBtn;
			WebElement CCcardBtn;
			WebElement AllLabel;
			WebElement CCcompareBtn1;
			WebElement CCcompareBtn2;
			WebElement CompareBtn;
			WebElement ScrollTill;
			WebElement MinIncome;
			
			CardBtn=driver.findElement(By.xpath("(//a[text()='Cards'])[2]"));
			CardBtn.click();

			CCcardBtn=driver.findElement(By.xpath("(//a[text()='Credit Cards'])[1]"));
			CCcardBtn.click();


			AllLabel = driver.findElement(By.xpath("//a[text()='All']"));
			js.executeScript("arguments[0].scrollIntoView(true);", AllLabel);
			
			
			
			WebDriverWait wait=new WebDriverWait(driver, 20);
			CCcompareBtn1=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='cb0']")));
			js.executeScript("arguments[0].click();", CCcompareBtn1);
			
			CCcompareBtn2=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='cb1']")));
			js.executeScript("arguments[0].click();", CCcompareBtn2);
			
			CompareBtn=driver.findElement(By.xpath("//button[@id='cardCompareBtn']"));
			js.executeScript("arguments[0].click();", CompareBtn);


			ScrollTill = driver.findElement(By.xpath("//div[text()='Card Type']"));
			js.executeScript("arguments[0].scrollIntoView(true);", ScrollTill);
			
			MinIncome=driver.findElement(By.xpath("//div[text()='Min Income Per Annum']/following::div[1]"));
			
			String IncomeVal=MinIncome.getText();
			
			test.log(LogStatus.FAIL, "Test Passed");
			Assert.assertTrue(IncomeVal.equals("S$30,000"));


		}
		else
		{
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.assertTrue(false);
		}
	}


	@AfterClass
	public static void endTest()
	{
		report.endTest(test);
		report.flush();
		driver.close();
	}
}