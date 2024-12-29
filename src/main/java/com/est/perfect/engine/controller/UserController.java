package com.est.perfect.engine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.est.perfect.engine.service.RecommendationService;
import com.est.perfect.engine.user.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
  
	@Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userId}/matches")
    public List<User> getMatches(@PathVariable Long userId, @RequestParam int topN) {
        return recommendationService.getTopMatches(userId, topN);
    }
}
