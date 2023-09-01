package com.mate.carHunter.components.clients;

import com.mate.carHunter.components.models.Car;
import com.mate.carHunter.components.models.Token;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
public class CarClient {

    private Token token;

    public void sendCarsToServer(List<Car> carsInfo) {

        JSONArray carsFinal = new JSONArray();
        carsInfo.forEach(newCar -> {
            JSONObject carFinal = newCar.getJSONInfo();
            carsFinal.put(carFinal);
        });

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.thecarhunt.com")
                .build();


        Mono<String> response = webClient.post()
                .uri("/api/cars")
                .headers(httpHeaders -> {
                    httpHeaders.set("Authorization", "Bearer " + getToken().getAuthenticationToken());
                    httpHeaders.set("Content-Type", "application/json");
                })
                .body(BodyInserters.fromValue(carsFinal.toString()))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMinutes(1));

        response.block();
        response.subscribe(e -> {
            System.out.println("------------------------------RESPONSE----------------------------------");
            System.out.println(e);
            System.out.println("------------------------------END----------------------------------");

        });

    }

    public void updateCar(Car car){

        JSONObject carInfo = car.getJSONInfo();

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.thecarhunt.com")
                .build();


        Mono<String> response = webClient.post()
                .uri("/api/cars/" + car.getMarketplaceId())
                .headers(httpHeaders -> {
                    httpHeaders.set("Authorization", "Bearer " + getToken().getAuthenticationToken());
                    httpHeaders.set("Content-Type", "application/json");
                })
                .body(BodyInserters.fromValue(carInfo.toString()))
                .retrieve()
                .bodyToMono(String.class);
        response.block();
        response.subscribe(e -> {
            System.out.println("------------------------------RESPONSE----------------------------------");
            System.out.println(e);
            System.out.println("------------------------------END----------------------------------");

        });

    }

    protected Token getToken() {
        if (token != null) {
            return token;
        }
        fetchToken();
        return token;
    }

    protected void fetchToken() {
        token = new Token();
        token.setAuthenticationToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYmE5MzA0ZDEyZGQ3ZWVmMjNhYmFiNmQ0ZDFhZjJlMGQyN2I5YjYzODE4Y2U4N2VlYzhjNmY2M2ViNzM0NWQxN2FjNTQ0MjA0M2EwYjA2ZmEiLCJpYXQiOjE2OTE1NzY1NzguMDg4MzQ0LCJuYmYiOjE2OTE1NzY1NzguMDg4MzQ3LCJleHAiOjE3MDc0NzQxNzguMDY5MzAyLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.yzDTxDby1qmADpEZQNfxQGDHpLJdjXvF5NLqRwYYAaed4-zWo72lYGAuf17yRoPccTwskbRoyTZZcSnl-zjOEq091dyIyU9RYEJnPbtONwVJac1m_MdKniMON3TT6YSAoQZ0jXCBTCpSs-0HEA32Dtv_qwt1jJaNWbKeTRPodURLeOtRPRsFMhqYp7EyZQfZ2ky6WLuz_AWCrIoV4nmMcooUmnzV-Pq6jIZuVij6SDZ8sQsuu8ARzVvWmZjHhrC56ILU43f4GoGJyic0WDgf9k_n-HzeShxhGMsrR2ZuIrBYkk0-53oPlbjAkvYVsPmUhmU9vU0EzNMddFnS2X8GElsou12lW3S8MQ_4s20Y8X21d8qBp4OdKJc20QV-Z9v54EHaK1bqb-7xhlM85fWV-RXG7TIYTqLztCPgrcpS84nBAHQwK_M9eRSFEInqIKg7u9t2Pb4P-U29uJGsZCKT2IposLn0zZuT7QFQ6LgVHjXDOoZn5Cr59tX9Sja4jDs9aoxv947H5svRhW059kOiJ7UGXON888UFSIwV-ILxI7W6quWuEAtfO0P-R9791Skx4cqO7NGcyHmhTP4ZMjABSrZi6yd8xow3sOfmykOL8JhoPNEYy9tIpueA4it0YF3wK7qwXPeZY9WosBUH1H0dP4f_dwPU5PJQD0DN7qQN-L8");

    }

}
