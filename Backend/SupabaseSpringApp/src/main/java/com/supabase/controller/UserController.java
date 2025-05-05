package com.supabase.controller;

import com.supabase.service.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "Endpoints for authenticated user info and admin access")
public class UserController {

    @Autowired
    private JWTService jwtService;

    @Operation(summary = "Get authenticated user info", description = "Returns email from valid JWT token")
    @GetMapping("/me")
    public ResponseEntity<String> getMe(
        @Parameter(description = "Bearer token", required = true)
        @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header.");
        }

        String token = authHeader.substring(7);
        if (!jwtService.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        String email = jwtService.extractEmail(token);
        return ResponseEntity.ok("Authenticated as: " + email);
    }

    @Operation(summary = "Admin-only route", description = "Accessible only by users with `admin` role in JWT")
    @GetMapping("/admin")
    public ResponseEntity<String> adminRoute() {
        return ResponseEntity.ok("You are an admin.");
    }
}
