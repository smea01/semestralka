package cz.vse.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class Creating_Project {
    private ChromeDriver driver;
    private String url="http://digitalnizena.cz/rukovoditel/index.php?module=users/login";

    @Before
    public void init() throws IOException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        ChromeOptions cho = new ChromeOptions();
        cho.addArguments("--headless");
        cho.addArguments("start-maximized");
        cho.addArguments("window-size=1200,1100");
        cho.addArguments("--disable-gpu");
        cho.addArguments("--disable-extensions");
        //driver = new ChromeDriver(cho);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
//        driver.close();
    }

    @Test
    public void novy_projekt_failed() {
        Metody.prihlasenie(driver);
        driver.findElement(By.cssSelector("li:nth-child(4) .title:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn-primary-modal-action")));
        driver.findElement(By.cssSelector(".btn-primary-modal-action")).click();
        wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_158-error")));
        Assert.assertTrue(driver.findElement(By.id("fields_158-error")).isDisplayed());
    }


    @Test
    public void novy_projekt_passed() {
        Metody.prihlasenie(driver);
        driver.findElement(By.cssSelector("li:nth-child(4) .title:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.id("fields_158")).click();
        Select select = new Select(driver.findElement(By.id("fields_156")));
        select.selectByIndex(1);
        WebElement searchInput = driver.findElement(By.id("fields_158"));
        searchInput.sendKeys("smea01");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(".btn-primary-modal-action")));
        driver.findElement(By.cssSelector(".btn-primary-modal-action")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("smea01")));
        driver.findElement(By.linkText("smea01")).click();
        Assert.assertTrue(driver.getTitle().startsWith("Rukovoditel | Tasks"));
    }

}
