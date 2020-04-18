package com.dwshu.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author dwshu
 * @create 2020/4/17
 * @describe
 */
@Configuration
public class ElasticsearchClientConfig {
    //容器任选其一就行

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client=new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"))
        );
        return client;
    }

    @Bean
    public RestHighLevelClient restClient(){
       return RestClients.create(ClientConfiguration.localhost()).rest();
    }

    @Bean
    public RestHighLevelClient client(){
        ClientConfiguration conf = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        return RestClients.create(conf).rest();
    }

}
