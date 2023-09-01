package com.mate.carHunter.components.browsers;


import com.mate.carHunter.components.models.User;
import com.mate.carHunter.components.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
@Component
public class FacebookBrowser {

    @Value("${chrome.driver.path}")
    String chromePath;
    private LinkedHashMap<String, WebDriver> drivers;

    public FacebookBrowser() {
        drivers = new LinkedHashMap<>();

    }

    public WebDriver getBrowser(User user){
        String email = user.getEmail();
        if (drivers.containsKey(email)) {
            return drivers.get(email);
        }
        return createAndLogin(user);

    }

    protected WebDriver createAndLogin(User user){

        String email = user.getEmail();
        String password = user.getPassword();

        String path = "";

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--log-path=" + path + chromePath);
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--no-sandbox");
        System.setProperty("webdriver.chrome.driver", path + chromePath);
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();


        try {

            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(email, password);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        drivers.put(email, driver);

        return driver;

    }
}
