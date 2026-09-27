package testclass;
import org.openqa.selenium.Alert;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Utility.ExcelUtils;


public class KeralaPropertyRegistration {
	WebDriver driver;
	   @BeforeClass
	   public void setup() throws Exception {
	       ExcelUtils.openExcel("UserDetails");
	       driver = new ChromeDriver();
	       driver.manage().window().maximize();
	   }
	   @Test
	   public void registrationValidation() throws Exception {
	       int rowCount = ExcelUtils.getRowCount();
	       for (int i = 1; i <= rowCount; i++) {
	           String iam = ExcelUtils.getCellData(i, 0);
	           String name = ExcelUtils.getCellData(i, 1);
	           String email = ExcelUtils.getCellData(i, 2);
	           String password = ExcelUtils.getCellData(i, 3);
	           String confirmPassword = ExcelUtils.getCellData(i, 4);
	           String mobile = ExcelUtils.getCellData(i, 5);
	           String expected = ExcelUtils.getCellData(i, 6);
	           driver.get("https://keralaproperty.in/member/register/");
	           // Select User Type
	           Select userType = new Select(driver.findElement(By.id("listowner")));
	           userType.selectByVisibleText(iam);
	           // Enter Name
	           driver.findElement(By.id("utitle")).clear();
	           driver.findElement(By.id("utitle")).sendKeys(name);
	           // Enter Email
	           driver.findElement(By.id("umail")).clear();
	           driver.findElement(By.id("umail")).sendKeys(email);
	           // Enter Password
	           driver.findElement(By.id("memberpass")).clear();
	           driver.findElement(By.id("memberpass")).sendKeys(password);
	           // Enter Confirm Password
	           driver.findElement(By.id("repass")).clear();
	           driver.findElement(By.id("repass")).sendKeys(confirmPassword);
	           // Enter Mobile Number
	           driver.findElement(By.id("umobile")).clear();
	           driver.findElement(By.id("umobile")).sendKeys(mobile);
	           // Click Create Account
	           driver.findElement(By.id("sub_regbutton")).click();
	           Thread.sleep(2000);
	           String actual;
	           try {
	               // Check if alert appears
	               Alert alert = driver.switchTo().alert();
	               String alertMessage = alert.getText();
	               System.out.println("Alert Message = " + alertMessage);
	               alert.accept();
	               actual = "Invalid User";
	           } catch (Exception e) {
	               String currentUrl = driver.getCurrentUrl();
	               System.out.println("Current URL = " + currentUrl);
	               if (currentUrl.contains("/member/login/success")) {
	                   actual = "Valid User";
	               } else {
	                   actual = "Invalid User";
	               }
	           }
	           // Write Actual Result
	           ExcelUtils.setCellData(i, 7, actual);
	           // Compare Expected and Actual
	           String status =
	                   actual.equalsIgnoreCase(expected)
	                   ? "PASS"
	                   : "FAIL";
	           // Write Status
	           ExcelUtils.setCellData(i, 8, status);
	           System.out.println(
	                   "Row " + i +
	                   " | Expected = " + expected +
	                   " | Actual = " + actual +
	                   " | Status = " + status);
	           Thread.sleep(1000);
	       }
	   }
	   @AfterClass
	   public void tearDown() throws Exception {
	       ExcelUtils.saveAndClose();
	       if (driver != null) {
	           driver.quit();
	       }
	   }
	}

	



