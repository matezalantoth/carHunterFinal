package com.mate.carHunter.components.containers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LinkContainer {
    private List<String> links;
    private Integer currentIndex;

    public LinkContainer() {
        this.currentIndex = 0;
        buildLinks();
    }

    public List<String> buildLinks() {
        this.links = new ArrayList<>();
        for (int firstNumber = 0; firstNumber <= 9000000; firstNumber += 1000000) {
            Integer secondNumber = firstNumber + 1000000;
            links.add("https://www.facebook.com/marketplace/budapest/vehicles?minPrice=" + firstNumber + "&maxPrice=" + secondNumber + "&sortBy=creation_time_descend&exact=false");

        }
        return links;
    }


    public String getLink(){
        String currentLink = links.get(currentIndex);
        currentIndex++;
        currentIndex = currentIndex % links.size();
        System.out.println(currentIndex);
        return currentLink;

    }
}

