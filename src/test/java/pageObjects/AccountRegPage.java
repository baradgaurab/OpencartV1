package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegPage extends BasePage
{
	public AccountRegPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath="//input[@id='input-firstname']")	
	WebElement txtfirstName;

	@FindBy(xpath="//input[@id='input-lastname']")	
	WebElement txtlastName;

	@FindBy(xpath="//input[@id='input-email']")	
	WebElement txtemail;
	
	@FindBy(xpath="//input[@id='input-telephone']")	
	WebElement txtphone;

	@FindBy(xpath="//input[@id='input-password']")	
	WebElement txtpassword;
	
	@FindBy(xpath="//input[@id='input-confirm']")	
	WebElement txtconfpassword;

	@FindBy(xpath="//input[@name='agree']")	
	WebElement chkpolicy;

	@FindBy(xpath="//input[@value='Continue']")	
	WebElement btnSubmit;

	@FindBy(xpath="//h1[text()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setFisrtName(String fname)
	{
		txtfirstName.sendKeys(fname);
	}
	public void setLastName(String lname)
	{
		txtlastName.sendKeys(lname);
	}
	public void setEmail(String email)
	{
		txtemail.sendKeys(email);
	}
	public void setPhoneno(String ph)
	{
		txtphone.sendKeys(ph);
	}
	public void setPassword(String pwd)
	{
		txtpassword.sendKeys(pwd);
	}
	
	public void setconfPassword(String pwd)
	{
		txtconfpassword.sendKeys(pwd);
	}
	public void setPolicy()
	{
		chkpolicy.click();
	}
	public void setSubmit()
	{
		//slo-1
		btnSubmit.click();
		//sol-2
		//btnSubmit.submit();
		//sol-3
		//	Actions act=new Actions(driver);
		//	act.moveToElement(btnSubmit).click().perform();
	}
	public String getConforationMsg()
	{
		try
		{
			return(msgConfirmation.getText());
		}catch(Exception e)
		{
			return(e.getMessage());
		}
	}

}
