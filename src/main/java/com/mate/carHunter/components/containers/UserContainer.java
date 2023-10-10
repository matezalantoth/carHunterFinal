package com.mate.carHunter.components.containers;

import com.mate.carHunter.components.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

@Component
public class UserContainer {

    private LinkedHashMap<Integer, User> fbUsers;
    private List<User> users;
    private List<Integer> randomNumbers;

    public UserContainer() {

        this.users = new ArrayList<>();
        this.randomNumbers = new ArrayList<>();
        fbUsers = new LinkedHashMap<>();

        User user1 = buildUser1();
        users.add(user1);
        fbUsers.put(user1.getId(), user1);

        User user2 = buildUser2();
        users.add(user2);
        fbUsers.put(user2.getId(), user2);

        User user3 = buildUser3();
        users.add(user3);
        fbUsers.put(user3.getId(), user3);

    }

    public User getUser(){
        int userPosition = getNumber();
        return users.get(userPosition);
    }

    protected User buildUser1() {
        User user = new User();
        user.setId(1);
        user.setEmail("budaizsolt836@gmail.com");
        user.setPassword("carhunter99");
        return user;
    }

    protected User buildUser2() {
        User user = new User();
        user.setId(2);
        user.setEmail("nagyfrigyes37@gmail.com");
        user.setPassword("carhunter99");
        return user;
    }

    protected User buildUser3() {
        User user = new User();
        user.setId(2);
        user.setEmail("budai9726@gmail.com");
        user.setPassword("carhunter99");
        return user;
    }

    protected Integer getNumber(){
        getRandomNumber();
        Integer number = randomNumbers.get(0);
        return number;
    }

    protected void wasTheLastNumberTheSame(Integer newNumber) {
        if (randomNumbers.isEmpty()){
            randomNumbers.add(newNumber);
            return;
        }
        Integer oldNumber = randomNumbers.get(0);
        if (newNumber == oldNumber) {
            getRandomNumber();
        }
        randomNumbers.remove(oldNumber);
        randomNumbers.add(newNumber);

    }

    protected void getRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(users.size());
        wasTheLastNumberTheSame(number);
    }

}
