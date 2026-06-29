package com.kqsf.global.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws")
public class AwsProperties {

    private String region;

//    private Credentials credentials = new Credentials();
//
//    @Getter
//    @Setter
//    public static class Credentials {
//        private String accessKey;
//        private String secretKey;
//    }
}
