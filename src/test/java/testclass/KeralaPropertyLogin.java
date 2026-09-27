package testclass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utility.ExcelUtils;

public class KeralaPropertyLogin {
	WebDriver driver;

    @BeforeClass
    public void setup() throws Exception {

        ExcelUtils.openExcel("Login");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void loginValidation() throws Exception {

        int rowCount = ExcelUtils.getRowCount();

        for(int i=1; i<=rowCount; i++) {

            String username = ExcelUtils.getCellData(i,0);
            String password = ExcelUtils.getCellData(i,1);
            String expected = ExcelUtils.getCellData(i,2);
            
            System.out.println("Username = " + username);
            System.out.println("Password = " + password);

            driver.get("https://keralaproperty.in/member/login");
            Thread.sleep(5000);

            System.out.println(driver.getCurrentUrl());
            System.out.println(driver.getTitle());
            System.out.println(driver.getPageSource().contains("ulogin"));

            driver.findElement(By.id("ulogin")).clear();
            driver.findElement(By.id("ulogin")).sendKeys(username);
            Thread.sleep(3000);

            driver.findElement(By.id("upass")).clear();
            driver.findElement(By.id("upass")).sendKeys(password);
            Thread.sleep(3000);

            driver.findElement(By.id("sub_logbtn")).click();

            Thread.sleep(3000);

            String actual;

            if(driver.getCurrentUrl().contains("success")) {
                actual = "Valid User";
            }
            else {
                actual = "Invalid User";
            }

            ExcelUtils.setCellData(i,3,actual);

            String status =
                    actual.equalsIgnoreCase(expected)
                    ? "PASS"
                    : "FAIL";

            ExcelUtils.setCellData(i,4,status);

            System.out.println(
                    "Row " + i +
                    " Expected = " + expected +
                    " Actual = " + actual +
                    " Status = " + status);
        }
    }

    @AfterClass
    public void tearDown() throws Exception {

        ExcelUtils.saveAndClose();

        if(driver!=null) {
            driver.quit();

}
    }
}
