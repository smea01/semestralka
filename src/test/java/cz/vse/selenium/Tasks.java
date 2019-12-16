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
public class Tasks {
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
    public void novy_task() {
        Metody.prihlasenie(driver);
        Metody.novyProjekt(driver);






        driver.findElement(By.xpath("//a[contains(text(),'smea01')]")).click();
        driver.findElement(By.cssSelector(".btn-default:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".btn-default:nth-child(1)")).click();

        WebDriverWait wait = new WebDriverWait(driver, 4);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn-group li:nth-child(2) > a")));
        driver.findElement(By.cssSelector(".btn-group li:nth-child(2) > a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uniform-delete_confirm")));
        driver.findElement(By.id("delete_confirm")).click();

        wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn:nth-child(3)")));
        driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();
    }

}
