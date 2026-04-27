package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener 
{
	public ExtentSparkReporter SparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);

		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName="Test-Report-" + timeStamp + ".html";
		
	//	String reportPath = System.getProperty("user.dir")+ File.separator + "reports" + File.separator + repName;
	//	SparkReporter = new ExtentSparkReporter(reportPath);
	//	SparkReporter= new ExtentSparkReporter("..\\reports\\"+ repName);
		
		String reportPath = System.getProperty("user.dir")+ "\\reports\\" + repName;

		SparkReporter = new ExtentSparkReporter(reportPath);

		SparkReporter.config().setDocumentTitle("opencart Automation Report");
		SparkReporter.config().setReportName("opencart Function Testing");
		SparkReporter.config().setTheme(Theme.DARK);

		extent=new ExtentReports();
		extent.attachReporter(SparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		List<String>incluedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
		if(!incluedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups",incluedGroups.toString());
		}

	}
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS,result.getName()+" got sucessfully executed");
	}
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL,result.getName()+" got Failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}catch(IOException e1)
		{
			e1.printStackTrace();
		}

	}
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP,result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	public void onFinish(ITestContext testContext)
	{
		extent.flush();

		String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport=new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		/*	try {
		
			String reportPath = System.getProperty("user.dir") + "\\reports\\" + repName;
			URL url = new URL("file:///" + reportPath);

			//create email msg
			ImageHtmlEmail email=new ImageHtmlEmail();

			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("gaurab.barad@gmail.com","Gaurab@25"));
			email.setSSLOnConnect(true);
			email.setFrom("gaurab.barad@mail.com");  //sender
			email.setSubject("Test Results");
			email.setMsg("Please find the attach report...");
			email.addTo("barad.gaurab@mail.com");   //receiver
			email.attach(url,"extent report", "please check report...");
			email.send();  //send the mail
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
*/

	}


}
