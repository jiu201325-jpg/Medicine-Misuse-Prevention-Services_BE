package com.example.medicines.service;

import com.example.medicines.entity.LinkCode;
import com.example.medicines.repository.LinkCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LinkCodeService {

    private final LinkCodeRepository linkCodeRepository;
    private final Random random = new Random();

    public String generateCode(Long elderId) {
        String code = String.format("%06d", random.nextInt(1_000_000));

        LinkCode linkCode = LinkCode.builder()
                .code(code)
                .elderId(elderId)
                .expiresAt(LocalDateTime.now().plusMinutes(10)) // 10분 유효
                .used(false)
                .build();

        linkCodeRepository.save(linkCode);
        return code;
    }
}