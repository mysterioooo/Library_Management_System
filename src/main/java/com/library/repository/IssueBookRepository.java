package com.library.repository;

import com.library.entity.IssueBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueBookRepository
        extends JpaRepository<IssueBook, Long> {

    List<IssueBook> findByUser_Id(Long userId);
}