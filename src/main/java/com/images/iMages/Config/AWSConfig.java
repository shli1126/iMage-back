package com.images.iMages.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfig {
    @Value("${ACCESS_KEY}")
    private String ACCESS_KEY;
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${REGION}")
    private String REGION;

    @Bean
    public S3Client s3() {
        AwsCredentials credentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        return S3Client.builder()
                .region(Region.of(REGION))
                .credentialsProvider(() ->credentials)
                .build();
    }

}
