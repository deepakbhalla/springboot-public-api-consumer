package com.example.consumer.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.consumer.model.Response;
import com.example.consumer.model.UserData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/api/v1")
public class Controller {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	Gson gson;

	@GetMapping("/health")
	public String checkHealth() {
		return "I am working fine";
	}

	@GetMapping("/details")
	public ResponseEntity<List<Response>> getDetails() {

		ResponseEntity<String> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/users",
				HttpMethod.GET, HttpEntity.EMPTY, String.class);

		// UserData[] userDataDetails = gson.fromJson(response.getBody(), UserData[].class);

		Type type = new TypeToken<ArrayList<UserData>>() {}.getType();
		ArrayList<UserData> apiData = gson.fromJson(response.getBody(), type);

		List<Response> filteredResponse = new ArrayList<Response>();
		for (UserData user : apiData) {
			Response res = new Response(user.getAddress().getGeo().getLat(), user.getAddress().getGeo().getLng(),
					user.getCompany().getName());
			filteredResponse.add(res);
		}

		return ResponseEntity.ok().body(filteredResponse);
	}
}
