package org.fatec.esportiva.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient geminiRestClient(RestClient.Builder builder, @Value("${ai.api.baseurl}") String url){
        return builder.baseUrl(url).build();
    }
}
