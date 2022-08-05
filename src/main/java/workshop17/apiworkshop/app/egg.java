package workshop17.apiworkshop.app;

//https://www.baeldung.com/spring-resttemplate-json-list

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonReaderFactory;
import workshop17.apiworkshop.models.currConverter;
import workshop17.apiworkshop.models.geoloc;
import workshop17.apiworkshop.models.googCode;
import workshop17.apiworkshop.models.trafficCamObj;
import workshop17.apiworkshop.models.weatherObj;

public class egg {

	public String country;
	public String coordinates;
	private String googleApiKey = System.getenv("GOOGLE_API_KEY");;
	private String currApiKey = System.getenv("FIXER_API_KEY");
	private String weatherApiKey = System.getenv("WEATHER_API_KEY");

	@Autowired
	geoloc geoloc;
	
	@Autowired 
	weatherObj weatherObj;

	@Autowired
	trafficCamObj trafficCamObj;

	@Autowired
	googCode googCode;

	public egg(String country) {
		this.country = country;
		coordinates = getCoords(country);
	}

	public egg(){

	}

	public String getCoords(String country) {

		//Craft out request
		RestTemplate template = new RestTemplate();
		
		//Craft out response
		// ResponseEntity<geoloc> response = template.exchange(request, geoloc.class);

		ResponseEntity<geoloc[]> responseEntity =
   			template.getForEntity("http://api.openweathermap.org/geo/1.0/direct?q=" + country + "&limit=5&appid=" + weatherApiKey,
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
		return geoloc.getLat() + ", " + geoloc.getLon();
	}

	public weatherObj getWeather(String coords) {
		String[] coordinates = coords.split(",");
		String latitude = coordinates[0].trim();
		String longitude = coordinates[1].trim();

		RestTemplate template = new RestTemplate();
		ResponseEntity<weatherObj> responseEntity =
   			template.getForEntity(("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + weatherApiKey),
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

	public currConverter convertCurrency(String to, String from, String amount) {
		//Start a Rest Template
		String url = "https://api.apilayer.com/fixer/convert?to=" + to + "&from=" + from + "&amount=" + amount;
		RestTemplate template = new RestTemplate();
		RequestEntity<Void> request = RequestEntity.get(url)
				.header("apikey", currApiKey) //We can send over a Header key and value
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<currConverter> response = template.exchange(request, currConverter.class);
		System.out.println(response.getBody().getResult());
		return response.getBody();
	}

	public HashMap getTrafficImage(String option) {
		String url = "https://api.data.gov.sg/v1/transport/traffic-images";
		RestTemplate template = new RestTemplate();
		ResponseEntity<trafficCamObj> responseEntity = 
			template.getForEntity(url, trafficCamObj.class);
		trafficCamObj = responseEntity.getBody().getItems().get(0);
		HashMap temp = responseEntity.getBody().getItems().get(0).getCameras().get(0);
		
		ArrayList<HashMap> cameras = new ArrayList<>();
		cameras = trafficCamObj.getCameras();
		int count = 0;
		ArrayList<HashMap> cameraHash = new ArrayList<>();
		for (HashMap camera : cameras) { //timestamp, image, location, camera_id, image_metadata
			HashMap location = (HashMap)camera.get("location");
			String latitude = location.get("latitude") + "";
			String longitude = location.get("longitude") + "";
			String camId = (String)camera.get("camera_id");
			String image = (String)camera.get("image");

			camera.put("camId", camId);
			camera.put("lat", latitude);
			camera.put("long", longitude);
			camera.put("coords", latitude+","+longitude);
			camera.put("image", image);
			cameraHash.add(camera);
			System.out.println("Camera Id: " + camera.get("camId") + " | Timestamp: " + camera.get("timestamp") + " | Latitude: " + camera.get("lat") + " | Longitude: " + camera.get("long") + " | " + camera.get("coords") + " | image: " + image);
			count++;
		}
		System.out.println("Images: " + count);

		for (HashMap camera : cameraHash) {
			if(option.contains(camera.get("camId").toString()))
				return camera; //return the whole HashMap //camId, lat, long, coords, image, timestamp
		}

		// getGoogleLocation(cameraHash); //Used to populate egg.txt, deprecated
		// System.out.println(cameraHash.toString());

		return cameras.get(0);
	}

	// public void getGoogleLocation(ArrayList<HashMap> cameraHash) {

	// 	RestTemplate template = new RestTemplate();
	// 	PrintWriter pWriter = null;
	// 	try {
			

	// 		File myObj = new File("./src/main/resources/static/egg.txt");
	// 		if(!myObj.exists())
	// 			myObj.createNewFile();
	// 		FileWriter fWriter = new FileWriter(myObj);
	// 		pWriter = new PrintWriter(fWriter);

	// 		for (HashMap hashMap : cameraHash) {
	// 			String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + hashMap.get("coords") + "&key=" + googleApiKey;
	// 			ResponseEntity<googCode> responseEntity = 
	// 				template.getForEntity(url, googCode.class);
	// 			ArrayList<HashMap> results = responseEntity.getBody().getResults(); //each Google location that we retrieved
	// 			ArrayList<String> toPrint = new ArrayList<>();
				
	// 			for (HashMap addressComponent : results) {
	// 				ArrayList<String> types = new ArrayList<>();

	// 				types = (ArrayList<String>)addressComponent.get("types");
	// 				HashMap geometry = (HashMap) addressComponent.get("geometry");

	// 				if(!types.contains("plus_code") && geometry.get("location_type").equals("GEOMETRIC_CENTER") || geometry.get("location_type").equals("ROOFTOP")){
	// 					toPrint.add((String)addressComponent.get("formatted_address"));				

	// 				}
					

	// 			}
	// 			System.out.println(hashMap.get("camId") + ": " + toPrint.get(toPrint.size() - 1));
	// 			System.out.println("Nearby Locations: ");
	// 			pWriter.println(hashMap.get("camId") + ": " + toPrint.get(toPrint.size() - 1));
	// 			pWriter.println("Nearby Locations: ");

	// 			for (int i = 0; i < toPrint.size() - 1; i++) {
	// 				System.out.println(toPrint.get(i));
	// 				pWriter.println(toPrint.get(i));
	// 			}

	// 			System.out.println("");
	// 			pWriter.println("~");
				

	// 		}

			
	// 		pWriter.close();

	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }

}