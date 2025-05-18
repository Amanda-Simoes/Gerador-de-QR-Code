package com.qr.generator.service;


import com.google.zxing.WriterException;
import com.qr.generator.dto.QrCodeGenerateResponse;
import com.qr.generator.ports.StoragePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class QrCodeGeneratorServiceTest {

    private StoragePort storagePort;
    private QrCodeGeneratorService qrCodeGeneratorService;

    @BeforeEach
    void setUp() {
        storagePort = mock(StoragePort.class);
        qrCodeGeneratorService = new QrCodeGeneratorService(storagePort);
    }

    @Test
    void testGenerateAndUploadQrCode() throws WriterException, IOException {
        // Create a sample input text
        String inputText = "https://exemple.com";
        String fakeUrl = "https://s3.fake-url/qr-code.png";

        // Mock the behavior of the storagePort
        when(storagePort.uploadFile(any(byte[].class), anyString(), eq("image/png"))).thenReturn(fakeUrl);

        QrCodeGenerateResponse response = qrCodeGeneratorService.generateAndUploadQrCode(inputText);

        // Verify that the uploadFile method was called with the correct parameters
        assertEquals(fakeUrl, response.url());

        // Verify that the uploadFile method was called once
        verify(storagePort, times(1)).uploadFile(any(byte[].class), anyString(), eq("image/png"));

        // Verify that the response is not null and contains the expected URL
        assertNotNull(response);
        assertNotNull(response.url());
    }

}
