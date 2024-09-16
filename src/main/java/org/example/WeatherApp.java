package org.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WeatherApp {
    public static void main(String[] args) {
        String apiKey = ""; // Enter API Key
        String city = "Atlanta";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                String jsonResponse = responseBuilder.toString();

                // Parse JSON response
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String cityName = jsonObject.getString("name");
                double temp = jsonObject.getJSONObject("main").getDouble("temp");
                int humidity = jsonObject.getJSONObject("main").getInt("humidity");
                String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

                // Display weather data
                System.out.println("City: " + cityName);
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Weather: " + weatherDescription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
