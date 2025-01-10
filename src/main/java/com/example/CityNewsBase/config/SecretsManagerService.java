//package com.example.CityNewsBase.config;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
//import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
//
//@Configuration
//public class SecretsManagerService {
//
//    private static final String SECRET_NAME = "ApiKeys";
//    private static final Region REGION = Region.of("eu-north-1");
//
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//    private static JsonNode secretJson;
//
//    static {
//        String secretString = getSecret();
//        try {
//            secretJson = objectMapper.readTree(secretString);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse secret JSON", e);
//        }
//    }
//
//    public static String getSecret() {
//        SecretsManagerClient client = SecretsManagerClient.builder()
//                .region(REGION)
//                .build();
//
//        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
//                .secretId(SECRET_NAME)
//                .build();
//
//        GetSecretValueResponse getSecretValueResponse;
//
//        try {
//            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve secret", e);
//        }
//
//        return getSecretValueResponse.secretString();
//    }
//
//    public static String getSecretValue(String key) {
//        if (secretJson == null || !secretJson.has(key)) {
//            throw new RuntimeException("Key not found in secret: " + key);
//        }
//        return secretJson.get(key).asText();
//    }
//}
