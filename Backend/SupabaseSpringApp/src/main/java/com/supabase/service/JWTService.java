package com.supabase.service;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class JWTService {

    public String extractEmail(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getStringClaim("email");
        } catch (ParseException e) {
            return null;
        }
    }

    public String extractEmailFromRequest(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        String token = auth.substring(7);
        return extractEmail(token);
    }

    public boolean isValid(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getStringClaim("email") != null;
        } catch (Exception e) {
            return false;
        }
    }
}
