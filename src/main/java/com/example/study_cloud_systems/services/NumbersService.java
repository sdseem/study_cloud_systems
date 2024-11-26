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
    @Value("${service.parameters.max-number}")
    private Long maxServiceNumber;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Optional<Long> processNumber(Long number) throws IllegalArgumentException {
        if (number < 0 || number > maxServiceNumber) {
            log.error("Provided number must be in range of 0 and {}", maxServiceNumber);
            return Optional.empty();
        }
        NumbersEntity providedNum = new NumbersEntity(number);
        NumbersEntity incrementedNum = new NumbersEntity(number + INC_STEP);
        Set<NumbersEntity> numbersToProcess = Set.of(providedNum, incrementedNum);
        Set<Long> idsToProcess = numbersToProcess.stream().map(NumbersEntity::getId).collect(Collectors.toUnmodifiableSet());

        Set<NumbersEntity> exists = numbersRepository.findAllByIdIn(idsToProcess);

        if (exists.contains(providedNum)) {
            log.error("Provided already used number {}", number);
            return Optional.empty();
        } else if (!exists.isEmpty()) {
            log.error("Provided number {} not used, but incremented number {} already used", number, incrementedNum);
            return Optional.empty();
        } else {
            numbersRepository.save(new NumbersEntity(number));
            return Optional.of(number + INC_STEP);
        }
    }
}
