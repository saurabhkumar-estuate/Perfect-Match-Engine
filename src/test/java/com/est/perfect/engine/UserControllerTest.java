package com.est.perfect.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.est.perfect.engine.controller.UserController;
import com.est.perfect.engine.service.RecommendationService;
import com.est.perfect.engine.user.User;

@SpringBootTest
public class UserControllerTest {
	
	@Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetMatches_ValidInput_ReturnsMatches() {
        // Mock recommendation service behavior
        Long userId = 1L;
        int topN = 2;
        List<User> expectedMatches = Arrays.asList(
                new User(3L, "User 3", "Male", 26, new String[]{"Movies", "Tennis", "Football", "Cricket"}),
                new User(2L, "User 2", "Male", 27, new String[]{"Cricket", "Football", "Movies"})
        );
        when(recommendationService.getTopMatches(userId, topN)).thenReturn(expectedMatches);

        // Call the controller method
        List<User> actualMatches = userController.getMatches(userId, topN);

        // Verify results
        assertEquals(expectedMatches, actualMatches);
    }

    @Test
    public void testGetMatches_InvalidUserId_ThrowsException() {
        // Mock recommendation service to throw exception
        Long invalidUserId = 10L;
        int topN = 2;
        when(recommendationService.getTopMatches(invalidUserId, topN)).thenThrow(new RuntimeException("User not found"));

        // Call the controller method and expect exception
        assertThrows(RuntimeException.class, () -> userController.getMatches(invalidUserId, topN));
    }

}
