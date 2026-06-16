package com.library.controller;

import com.library.dto.DashboardDTO;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService service;

    @GetMapping
    public DashboardDTO dashboard() {

        return service.dashboard();
    }
}