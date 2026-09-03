package com.example.medicines.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "medicines")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String ingredient;
    private String name;

    @ElementCollection
    @CollectionTable(name = "medicine_product_names", joinColumns = @JoinColumn(name = "medicine_id"))
    @Column(name = "product_name")
    private List<String> productNames;

    @Column(columnDefinition = "TEXT")
    private String effect;

    @Column(name = "usage_text", columnDefinition = "TEXT")
    private String usage;

    @Column(columnDefinition = "TEXT")
    private String precautions;

    @ElementCollection
    @CollectionTable(name = "medicine_emergency_signs", joinColumns = @JoinColumn(name = "medicine_id"))
    @Column(name = "sign")
    private List<String> emergencySigns;

    @ElementCollection
    @CollectionTable(name = "medicine_interactions", joinColumns = @JoinColumn(name = "medicine_id"))
    @Column(name = "interaction")
    private List<String> interactions;

    @Column(columnDefinition = "TEXT")
    private String easyExplanation;

    private String audioUrl;

    public void updateEasyExplanation(String easyExplanation) {
        this.easyExplanation = easyExplanation;
    }
}