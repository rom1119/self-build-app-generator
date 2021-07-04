package com.SelfBuildApp.ddd.Project.domain.WebsiteCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PageCrawler {

    public void run(String URL) throws IOException {
        // set chrome driver exe path
        System.setProperty("webdriver.chrome.driver", "/Users/romanpytka/Downloads/chromedriver");
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://onet.pl");
//        List<WebElement> elemnts = new By.ByCssSelector("*");

        driver.findElement(By.cssSelector(".cmp-intro_acceptAll")).click();
//        WebDriverWait wait = new WebDriverWait(driver, 1);
        // find the search textbar in JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (WebElement htmlEl: driver.findElements(new By.ByCssSelector("body > *"))) {
            try {
                if (htmlEl.getTagName().equals("html") || htmlEl.getTagName().equals("body") || htmlEl.getTagName().equals("head")) {
                    continue;
                }
                System.out.println(htmlEl.getTagName());
                System.out.println(htmlEl.getText());
                System.out.println(htmlEl.getAttribute("class"));

//                wait.until(ExpectedConditions.elementToBeClickable(htmlEl));

            } catch (Exception e) {
                continue;

            }
//            System.out.println(htmlEl.getCssValue());

        }
        Object searchTextbar = js.executeScript("return document.styleSheets[0]");
        // we have to cast the returned object into webelement to access
        // all the webelement related methods
        ((WebElement) searchTextbar).sendKeys("abc");
//        String bgColor = driver.findElement(By.xpath("//h1[0]")).getCssValue("background-color");
//        String color = driver.findElement(By.xpath("//input[contains(@class,'searchSubmit')]")).getCssValue("background");
//        String borderBottomWidth = driver.findElement(By.xpath("//button[contains(@class,'btn-primary')]")).getCssValue("border-bottom-width");
//        System.out.println("Css Value for background color is : "+ bgColor);
//        System.out.println("Css Value for color is : "+ color);
//        System.out.println("Css Value for border bottom color is : "+ borderBottomWidth);
        driver.quit();
    }
}
