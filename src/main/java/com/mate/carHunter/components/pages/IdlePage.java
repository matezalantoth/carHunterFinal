package com.mate.carHunter.components.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class IdlePage {

    WebDriver driver;

    public IdlePage(WebDriver driver){
        this.driver = driver;
    }

    public void scrollOnHomePage() throws InterruptedException {
        driver.get("facebook.com");
        scrollDown();

    }

    private void scrollDown() throws InterruptedException {

        for (int i = 1; i <= 5; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 10000)", "");
            Thread.sleep(10000);
        }
    }

}
