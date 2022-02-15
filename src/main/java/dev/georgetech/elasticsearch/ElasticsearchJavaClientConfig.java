package dev.georgetech.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchJavaClientConfig {

  @Value("${elasticsearch.host}")
  private String host;
  @Value("${elasticsearch.port}")
  private int port;


  @Bean
  public ElasticsearchClient elasticsearchJavaClient() {
    // Create the low-level client
    RestClient restClient = RestClient.builder(
        new HttpHost(host, port)).build();

// Create the transport with a Jsonb mapper
    ElasticsearchTransport transport = new RestClientTransport(
        restClient, new JsonbJsonpMapper());

    return new ElasticsearchClient(transport);
  }

  @Bean
  public ElasticsearchAsyncClient elasticsearchAsyncJavaClient() {
    // Create the low-level client
    RestClient restClient = RestClient.builder(
        new HttpHost(host, port)).build();

// Create the transport with a Jsonb mapper
    ElasticsearchTransport transport = new RestClientTransport(
        restClient, new JsonbJsonpMapper());

    return new ElasticsearchAsyncClient(transport);
  }

}
