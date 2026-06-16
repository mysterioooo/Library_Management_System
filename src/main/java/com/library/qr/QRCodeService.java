package com.library.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QRCodeService {

    public byte[] generateUPIQRCode(
            Double amount,
            Long fineId)
            throws Exception {

        String upiId =
                "shubhamdube.akole@oksbi";

        String payeeName =
                "Shubham Dube";

        String upiUrl =
                "upi://pay"
                        + "?pa=" + upiId
                        + "&pn=" + payeeName
                        + "&am=" + amount
                        + "&cu=INR"
                        + "&tn=Fine Payment #" + fineId;

        QRCodeWriter writer =
                new QRCodeWriter();

        BitMatrix matrix =
                writer.encode(
                        upiUrl,
                        BarcodeFormat.QR_CODE,
                        300,
                        300);

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(
                matrix,
                "PNG",
                outputStream);

        return outputStream.toByteArray();
    }
}