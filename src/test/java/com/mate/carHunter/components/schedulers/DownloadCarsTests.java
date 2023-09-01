package com.mate.carHunter.components.schedulers;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class DownloadCarsTests {
    @Test
    void randomNumberGeneratorTest() {
        Random random = new Random();
        int randomNumber = random.nextInt(7);
        System.out.println(randomNumber);

    }

}
