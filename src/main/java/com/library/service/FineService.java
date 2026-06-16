package com.library.service;

import com.library.entity.Fine;

import java.util.List;

public interface FineService {

    List<Fine> getAllFines();

    void payFine(Long fineId);
}