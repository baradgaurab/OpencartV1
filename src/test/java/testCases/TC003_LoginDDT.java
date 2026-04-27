package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountpage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass
{
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven")//getting data provider from different class,
	//if dataprovider is from same class then no need to write this dataProviderClass=DataProviders.class
	public void verify_loginDDT(String email,String pwd,String exp)
	{	
		logger.info("***Strating TC003_LoginDDT***");
		try {
			//HomePage
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			//LoginPage
			LoginPage lp=new LoginPage(driver);
			lp.setEmailAddress(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			//MyAccount
			MyAccountpage ma=new MyAccountpage(driver);
			boolean targetpage=ma.isMyAccountPageExist();

			if(exp.equalsIgnoreCase("Valid"))
			{
				//if data is valid-Login successful->test pass-logout
				//				  -Login failed-test failed	
				if(targetpage==true)
				{
					ma.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			if(exp.equalsIgnoreCase("Invalid"))
			{
				//if data is Invalid-Login successful->test failed-logout
				//				  -Login failed-test pass	

				if(targetpage==true)
				{
					ma.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			Assert.fail();
		}

		logger.info("***finished TC003_LoginDDT***");
	}
}
