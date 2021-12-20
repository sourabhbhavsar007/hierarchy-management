package com.personio.hierarchymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.personio.hierarchymanagement.security.SecurityConfig;

@SpringBootApplication
public class HierarchyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HierarchyManagementApplication.class, args);
	}

}
