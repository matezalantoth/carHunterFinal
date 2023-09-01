package com.mate.carHunter.components.schedulers;

import com.mate.carHunter.components.browsers.FacebookBrowser;
import com.mate.carHunter.components.clients.CarClient;
import com.mate.carHunter.components.containers.ImageContainer;
import com.mate.carHunter.components.containers.LinkContainer;
import com.mate.carHunter.components.containers.UserContainer;
import com.mate.carHunter.components.models.Car;
import com.mate.carHunter.components.models.Image;
import com.mate.carHunter.components.models.User;
import com.mate.carHunter.components.pages.IdlePage;
import com.mate.carHunter.components.pages.MarketplacePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

@Component
public class DownloadCars {

    @Autowired
    FacebookBrowser facebookBrowser;
    @Autowired
    UserContainer userContainer;
    @Autowired
    LinkContainer linkContainer;
    @Autowired
    ImageContainer imageContainer;
    @Autowired
    CarClient carClient;

    private LinkedHashMap<String, String> activeTabs;
    public DownloadCars(){
        this.activeTabs = new LinkedHashMap<>();
    }

    @Scheduled(fixedRate = 300_000)
    public void getCars() throws Exception {
        LocalDateTime time = LocalDateTime.now();
        if (time.getHour() < 8){
            return;
        }
        if (time.getHour() > 17){
            return;
        }
        User user = userContainer.getUser();
        String currentLink = linkContainer.getLink();

        WebDriver driver = facebookBrowser.getBrowser(user);
        Thread.sleep(2_000);

        if (activeTabs.containsKey(currentLink)){
            when_tabIsFound(driver, currentLink);
        }else {
            when_tabIsNotFound(driver, currentLink);
        }

        MarketplacePage marketplacePage = new MarketplacePage(driver, imageContainer);
        List<Car> cars = marketplacePage.getCars(currentLink);
        carClient.sendCarsToServer(cars);

        cars.forEach(car -> {
            Image image = imageContainer.getImageByMarketplaceId(car.getMarketplaceId());
            car.setImageContent(image.getImageContent());
            carClient.updateCar(car);

        });

        IdlePage idlePage = new IdlePage(driver);
        idlePage.scrollOnHomePage();


    }

    public void when_tabIsFound(WebDriver driver, String link){
        System.out.println("tab found");
        String correspondingWindowHandle = activeTabs.get(link);
        System.out.println(correspondingWindowHandle);
        System.out.println(link);
        driver.switchTo().window(correspondingWindowHandle);
        System.out.println("worked");
        driver.get(link);

    }

    public void when_tabIsNotFound(WebDriver driver, String link){
        System.out.println("tab not found");
        driver.switchTo().newWindow(WindowType.TAB);
        String windowHandle = driver.getWindowHandle();
        System.out.println(windowHandle);
        System.out.println(link);
        driver.get(link);
        activeTabs.put(link, windowHandle);

    }

}
