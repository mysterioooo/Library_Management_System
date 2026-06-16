package com.library.dto;

import lombok.Data;

@Data
public class IssueBookRequest {

    private Long userId;

    private Long bookId;
}