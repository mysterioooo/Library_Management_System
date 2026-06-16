package com.library.controller;

import com.library.dto.IssueBookRequest;
import com.library.entity.IssueBook;
import com.library.repository.IssueBookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueBookController {

    private final BookService service;
    private final IssueBookRepository repository;

    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @PostMapping
    public ResponseEntity<String> issueBook(
            @RequestBody IssueBookRequest request){

        service.issueBook(request);

        return ResponseEntity.ok(
                "Book Issued");
    }

    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @PostMapping("/return/{issueId}")
    public ResponseEntity<?> returnBook(
            @PathVariable Long issueId){

        try{

            service.returnBook(issueId);

            return ResponseEntity.ok(
                    "Book Returned");

        }catch(RuntimeException ex){

            return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','MEMBER')")
    @GetMapping("/history/{userId}")
    public List<IssueBook> history(
            @PathVariable Long userId){

        return repository.findByUser_Id(userId);
    }

    @GetMapping
    public List<IssueBook> getAllIssues() {

        System.out.println("Loading issues");

        List<IssueBook> issues =
                repository.findAll();

        System.out.println(
                "Count = " + issues.size());

        return issues;
    }

    @GetMapping("/whoami")
    public String whoami(
            org.springframework.security.core.Authentication auth){

        return auth.getAuthorities().toString();
    }
}