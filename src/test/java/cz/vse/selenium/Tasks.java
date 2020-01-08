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
import java.util.List;

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
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void novy_task() {
        Metody.prihlasenie(driver);
        Metody.novyProjekt(driver);
        driver.findElement(By.cssSelector(".btn-primary")).click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_168")));

        //vstup: nazov, typ, status, priorita, popis
        WebElement searchInput = driver.findElement(By.id("fields_168"));
        searchInput.sendKeys("smea01_task");
        driver.findElement(By.id("fields_167"));
        Select select = new Select(driver.findElement(By.id("fields_167")));
        select.selectByIndex(1);
        driver.findElement(By.id("fields_169"));
        select = new Select(driver.findElement(By.id("fields_169")));
        select.selectByIndex(0);
        driver.findElement(By.id("fields_170"));
        select = new Select(driver.findElement(By.id("fields_170")));
        select.selectByIndex(2);
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        driver.findElement(By.tagName("body")).sendKeys("Popis");
        driver.switchTo().defaultContent();
        driver.findElement(By.className("btn-primary-modal-action")).click();

        wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        List<WebElement> zlozka = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        List<WebElement> bunka = zlozka.get(1).findElements(By.tagName("td"));
        List<WebElement> obsah_bunky = bunka.get(1).findElements(By.tagName("a"));
        obsah_bunky.get(2).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-bordered table-hover table-item-details'] tr")));
        zlozka = driver.findElements(By.cssSelector("[class='table table-bordered table-hover table-item-details'] tr"));

        WebElement nazov = driver.findElement(By.className("caption"));
        Assert.assertEquals("smea01_task", nazov.getText());
        WebElement popis = driver.findElement(By.className("fieldtype_textarea_wysiwyg"));
        Assert.assertEquals("Popis", popis.getText());

        //kontrola typ - Task, status - new, priorita - medium
        bunka = zlozka.get(3).findElements(By.tagName("td"));
        obsah_bunky = bunka.get(0).findElements(By.tagName("div"));
        Assert.assertEquals("Task", obsah_bunky.get(0).getText());
        bunka = zlozka.get(4).findElements(By.tagName("td"));
        obsah_bunky = bunka.get(0).findElements(By.tagName("div"));
        Assert.assertEquals("New", obsah_bunky.get(0).getText());
        bunka = zlozka.get(5).findElements(By.tagName("td"));
        obsah_bunky = bunka.get(0).findElements(By.tagName("div"));
        Assert.assertEquals("Medium", obsah_bunky.get(0).getText());
        }


    @Test
    public void nove_tasky_sedem() {
        Metody.prihlasenie(driver);
        Metody.novyProjekt(driver);
        //When
        for(int i = 0;i<7;i++)
        {
            driver.findElement(By.className("btn-primary")).click();
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_168")));
            WebElement searchInput = driver.findElement(By.id("fields_168"));
            searchInput.sendKeys("smea01_task");
            Select select = new Select(driver.findElement(By.id("fields_169")));
            select.selectByIndex(i);
            driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
            driver.findElement(By.tagName("body")).sendKeys("Popis");
            driver.switchTo().defaultContent();
            driver.findElement(By.className("btn-primary-modal-action")).click();
        }
        //Then
        //Zobrazenie 3 taskov
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        List<WebElement> zlozka = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        Assert.assertTrue(zlozka.size() == 4); // 4 protože nadpis je taky řádek
        



}
