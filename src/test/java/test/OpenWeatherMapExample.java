package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class OpenWeatherMapExample {
    public static void main(String[] args) {
        String apiKey = "API_KEY";
        
        try {
            // Step 1: Get the current weather details of Hyderabad
            String initialWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=hyderabad&appid=" + apiKey;
            JSONObject initialResponse = getWeatherData(initialWeatherUrl);

            // Check if the initial response is successful
            if (initialResponse != null) {
                // Extract latitude and longitude from the initial response
                double latitude = initialResponse.getJSONObject("coord").getDouble("lat");
                double longitude = initialResponse.getJSONObject("coord").getDouble("lon");

                // Step 2: Use latitude and longitude to get weather details again
                String secondWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
                JSONObject secondResponse = getWeatherData(secondWeatherUrl);

                // Verify specific attributes in the second response
                String name = secondResponse.getString("name");
                String country = secondResponse.getJSONObject("sys").getString("country");
                double tempMin = secondResponse.getJSONObject("main").getDouble("temp_min");
                double temp = secondResponse.getJSONObject("main").getDouble("temp");

                // Check the conditions you specified
                if (name.equals("Hyderabad") && country.equals("IN") && tempMin > 0 && temp > 0) {
                    System.out.println("Verification successful.");
                } else {
                    System.out.println("Verification failed.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper function to send HTTP GET request and return JSON response
    public static JSONObject getWeatherData(String weatherUrl) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(weatherUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        connection.disconnect();
        return new JSONObject(result.toString());
    }
}
