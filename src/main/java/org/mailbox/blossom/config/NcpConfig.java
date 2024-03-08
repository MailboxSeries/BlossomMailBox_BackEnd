package org.mailbox.blossom.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class NcpConfig {
    @Value("${spring.cloud.aws.s3.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.s3.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.s3.region}")
    private String region;

    @Value("${spring.cloud.aws.s3.endpoint}")
    private String endPoint;

    @Value("${spring.cloud.aws.s3.credentials.bucket}")
    private String bucket;


    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }
}