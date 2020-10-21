package com.rcrd.usermanager.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class UserLocationService implements UserLocationServiceI {

    private final String countryServiceURL = "https://ipapi.co/%s/country/";

    public String getCountryByIp(String ipAddress) {
        try {
            URL ipApi = new URL(String.format(countryServiceURL, ipAddress));
            URLConnection c = ipApi.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String country = reader.readLine();
            reader.close();
            return country;
        } catch (Exception e) {
            throw new RuntimeException("Error while resolving ip address");
        }
    }
}
