package com.supabase.controller;

import com.supabase.service.JWTService;
import com.supabase.service.SupabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/properties")
@Tag(name = "Properties", description = "Manage user property records")
public class PropertyController {

    @Autowired
    private SupabaseService supabaseService;

    @Autowired
    private JWTService jwtService;

    private static final String TABLE = "properties";

    @Operation(summary = "Get all properties owned by the user")
    @GetMapping
    public ResponseEntity<String> getMyProperties(HttpServletRequest request) {
        String userEmail = jwtService.extractEmailFromRequest(request);
        return supabaseService.fetchUserData(TABLE, "user_email", userEmail);
    }

    @Operation(summary = "Create a new property")
    @PostMapping
    public ResponseEntity<String> createProperty(@RequestBody Map<String, Object> property, HttpServletRequest request) {
        String userEmail = jwtService.extractEmailFromRequest(request);
        property.put("user_email", userEmail);
        return supabaseService.insertUserData(TABLE, property);
    }

    @Operation(summary = "Update a property by ID (must belong to user)")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProperty(@PathVariable String id, @RequestBody Map<String, Object> updates, HttpServletRequest request) {
        String userEmail = jwtService.extractEmailFromRequest(request);
        return supabaseService.updateUserDataWithMatch(TABLE, Map.of("id", id, "user_email", userEmail), updates);
    }

    @Operation(summary = "Delete a property by ID (must belong to user)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable String id, HttpServletRequest request) {
        String userEmail = jwtService.extractEmailFromRequest(request);
        return supabaseService.deleteUserDataWithMatch(TABLE, Map.of("id", id, "user_email", userEmail));
    }
}
