package com.cardss.service;

import com.cardss.model.Car;
import com.cardss.util.UserPreferences;

import java.util.ArrayList;
import java.util.List;

public class RecommendationEngine {

    public static List<Car> recommend(List<Car> cars, UserPreferences prefs, int topN) {
        if (cars == null || cars.isEmpty()) return new ArrayList<>();
        if (prefs == null) prefs = new UserPreferences();

        List<Car> scored = new ArrayList<>();

        for (Car car : cars) {
            double score = 0;
            String reason = "";

            if (prefs.getBudget() <= 0 || car.getSellingPrice() <= prefs.getBudget()) {
                score += 40;
                reason += "within budget, ";
            }

            if ("Any".equalsIgnoreCase(prefs.getFuelType()) || prefs.getFuelType().equalsIgnoreCase(car.getFuel())) {
                score += 20;
                reason += car.getFuel() + " fuel, ";
            }

            if ("Any".equalsIgnoreCase(prefs.getTransmission()) || prefs.getTransmission().equalsIgnoreCase(car.getTransmission())) {
                score += 20;
                reason += car.getTransmission() + " transmission, ";
            }

            if (car.getSeats() >= prefs.getMinSeats()) {
                score += 20;
            } else {
                reason += "not enough seats, ";
            }

            if (reason.endsWith(", ")) reason = reason.substring(0, reason.length() - 2);
            if (reason.isEmpty()) reason = "Partial match";

            car.setDssScore(score);
            car.setExplanation(reason);
            scored.add(car);
        }

        for (int i = 0; i < scored.size() - 1; i++) {
            for (int j = 0; j < scored.size() - i - 1; j++) {
                if (scored.get(j).getDssScore() < scored.get(j + 1).getDssScore()) {
                    Car temp = scored.get(j);
                    scored.set(j, scored.get(j + 1));
                    scored.set(j + 1, temp);
                }
            }
        }

        return scored.subList(0, Math.min(topN, scored.size()));
    }
}
