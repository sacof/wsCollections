package com.collections.app.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONArray;

@Component
public class CallRestService {

	
	
	private static HttpHeaders createHttpHeaders(String user)
	{
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("client_id", "ejcxMYgmWK3jRhq90P3UkVUDWWtsJ50DRCSqn4b_p10");
	    headers.add("Authorization", "Client-ID "+user);
	    return headers;
	}
	
	public static JSONArray callRestService() {
		RestTemplate restTemplate = new RestTemplate();
		String theUrl = "https://api.unsplash.com/collections/";
		JSONArray listado = null;
		try {
			HttpHeaders headers = createHttpHeaders("ejcxMYgmWK3jRhq90P3UkVUDWWtsJ50DRCSqn4b_p10");
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			ResponseEntity<JSONArray> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, JSONArray.class);
			if (response.hasBody())
			{
				listado = response.getBody();
				
			}
			System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
		}
		catch (Exception eek) {
			System.out.println("** Exception: "+ eek.getMessage());
		}
		return listado;
		
	}

}
