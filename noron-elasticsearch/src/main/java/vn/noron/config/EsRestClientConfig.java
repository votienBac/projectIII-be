package vn.noron.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsRestClientConfig {
    private final EsConfig esConfig;

    public EsRestClientConfig(EsConfig esConfig) {
        this.esConfig = esConfig;
    }

    @Bean
    public RestClient restClient() {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esConfig.getUsername(), esConfig.getPassword()));

        final HttpHostConfig hostPort = esConfig.getHostPort();
        return RestClient
                .builder(new HttpHost(hostPort.getHost(), hostPort.getPort(), hostPort.getSchema()))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .build();
    }

    @Bean
    public ElasticsearchTransport transport() {
        return new RestClientTransport(restClient(), new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient client() {
        return new ElasticsearchClient(transport());
    }
}
