package com.example.country.service;

import com.example.country.dto.Country;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

  private static final String API_URL = "https://restcountries.com/v3.1/all";

  public List<Country> fetchAllCountries() {
    try {
      URL url = new URL(API_URL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      String output;
      StringBuilder response = new StringBuilder();
      while ((output = br.readLine()) != null) {
        response.append(output);
      }

      conn.disconnect();

      // Parse the JSON string to POJOs
      List<Country> countries = parseJsonToCountries(response.toString());

      return countries;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private List<Country> parseJsonToCountries(String json) {
    List<Country> countries = new ArrayList<>();
    // Splitting by '},{' might work for demonstration purposes, but it's not a safe method for parsing JSON.
    String[] countryData = json.split("},\\{");
    for (String data : countryData) {
      Country country = new Country();
      // You would then need to scan `data` string to extract and set each property.
      // For example:
      String name = extractValue(data, "name");
      // ... do this for each property
      countries.add(country);
    }
    return countries;
  }

  private String extractValue(String data, String key) {
    // Very rudimentary method to extract value based on key. This won't handle nested objects or arrays.
    int keyIndex = data.indexOf(key);
    if (keyIndex != -1) {
      int startIndex = data.indexOf(":", keyIndex) + 1;
      int endIndex = data.indexOf(",", keyIndex);
      return data.substring(startIndex, endIndex).trim();
    }
    return null;
  }

}
