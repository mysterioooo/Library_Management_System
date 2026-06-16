package com.library.service;

import com.library.entity.Fine;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.FineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FineServiceImpl
        implements FineService {

    private final FineRepository fineRepository;

    @Override
    public List<Fine> getAllFines() {

        return fineRepository.findAll();
    }

    @Override
    public void payFine(Long fineId) {

        Fine fine =
                fineRepository.findById(fineId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Fine not found"));

        if(fine.getPaid()) {

            throw new RuntimeException(
                    "Fine already paid");
        }

        fine.setPaid(true);

        fineRepository.save(fine);
    }
}