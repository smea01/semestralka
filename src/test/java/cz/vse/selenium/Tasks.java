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
    public void newTask() {
        //GIVEN
        Methods.signIn(driver);
        Methods.newProject(driver);
        //WHEN
        driver.findElement(By.cssSelector(".btn-primary")).click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_168")));

        //vstup: name, typ, status, priorita, description
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
        driver.findElement(By.tagName("body")).sendKeys("description");
        driver.switchTo().defaultContent();
        driver.findElement(By.className("btn-primary-modal-action")).click();
        //THEN
        wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        List<WebElement> component = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        List<WebElement> tableCell = component.get(1).findElements(By.tagName("td"));
        List<WebElement> contentsCell = tableCell.get(1).findElements(By.tagName("a"));
        contentsCell.get(2).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-bordered table-hover table-item-details'] tr")));
        component = driver.findElements(By.cssSelector("[class='table table-bordered table-hover table-item-details'] tr"));
        WebElement name = driver.findElement(By.className("caption"));
        Assert.assertEquals("smea01_task", name.getText());
        WebElement description = driver.findElement(By.className("fieldtype_textarea_wysiwyg"));
        Assert.assertEquals("description", description.getText());

        //kontrola typ - Task, status - new, priorita - medium
        tableCell = component.get(3).findElements(By.tagName("td"));
        contentsCell = tableCell.get(0).findElements(By.tagName("div"));
        Assert.assertEquals("Task", contentsCell.get(0).getText());
        tableCell = component.get(4).findElements(By.tagName("td"));
        contentsCell = tableCell.get(0).findElements(By.tagName("div"));
        Assert.assertEquals("New", contentsCell.get(0).getText());
        tableCell = component.get(5).findElements(By.tagName("td"));
        contentsCell = tableCell.get(0).findElements(By.tagName("div"));
        Assert.assertEquals("Medium", contentsCell.get(0).getText());
        }


    @Test
    public void newTaskSeven() {
        //GIVEN
        Methods.signIn(driver);
        Methods.newProject(driver);
        //WHEN
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
            driver.findElement(By.tagName("body")).sendKeys("description");
            driver.switchTo().defaultContent();
            driver.findElement(By.className("btn-primary-modal-action")).click();
        }
        //THEN
        //Zobrazenie 3 taskov
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        List<WebElement> component = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        Assert.assertTrue(component.size() == 4);
        //Filtre z new na waiting
        driver.findElement(By.className("filters-preview-condition-include")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='chosen-choices'] a")));
        List<WebElement> filter = driver.findElements(By.cssSelector("[class='chosen-choices'] a"));
        filter.get(1).click();
        driver.findElement(By.className("btn-primary-modal-action")).click();
        //Zobrazenie 2 taskov
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        component = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        Assert.assertTrue(component.size() == 3);
        //Zmazanie filtrov
        driver.findElement(By.className("filters-preview-condition-include")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='chosen-choices'] a")));
        filter = driver.findElements(By.cssSelector("[class='chosen-choices'] a"));
        filter.get(1).click();
        filter.get(0).click();
        driver.findElement(By.className("btn-primary-modal-action")).click();
        //Zobrazenie 7 taskov
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr")));
        component = driver.findElements(By.cssSelector("[class='table table-striped table-bordered table-hover'] tr"));
        Assert.assertTrue(component.size() == 8);
        //Vymazanie taskov
        driver.findElement(By.id("select_all_items")).click();
        driver.findElement(By.cssSelector("[class='btn btn-default dropdown-toggle']")).click();
        driver.findElement(By.cssSelector("[class='btn btn-default dropdown-toggle']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
        driver.findElement(By.linkText("Delete")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary-modal-action")));
        driver.findElement(By.className("btn-primary-modal-action")).click();
    }
}