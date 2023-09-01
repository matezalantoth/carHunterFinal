package com.mate.carHunter.components.containers;

import org.junit.jupiter.api.Test;

public class LinkContainerTest {
    @Test
    void linkIndexTest(){
        LinkContainer linkContainer = new LinkContainer();
        String link = linkContainer.getLink();
        System.out.println(link);
        String link2 = linkContainer.getLink();
        System.out.println(link2);


    }
}
