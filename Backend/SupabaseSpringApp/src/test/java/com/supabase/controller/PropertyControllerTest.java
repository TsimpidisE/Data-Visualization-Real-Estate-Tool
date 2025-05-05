package com.supabase.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyControllerTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static final String JWT_USER =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
        "eyJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJyb2xlIjoidXNlciJ9." +
        "rNvWZ0TwvZx5VDlEuus7v_cHn7ZrC5O2RoWiHL7D0So";

    @Test
    public void testCreateProperty() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_USER);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Test Home");
        payload.put("location", "Nowhere");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/properties", request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
