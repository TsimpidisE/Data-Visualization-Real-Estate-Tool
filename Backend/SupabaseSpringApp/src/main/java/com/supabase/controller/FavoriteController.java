package com.supabase.controller;

import com.supabase.service.JWTService;
import com.supabase.service.SupabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/favorites")
@Tag(name = "Favorites", description = "Manage favorite items per user")
public class FavoriteController {

    @Autowired
    private SupabaseService supabaseService;

    @Autowired
    private JWTService jwtService;

    private static final String TABLE = "favorites";

    @Operation(summary = "Get all favorites for user")
    @GetMapping
    public ResponseEntity<String> getFavorites(HttpServletRequest request) {
        String userEmail = jwtService.extractEmailFromRequest(request);
        return supabaseService.fetchUserData(TABLE, "user_email", userEmail);
    }

    @Operation(summary = "Add a new favorite item")
    @PostMapping("/{itemId}")
    public ResponseEntity<String> addFavorite(@PathVariable String itemId, HttpServletRequest request) {
        String userEmail = jwtService.extractEmailFromRequest(request);
        Map<String, Object> body = new HashMap<>();
        body.put("user_email", userEmail);
        body.put("item_id", itemId);
        return supabaseService.insertUserData(TABLE, body);
    }

    @Operation(summary = "Remove a favorite item by itemId")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteFavorite(@PathVariable String itemId, HttpServletRequest request) {
        String userEmail = jwtService.extractEmailFromRequest(request);
        // assuming composite unique key user_email + item_id
        return supabaseService.deleteUserDataWithMatch(TABLE, Map.of(
            "user_email", userEmail,
            "item_id", itemId
        ));
    }
}
