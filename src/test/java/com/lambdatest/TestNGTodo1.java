package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "TestNG With Java");
        capabilities.setCapability("name", m.getName() + " - " + this.getClass().getName());
        capabilities.setCapability("platformName", "Windows 10");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("version", "latest");
        capabilities.setCapability("plugin", "git-testng");
        capabilities.setCapability("tags", new String[]{"Feature", "Falcon", "Severe"});

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), capabilities);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
        driver.get("https://lambdatest.github.io/sample-todo-app/");

        System.out.println("Checking Boxes...");
        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li2")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li4")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 6");
        driver.findElement(By.id("addbutton")).click();
        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 7");
        driver.findElement(By.id("addbutton")).click();
        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 8");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li7")).click();
        driver.findElement(By.name("li8")).click();
        Thread.sleep(300);

        driver.findElement(By.id("sampletodotext")).sendKeys("Get Taste of Lambda and Stick to It");
        driver.findElement(By.id("addbutton")).click();
        driver.findElement(By.name("li9")).click();

        String spanText = driver.findElement(By.xpath("//li[9]/span")).getText();
        Assert.assertEquals(spanText.trim(), "Get Taste of Lambda and Stick to It");

        Status = "passed";
        Thread.sleep(150);
        System.out.println("Test Finished");
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.executeScript("lambda-status=" + Status);
        } finally {
            if (driver != null) driver.quit();
        }
    }
}