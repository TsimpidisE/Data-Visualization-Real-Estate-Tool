package com.supabase.controller;

import com.supabase.model.SupabaseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User signup and login via Supabase Auth API")
public class AuthController {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon.key}")
    private String anonKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Operation(summary = "Sign up user", description = "Registers a user using Supabase signup endpoint")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SupabaseUser user) {
        String url = supabaseUrl + "/auth/v1/signup";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", anonKey);
        Map<String, Object> body = new HashMap<>();
        body.put("email", user.getEmail());
        body.put("password", user.getPassword());
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    @Operation(summary = "Log in user", description = "Authenticates a user using Supabase login endpoint")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SupabaseUser user) {
        String url = supabaseUrl + "/auth/v1/token?grant_type=password";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", anonKey);
        Map<String, Object> body = new HashMap<>();
        body.put("email", user.getEmail());
        body.put("password", user.getPassword());
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }
}
