package com.library.repository;

import com.library.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FineRepository
        extends JpaRepository<Fine,Long> {
    Optional<Fine> findByIssueBook_Id(Long issueId);

    List<Fine> findByIssueBook_User_Id(Long userId);
}