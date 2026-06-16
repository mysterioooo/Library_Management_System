package com.library.controller;

import com.library.entity.Fine;
import com.library.qr.QRCodeService;
import com.library.repository.FineRepository;
import com.library.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;
    private final FineRepository fineRepository;
    private final QRCodeService qrCodeService;

    @GetMapping
    public ResponseEntity<List<Fine>>
    getAllFines() {

        return ResponseEntity.ok(
                fineService.getAllFines());
    }

    @PostMapping("/pay/{fineId}")
    public ResponseEntity<String>
    payFine(
            @PathVariable Long fineId) {

        fineService.payFine(fineId);

        return ResponseEntity.ok(
                "Fine Paid Successfully");
    }

    @GetMapping(
            value = "/qr/{fineId}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQR(
            @PathVariable Long fineId)
            throws Exception {

        Fine fine =
                fineRepository.findById(fineId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fine not found"));

        byte[] qrCode =
                qrCodeService.generateUPIQRCode(
                        fine.getAmount(),
                        fine.getId());

        return ResponseEntity.ok(qrCode);
    }

    @PostMapping("/pay-by-issue/{issueId}")
    public ResponseEntity<String> payByIssue(
            @PathVariable Long issueId){

        Fine fine = fineRepository
                .findByIssueBook_Id(issueId)
                .orElseThrow();

        fine.setPaid(true);

        fineRepository.save(fine);

        return ResponseEntity.ok(
                "Fine Paid Successfully. You can now return the book.");
    }

    @GetMapping("/user/{userId}")
    public List<Fine> getUserFines(
            @PathVariable Long userId){

        return fineRepository
                .findByIssueBook_User_Id(userId);
    }
}