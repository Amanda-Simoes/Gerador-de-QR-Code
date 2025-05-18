package com.qr.generator.controller;

import com.google.zxing.WriterException;
import com.qr.generator.dto.QrCodeGenerateRequest;
import com.qr.generator.dto.QrCodeGenerateResponse;
import com.qr.generator.service.QrCodeGeneratorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class QrCodeControllerTest {

    @InjectMocks
    QrCodeController qrCodeController;

    @Mock
    QrCodeGeneratorService qrCodeGeneratorService;

    @Test
    public void testGenerate() throws IOException, WriterException {
        // Create a sample request
        QrCodeGenerateRequest request = new QrCodeGenerateRequest("https://example.com");

        // Mock the service call
        Mockito.when(qrCodeGeneratorService.generateAndUploadQrCode(request.text()))
                .thenReturn(new QrCodeGenerateResponse("https://example.com"));

        // Call the generate method
        ResponseEntity<QrCodeGenerateResponse> response = qrCodeController.generate(request);

        // Verify the response
        assert response.getStatusCode().is2xxSuccessful();
    }

}
