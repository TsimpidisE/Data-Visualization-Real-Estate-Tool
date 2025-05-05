package com.supabase.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerRoleTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static final String JWT_USER =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
            "eyJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJyb2xlIjoidXNlciJ9." +
            "rNvWZ0TwvZx5VDlEuus7v_cHn7ZrC5O2RoWiHL7D0So";

    private static final String JWT_ADMIN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
            "eyJlbWFpbCI6ImFkbWluQGV4YW1wbGUuY29tIiwicm9sZSI6ImFkbWluIn0." +
            "XsulfdOaUZjlVh3aSCtsBlRXJwMwBtTSosRz3EylYec";

    @Test
    public void testAdminAccessDeniedForUserRole() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + JWT_USER);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/user/admin",
                HttpMethod.GET,
                request,
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testAdminAccessAllowedForAdminRole() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + JWT_ADMIN);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/user/admin",
                HttpMethod.GET,
                request,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("You are an admin.", response.getBody());
    }
}
