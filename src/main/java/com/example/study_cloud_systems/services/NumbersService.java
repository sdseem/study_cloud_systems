package com.example.study_cloud_systems.services;

import com.example.study_cloud_systems.dto.entity.NumbersEntity;
import com.example.study_cloud_systems.repositories.NumbersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumbersService {
    private static final long INC_STEP = 1L;
    private final NumbersRepository numbersRepository;
//    @Value("${service.parameters.max-number}")
//    private Long maxServiceNumber;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Optional<Long> processNumber(Long number) throws IllegalArgumentException {
        Set<NumbersEntity> numbersTooProcess = Set.of(new NumbersEntity(number), new NumbersEntity(number + INC_STEP));
        Set<Long> idsToProcess = numbersTooProcess.stream().map(NumbersEntity::getId).collect(Collectors.toUnmodifiableSet());

        boolean exists = numbersRepository.allExistsByIds(idsToProcess);

        if (exists) {
            log.error("Provided already used number {}", number);
            return Optional.empty();
        } else {
            numbersRepository.save(new NumbersEntity(number));
            return Optional.of(number + INC_STEP);
        }
    }
}
