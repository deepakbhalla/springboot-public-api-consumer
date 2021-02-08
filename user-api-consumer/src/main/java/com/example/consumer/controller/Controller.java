package com.example.consumer.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.example.consumer.model.UserDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/api/v1")
public class Controller {

	private static final String URL_1 = "https://jsonplaceholder.typicode.com/users";
	private static final String URL_2 = "https://jsonplaceholder.typicode.com/comments";

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	Gson gson;

	@GetMapping("/health")
	public String checkHealth() {
		return "I am working fine";
	}

	/**
	 * Reads the response from URL_1 and performs some filter operation and returns the data.
	 * @return
	 */
	@GetMapping("/details/url/1")
	public ResponseEntity<List<Response>> getDetails() {

		ResponseEntity<String> response = restTemplate.exchange(URL_1,
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
	
	@GetMapping("/details/url/2")
	public ResponseEntity<UserDetail[]> getFilteredDetails() {

		ResponseEntity<String> response = restTemplate.exchange(URL_2, HttpMethod.GET, HttpEntity.EMPTY, String.class);

		UserDetail[] details = gson.fromJson(response.getBody(), UserDetail[].class);

		/** Filter/update response, solution 1 (change response type to ResponseEntity<List<UserDetail>) */
		/*List<UserDetail> listUsers = new ArrayList<UserDetail>();
		for (UserDetail dt : details) {
			if (dt.getPostId() == 1) {
				dt.setBody("Id ".concat(String.valueOf(dt.getId())));
				listUsers.add(dt);
			}
		}*/

		/** Filter/update response, solution 2 (change response type to ResponseEntity<List<UserDetail>) */
		/* List<UserDetail> listUsers = 
		 		Arrays.asList(details).stream().filter(u -> (u.getPostId() == 1)).collect(Collectors.toList());
		   listUsers.stream().forEach(a -> a.setBody("Id ".concat(String.valueOf(a.getId())))); 
		*/
		
		/** Filter/update response, solution 3 */
		Arrays.asList(details).stream().filter(u -> (u.getPostId() == 1))
			.forEach(a -> a.setBody("Id ".concat(String.valueOf(a.getId()))));

		return ResponseEntity.ok().body(details);
	}
}
