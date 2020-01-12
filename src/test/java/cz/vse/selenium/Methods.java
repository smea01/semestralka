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


public class Methods {
    private static String url="http://digitalnizena.cz/rukovoditel/index.php?module=users/login";

    public static void signIn(ChromeDriver driver) {
        driver.get(url);
        WebElement searchInput = driver.findElement(By.name("username"));
        searchInput.sendKeys("rukovoditel");
        searchInput = driver.findElement(By.name("password"));
        searchInput.sendKeys("vse456ru");
        searchInput.sendKeys(Keys.ENTER);
    }

    public static void newProject(ChromeDriver driver) {
    driver.findElement(By.cssSelector("li:nth-child(4) .title:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();

    WebDriverWait wait = new WebDriverWait(driver, 4);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_158")));

    WebElement searchInput = driver.findElement(By.id("fields_158"));
        searchInput.sendKeys("smea01");
        driver.findElement(By.id("fields_156"));
    Select select = new Select(driver.findElement(By.id("fields_156")));
        select.selectByIndex(1);

        driver.findElement(By.id("fields_159")).click();
        driver.findElement(By.cssSelector("td[class='active day']")).click();
        driver.findElement(By.className("btn-primary-modal-action")).click();
    }
}
