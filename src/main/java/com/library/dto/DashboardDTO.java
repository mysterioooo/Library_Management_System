package com.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDTO {

    private long totalBooks;

    private long totalUsers;

    private long totalIssuedBooks;

    private long totalFines;
}