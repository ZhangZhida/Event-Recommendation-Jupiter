package ticketMasterClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;


public class TicketMasterClient {
	private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
	private static final String DEFAULT_KEYWORD = "event";
	private static final String API_KEY = "ZPqfRFBrHnZUspBp0DQnR0AvUgBckb9d";
	
	public JSONArray search(double lat, double lon, String keyword) {
		if(keyword == null) {
			keyword = DEFAULT_KEYWORD;
		}
		
		try {
			// some character need to be encoded, like Chinese, space need to be encoded. "hi there" => "hi20%there"
			keyword = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//query string
		String query = String.format("apikey=%s&latlong=%s,%s&keywork=%s&radius=%s", API_KEY, lat, lon, keyword, 50);
		String url = URL + "?" + query;
		
		try {
			// build http connection
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			
			// get response code, success if 200, otherwise failed.
			int responseCode = connection.getResponseCode();
			System.out.println(String.format("response code: %s", responseCode));
			if(responseCode != 200) {
				return new JSONArray();
			}
			
			// get response content
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();
			
			while((line = reader.readLine()) != null) {
				response.append(line);
			}
			JSONObject obj = new JSONObject(response.toString());
			
			if(!obj.isNull("_embedded")) {
				JSONObject embedded = obj.getJSONObject("_embedded");
				return embedded.getJSONArray("events");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return new JSONArray();
	}
	
	// test method
	private void testQuery(double lat, double lon) {
		JSONArray events = search(lat, lon, null);
		try {
			for(int i=0; i<events.length(); i++) {
				JSONObject obj = events.getJSONObject(i);
				System.out.println(obj.toString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TicketMasterClient client = new TicketMasterClient();
		client.testQuery(40.712776, -74.005974);
	}
}
