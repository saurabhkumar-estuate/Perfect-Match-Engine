package com.est.perfect.engine.service;

import com.est.perfect.engine.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<User> users;

    // Hardcoded users
    public RecommendationService() {
        this.users = new ArrayList<>(List.of(
            new User(1L, "User 1", "Female", 25, new String[]{"Cricket", "Chess"}),
            new User(2L, "User 2", "Male", 27, new String[]{"Cricket", "Football", "Movies"}),
            new User(3L, "User 3", "Male", 26, new String[]{"Movies", "Tennis", "Football", "Cricket"}),
            new User(4L, "User 4", "Female", 24, new String[]{"Tennis", "Football", "Badminton"}),
            new User(5L, "User 5", "Female", 32, new String[]{"Cricket", "Football", "Movies", "Badminton"})
        ));
    }

    // Get top matches for a user
    public List<User> getTopMatches(Long userId, int topN) {
        // Find the current user
        User currentUser = users.stream()
            .filter(user -> user.getId().equals(userId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Sort users by score and get top N matches
        return users.stream()
            .filter(user -> !user.getId().equals(userId))  // Exclude the current user
            .sorted(Comparator.comparingInt(user -> calculateScore(currentUser, user)))  // Sort by calculated score
            .limit(topN)  // Get top N matches
            .collect(Collectors.toList());
    }

    // Change the method visibility to public
    public int calculateScore(User currentUser, User potentialMatch) {
        int score = 0;

        // Gender rule: Prioritize opposite gender
        if (!currentUser.getGender().equals(potentialMatch.getGender())) {
            score += 10;  // Add 10 points for opposite gender match
        }

        // Age rule: Penalize based on age difference
        int ageDifference = Math.abs(currentUser.getAge() - potentialMatch.getAge());
        score -= ageDifference;  // Penalize age difference directly

        // Interest rule: Count number of matching interests
        long interestMatches = Arrays.stream(currentUser.getInterests())
                .filter(interest -> Arrays.asList(potentialMatch.getInterests()).contains(interest))
                .count();
        score += interestMatches;  // Add points for each matching interest

        // Return the negated score for sorting in descending order (higher score is better)
        return -score;
    }
}
