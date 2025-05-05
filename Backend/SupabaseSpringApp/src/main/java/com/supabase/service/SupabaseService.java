package com.supabase.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.StringJoiner;

@Service
public class SupabaseService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service.key}")
    private String serviceRoleKey;

    public RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> fetchUserData(String table, String field, String value) {
        String url = supabaseUrl + "/rest/v1/" + table + "?" + field + "=eq." + value;
        return restTemplate.exchange(url, HttpMethod.GET, buildEntity(), String.class);
    }

    public ResponseEntity<String> insertUserData(String table, Map<String, Object> body) {
        String url = supabaseUrl + "/rest/v1/" + table;
        return restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), String.class);
    }

    public ResponseEntity<String> updateUserDataWithMatch(String table, Map<String, Object> match, Map<String, Object> updates) {
        StringJoiner conditions = new StringJoiner("&");
        for (Map.Entry<String, Object> entry : match.entrySet()) {
            conditions.add(entry.getKey() + "=eq." + entry.getValue());
        }
        String url = supabaseUrl + "/rest/v1/" + table + "?" + conditions.toString();
        return restTemplate.exchange(url, HttpMethod.PATCH, buildEntity(updates), String.class);
    }

    public ResponseEntity<String> deleteUserDataWithMatch(String table, Map<String, Object> match) {
        StringJoiner conditions = new StringJoiner("&");
        for (Map.Entry<String, Object> entry : match.entrySet()) {
            conditions.add(entry.getKey() + "=eq." + entry.getValue());
        }
        String url = supabaseUrl + "/rest/v1/" + table + "?" + conditions.toString();
        return restTemplate.exchange(url, HttpMethod.DELETE, buildEntity(), String.class);
    }

    private HttpEntity<String> buildEntity() {
        HttpHeaders headers = baseHeaders();
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Map<String, Object>> buildEntity(Map<String, Object> body) {
        HttpHeaders headers = baseHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Prefer", "return=representation");
        return new HttpEntity<>(body, headers);
    }

    private HttpHeaders baseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", serviceRoleKey);
        headers.set("Authorization", "Bearer " + serviceRoleKey);
        return headers;
    }
}
