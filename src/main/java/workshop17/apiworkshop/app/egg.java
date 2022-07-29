package workshop17.apiworkshop.app;

//https://www.baeldung.com/spring-resttemplate-json-list

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonReaderFactory;

public class egg {

	public String country;
	public String coordinates;
	
	@Autowired
	geoloc geoloc;
	
	@Autowired 
	weatherObj weatherObj;

	public egg(String country) {
		this.country = country;
		coordinates = getCoords(country);
	}

	public String getCoords(String country) {

		//Craft out request
		RestTemplate template = new RestTemplate();
		// RequestEntity<Void> request = RequestEntity
		// .get("http://api.openweathermap.org/geo/1.0/direct?q=Singapore&limit=5&appid=5996a2efa32ae55b6aa493b963bad792")
		// .accept(MediaType.APPLICATION_JSON)
		// .build();
		
		//Craft out response
		// ResponseEntity<geoloc> response = template.exchange(request, geoloc.class);

		ResponseEntity<geoloc[]> responseEntity =
   			template.getForEntity("http://api.openweathermap.org/geo/1.0/direct?q=" + country + "&limit=5&appid=5996a2efa32ae55b6aa493b963bad792",
						 geoloc[].class);
		geoloc[] objects = responseEntity.getBody();
		
		//Can also use for loop to loop through JSON object
		// for (geoloc geoloc : objects) {
		// 	System.out.println(geoloc.getCountry());
		// }

		geoloc = Arrays.stream(objects)
  				.collect(Collectors.toList()).get(0);

		System.out.println(geoloc.getName());
		System.out.println(geoloc.getCountry());
		System.out.println(geoloc.getLat() + ", " + geoloc.getLon());

		System.out.println(Arrays.stream(objects)
		.collect(Collectors.toList()).size());
		//Test printing out as String
		// ResponseEntity<String> responseEntity =
   		// template.getForEntity("http://api.openweathermap.org/geo/1.0/direct?q=Singapore&limit=5&appid=5996a2efa32ae55b6aa493b963bad792", String.class);
		// String temp = responseEntity.getBody().substring(1, responseEntity.getBody().length() - 1);
		// System.out.println(temp); //Works
		return geoloc.getLat() + ", " + geoloc.getLon();
	}

	public weatherObj getWeather(String coords) {
		String[] coordinates = coords.split(",");
		String latitude = coordinates[0].trim();
		String longitude = coordinates[1].trim();

		RestTemplate template = new RestTemplate();
		// String url = "https://api.openweathermap.org/data/2.5/weather?lat=1.2904753&lon=103.8520359&appid=5996a2efa32ae55b6aa493b963bad792";
		ResponseEntity<weatherObj> responseEntity =
			// template.getForEntity(url, weatherObj.class);
   			template.getForEntity(("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=5996a2efa32ae55b6aa493b963bad792"),
						 weatherObj.class);
		
		System.out.println(responseEntity.getStatusCode());
		System.out.println(responseEntity.getBody().getName());
		System.out.println(responseEntity.getBody().getCoord());
		System.out.println(responseEntity.getBody().getWeather());
		double temp = (double)responseEntity.getBody().getMain().get("temp");
		double temp2 = Double.parseDouble(temp+"");
		System.out.println(temp2 + " degrees celcius");
		return responseEntity.getBody();
	}
}