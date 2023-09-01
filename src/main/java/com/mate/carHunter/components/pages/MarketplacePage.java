package com.mate.carHunter.components.pages;

import com.mate.carHunter.components.containers.ImageContainer;
import com.mate.carHunter.components.models.Car;
import com.mate.carHunter.components.models.Image;
import org.openqa.selenium.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarketplacePage {
    WebDriver driver;
    ImageContainer imageContainer;

    public MarketplacePage(WebDriver driver, ImageContainer imageContainer) {
        this.driver = driver;
        this.imageContainer = imageContainer;
    }

    public List<Car> getCars(String facebookLink) throws InterruptedException {
        Thread.sleep(2000);
        List<Car> carsInfo = new ArrayList<>();

        scrollDown();
        driver.findElements(By.xpath("//img")).forEach(image -> {
            try {

                String name = image.getAttribute("alt");
                if (name.equals("Loading more items") || name.isEmpty() || name.equals("További termékek betöltése")) {
                    return;
                }

                String screenshot = ((TakesScreenshot) image).getScreenshotAs(OutputType.BASE64);

                WebElement carLink = image.findElement(By.xpath("./ancestor::a"));
                WebElement distance = carLink.findElement(By.xpath("./div/div/following-sibling::div/div/div/span/span"));
                WebElement price = carLink.findElement(By.xpath("./div/div/following-sibling::div/div/span/div/span"));

                String distanceText = distance.getText();

                Car car = setCarInfo(image, carLink, price, distanceText, facebookLink);
                BigInteger marketplaceId = regexMarketplaceId(car);
                car.setMarketplaceId(marketplaceId);

                Image carImage = new Image();
                carImage.setMarketplaceId(marketplaceId);
                carImage.setImageContent("data:image/png;base64," + screenshot);
                imageContainer.addImage(carImage);

                carsInfo.add(car);


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });
        return carsInfo;
    }

    private void scrollDown() throws InterruptedException {
        int scrolls = getRandomNumberUsingNextInt();

        for (int i = 1; i <= scrolls; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 10000)", "");
            Thread.sleep(4000);
        }
    }

    private Car setCarInfo(WebElement image, WebElement link, WebElement price, String distanceText, String facebookLink) {
        Car car = new Car();

        car.setTitle(image.getAttribute("alt"));
        car.setImage(image.getAttribute("src"));
        car.setLink(link.getAttribute("href"));
        car.setPrice(price.getText());
        car.setDistance(distanceText);
        car.setSourceUrl(facebookLink);

        return car;
    }

    private BigInteger regexMarketplaceId(Car car) throws Exception {
        Pattern pattern = Pattern.compile("/item/([0-9]+)/");
        Matcher matcher = pattern.matcher(car.getLink());

        if (!matcher.find()) {
            throw new Exception("No Marketplace ID was found!!");
        }
        String marketplaceId = matcher.group(1);

        return new BigInteger(marketplaceId);
    }

    public int getRandomNumberUsingNextInt() {
        Random random = new Random();
        return random.nextInt(6 - 4) + 4;
    }

}
