package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;//log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass 
{
	public static WebDriver driver;

	public Logger logger;//log4j
	public Properties p;

	@BeforeClass(groups={"sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException
	{
		//adding properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);



		logger=LogManager.getLogger(this.getClass()); 


		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities=new DesiredCapabilities();

			//OS
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("Linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else
			{
				System.out.println("no matching os");
				return;
			}

			//browser

			switch(br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome");break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
			default: System.out.println("no matching browser");return;
			}
			
			driver=new RemoteWebDriver(new URL("http://192.168.29.107:4444/wd/hub"),capabilities);
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" :driver=new ChromeDriver();break;
			case "edge" :driver=new EdgeDriver();break;
			case "firefox" :driver=new FirefoxDriver();break;
			default :System.out.println("invalid");return;
			}


		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");
		driver.get(p.getProperty("appURL1"));//reading from properties file
		driver.manage().window().maximize();
	}

	@AfterClass(groups={"sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}

	public String randomString()
	{
		return UUID.randomUUID().toString().substring(0, 5);   //UUID-->generate universally unique id,it is used to help create random data
	}
	public String randomNumber()
	{
		return UUID.randomUUID().toString().replaceAll("[^0-9]","").substring(0, 5);   
	}
	public String randomPassword()
	{
		return UUID.randomUUID().toString().replaceAll("[^0-9]","").substring(0, 8)+"@";   


	}

	public String captureScreen(String tname) throws IOException
	{
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
		File SourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname +"_"+timeStamp+".png";

		File targetFile=new File(targetFilePath);
		SourceFile.renameTo(targetFile);
		return targetFilePath;
	}

}
