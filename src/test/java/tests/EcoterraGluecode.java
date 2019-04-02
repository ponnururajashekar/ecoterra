package tests;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import pages.HomePage;
import pages.LoginPage;


public class EcoterraGluecode 
{
	public WebDriver driver;
	public WebDriverWait wait;
	public HomePage hp;
	public LoginPage lp;
	public Scenario s;
	public Properties p;
	
	@Before
	public void method1(Scenario s)throws Exception
	{
		this.s=s;
		File f=new File("E:\\leelajava\\ecoterra\\src\\test\\resources\\repository\\ecoterraproperties.properties");
		FileReader fr=new FileReader(f);
		p=new Properties();
		p.load(fr);
	}
	
	@Given("^launch site$")
	public void method2()
	{
		System.setProperty("webdriver.chrome.driver",p.getProperty("cpath"));
		driver=new ChromeDriver();
		hp=new HomePage(driver);
		lp=new LoginPage(driver);
		driver.get(p.getProperty("url"));
		wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOf(lp.logintype));
		driver.manage().window().maximize();
	}
	
	@When("^click on dropdown and select admin from dropdown$")
	public void method()
	{
		wait.until(ExpectedConditions.visibilityOf(lp.logintype));
		lp.selectDropDownAdmin();
	}
	
	@When("^click on dropdown and select Customer from dropdown$")
	public void methodc()
	{
		wait.until(ExpectedConditions.visibilityOf(lp.logintype));
		lp.selectDropDownCustomer();
	}
	
	@And("^fill username \"(.*)\"$")
	public void method3(String x)
	{
		wait.until(ExpectedConditions.visibilityOf(lp.uid));
		lp.filluid(x);
	}
	
	@And("^fill password \"(.*)\"$")
	public void method4(String y)
	{
		wait.until(ExpectedConditions.visibilityOf(lp.pwd));
		lp.fillpwd(y);
	}
	
	@And("^click login button$")
	public void method5()
	{
		wait.until(ExpectedConditions.visibilityOf(lp.loginbtn));
		lp.clickLogin();
	}
	
	@Then("^validate output for criteria \"(.*)\" for \"(.*)\" and \"(.*)\" for \"(.*)\"$")
	public void method6(String uc,String u,String pc,String p)throws Exception
	{
		Thread.sleep(5000);
	    try 
	    {
	    	if(uc.equals("valid") && pc.equals("valid") && hp.welcomemessg.isDisplayed())
	    	{
	    		
	    		wait.until(ExpectedConditions.visibilityOf(hp.welcomemessg));
	    		s.write("valid userid and password test passed");
	    	}
	    	else if(uc.equals("uid_blank") && pc.equals("valid") && lp.blankuidmessg.isDisplayed())
	    	{
	    		s.write("blank userid test passed");
	    	}
	    	else if(uc.equals("valid") && pc.equals("pwd_blank") && lp.blankpwdmessg.isDisplayed())
	    	{
	    		s.write("blank pwd test passed");
	    	}
	    	else if(uc.equals("invalid") && pc.equals("valid") && lp.uiderrmessg.isDisplayed())
	    	{
	    		s.write("invalid uid test passed");
	    	}
	    	else
	    	{
	    		byte[] ss=((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	    		s.embed(ss,"login test failed");
	    		Assert.fail();
	    	}
	    }
	    catch(Exception ex)
	    {
	    	byte[] ss=((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    		s.embed(ss,ex.getMessage());
    		Assert.fail();
	    }
	
	}
	@And("^close site$")
	public void method7()
	{
		driver.close();
	}
	
}
