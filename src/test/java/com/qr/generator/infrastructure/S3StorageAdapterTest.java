package com.qr.generator.infrastructure;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class S3StorageAdapterTest {

    @Autowired
    S3StorageAdapter s3StorageAdapter;

    S3Client s3Client;

    private final static String FILE_NAME = "fileName";
    private final static String CONTENT_TYPE = "image/png";

    private final static String TEST = "test";
    private final static String BUCKET_NAME = "bucketName";
    private final static String REGION = "us-east-1";

    @BeforeEach
    void setUp() {
        s3Client = mock(S3Client.class);
        S3ClientBuilder mockBuilder = mock(S3ClientBuilder.class);

        when(mockBuilder.region(any(Region.class))).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(s3Client);

        try (MockedStatic<S3Client> mockedStatic = mockStatic(S3Client.class)) {
            mockedStatic.when(S3Client::builder).thenReturn(mockBuilder);

            s3StorageAdapter = new S3StorageAdapter(REGION, BUCKET_NAME);
        }
    }

    @Test
    void testUploadFile() {
        // Create a sample request
        byte[] fileData = TEST.getBytes();

        // Call the uploadFile method
        String resultUrl = s3StorageAdapter.uploadFile(fileData, FILE_NAME, CONTENT_TYPE);

        // Verify the URL format
        Assertions.assertNotNull(resultUrl);
    }

}
