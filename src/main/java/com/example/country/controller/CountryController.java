package com.example.country.controller;

import com.example.country.dto.Country;
import com.example.country.service.CountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

  @Autowired
  private CountryService countryService;

  @GetMapping
  public List<Country> fetchCities(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer population,
      @RequestParam(required = false) String sorting,
      @RequestParam(required = false) String pagination) {

    List<Country> countries = countryService.fetchAllCountries();

    return countries;
  }
}
