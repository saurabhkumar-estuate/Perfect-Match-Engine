package com.est.perfect.engine.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	
	private Long id;
    private String name;
    private String gender;
    private int age;
    private String[] interests;

}
