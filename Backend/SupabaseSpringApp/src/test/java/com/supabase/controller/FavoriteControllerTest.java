package com.supabase.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FavoriteControllerTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static final String JWT_USER =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
        "eyJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJyb2xlIjoidXNlciJ9." +
        "rNvWZ0TwvZx5VDlEuus7v_cHn7ZrC5O2RoWiHL7D0So";

    @Test
    public void testAddAndDeleteFavorite() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_USER);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Add item
        ResponseEntity<String> addResp = restTemplate.exchange("/favorites/abc123", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, addResp.getStatusCode());

        // Delete item
        ResponseEntity<String> delResp = restTemplate.exchange("/favorites/abc123", HttpMethod.DELETE, entity, String.class);
        assertTrue(delResp.getStatusCode().is2xxSuccessful());
    }
}
