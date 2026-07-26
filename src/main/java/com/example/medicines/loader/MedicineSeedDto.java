package com.example.medicines.loader;

import java.util.List;

public record MedicineSeedDto(
        int id,
        String category,
        String ingredient,
        List<String> product_names,
        String name,
        String effect,
        String usage,
        String precautions,
        List<String> emergency_signs,
        List<String> interactions,
        String easy_explanation,
        String audio_url
) {}
