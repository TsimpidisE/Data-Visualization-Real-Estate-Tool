package com.supabase.controller;

import com.supabase.model.LoanRequest;
import com.supabase.model.LoanResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanControllerTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testCalculateLoan() {
        LoanRequest request = new LoanRequest();
        request.setLoanAmount(300000);
        request.setInterestRate(3.5);
        request.setLoanTerm(30);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoanRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<LoanResponse> response = restTemplate.postForEntity("http://localhost:8080/loan/calculate", requestEntity, LoanResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMonthlyPayment() > 0);
    }

    @Test
    public void testCalculateLoan_InvalidInput() {
        LoanRequest request = new LoanRequest();
        request.setLoanAmount(0);        // invalid
        request.setInterestRate(0);      // invalid
        request.setLoanTerm(0);          // invalid

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoanRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8080/loan/calculate", requestEntity, Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();

        assertTrue(errors.containsKey("loanAmount"));
        assertTrue(errors.containsKey("interestRate"));
        assertTrue(errors.containsKey("loanTerm"));
    }
}
