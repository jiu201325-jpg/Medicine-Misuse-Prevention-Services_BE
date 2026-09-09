package com.example.medicines.config;

import com.example.medicines.entity.InteractionRule;
import com.example.medicines.repository.InteractionRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InteractionRuleDataLoader {

    @Bean
    public CommandLineRunner loadInteractionRules(InteractionRuleRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        InteractionRule.builder()
                                .drugAId(101L)
                                .drugBId(202L)
                                .riskLevel("HIGH")
                                .warningMessage("약물 A와 약물 B는 병용 시 심각한 간 독성 위험이 있습니다.")
                                .build(),
                        InteractionRule.builder()
                                .drugAId(303L)
                                .drugBId(404L)
                                .riskLevel("MODERATE")
                                .warningMessage("약물 C와 약물 D는 병용 시 졸음을 유발할 수 있습니다.")
                                .build()
                ));
            }
        };
    }
}