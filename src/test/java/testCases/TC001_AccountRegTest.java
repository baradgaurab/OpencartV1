package testCases;


import static org.testng.Assert.fail;

import org.testng.Assert;
import org.testng.annotations.Test;


import pageObjects.AccountRegPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegTest extends BaseClass
{
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("***Starting TC001_AccountReg***");
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("***clicked on my account link***");
		hp.clickRegister();
		logger.info("***clicked on register link***");
		AccountRegPage RegPage=new AccountRegPage(driver);
		logger.info("***providing customer details***");
		RegPage.setFisrtName(randomString().toUpperCase());
		RegPage.setLastName(randomString().toUpperCase());
		RegPage.setEmail(randomString()+"@gmail.com");
		RegPage.setPhoneno(randomNumber());
		
		String password=randomPassword();
		RegPage.setPassword(password);            //here both password and confirm password should be same
		RegPage.setconfPassword(password);		  //so here,we create one string password under randomPassword
		RegPage.setPolicy();
		RegPage.setSubmit();
		logger.info("***validating msg***");
		
		String confmsg=RegPage.getConforationMsg();
		Assert.assertEquals(confmsg,"Your Account Has Been Created!" );
		}
		
		catch(Exception e)
		{
			logger.error("test failed");
		//	logger.debug("debug logs");
			Assert.fail();
		}
		logger.info("***Finished***");
	}

	
	
	
	

}
