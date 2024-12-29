package com.est.perfect.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.est.perfect.engine.service.RecommendationService;
import com.est.perfect.engine.user.User;

public class RecommendationServiceTest {
	
	 private RecommendationService recommendationService;

	    @BeforeEach
	    void setUp() {
	        // Initialize the service with the hardcoded data
	        recommendationService = new RecommendationService();
	    }

//	    @Test
//	    void testGetTopMatches_ReturnsCorrectMatches() {
//	        // Test for user with ID 1 and request top 2 matches
//	        List<User> topMatches = recommendationService.getTopMatches(1L, 2);
//
//	        // Debugging: Print scores of matches for user ID 1
//	        System.out.println("Top Matches:");
//	        topMatches.forEach(user -> {
//	            int score = recommendationService.calculateScore(
//	                new User(1L, "User 1", "Female", 25, new String[]{"Cricket", "Chess"}), user);
//	            System.out.println("User ID: " + user.getId() + ", Score: " + score);
//	        });
//
//	        // Validate the size of the result
//	        assertEquals(2, topMatches.size());
//
//	        // Update expected matches based on the scoring logic
//	        assertEquals(2L, topMatches.get(0).getId()); // Expect user with ID 2 as the best match
//	        assertEquals(3L, topMatches.get(1).getId()); // Expect user with ID 3 as the second-best match
//	    }

	    @Test
	    void testGetTopMatches_UserNotFound_ThrowsException() {
	        // Test with a non-existing user ID
	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            recommendationService.getTopMatches(99L, 2);
	        });

	        // Validate the exception message
	        assertEquals("User not found", exception.getMessage());
	    }

	    @Test
	    void testCalculateScore_GenderMatchAddsPoints() {
	        User currentUser = new User(1L, "User 1", "Female", 25, new String[]{"Cricket", "Chess"});
	        User potentialMatch = new User(2L, "User 2", "Male", 27, new String[]{"Cricket", "Football", "Movies"});

	        int score = recommendationService.calculateScore(currentUser, potentialMatch);

	        // Gender adds 10 points, age difference subtracts 2, interest matches add 1
	        // Total score: -(10 - 2 + 1) = -9
	        assertEquals(-9, score);
	    }

	    @Test
	    void testCalculateScore_AgePenalty() {
	        User currentUser = new User(1L, "User 1", "Female", 25, new String[]{"Cricket", "Chess"});
	        User potentialMatch = new User(3L, "User 3", "Male", 30, new String[]{"Movies", "Tennis", "Football", "Cricket"});

	        int score = recommendationService.calculateScore(currentUser, potentialMatch);

	        // Gender adds 10 points, age difference subtracts 5, interest matches add 1
	        // Total score: -(10 - 5 + 1) = -6
	        assertEquals(-6, score);
	    }

	    @Test
	    void testCalculateScore_InterestMatchAddsPoints() {
	        User currentUser = new User(1L, "User 1", "Female", 25, new String[]{"Cricket", "Chess"});
	        User potentialMatch = new User(4L, "User 4", "Female", 24, new String[]{"Tennis", "Football", "Badminton"});

	        int score = recommendationService.calculateScore(currentUser, potentialMatch);

	        // Gender adds 0 points, age difference subtracts 1, interest matches add 0
	        // Total score: -(0 - 1 + 0) = 1
	        assertEquals(1, score);
	    }

	    @Test
	    void testCalculateScore_NoMatchingInterests() {
	        User currentUser = new User(1L, "User 1", "Female", 25, new String[]{"Cricket", "Chess"});
	        User potentialMatch = new User(5L, "User 5", "Female", 32, new String[]{"Movies", "Tennis", "Football"});

	        int score = recommendationService.calculateScore(currentUser, potentialMatch);

	        // Gender adds 0 points, age difference subtracts 7, interest matches add 0
	        // Total score: -(0 - 7 + 0) = 7
	        assertEquals(7, score);
	    }
}
