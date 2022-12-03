package vn.noron.utils.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.jooq.tools.json.JSONObject;
import org.jooq.tools.json.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
@Data
public class API {
    private String url;
    private Map<String, String> params;
    long timeout;

    public API(String url, Map<String, String> params){
        this.setParams(params);
        this.setUrl(url);
        this.timeout = 0;
    }

    public API(String url, Map<String, String> params, long timeout){
        this(url, params);
        this.setTimeout(timeout);
    }

    @SneakyThrows
    public JSONObject post(){
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(params);

        HttpClient httpClient = buildHttpClient(this.timeout);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .setHeader("Accept", "application/json")
                .setHeader("Content-type", "application/json")
                .uri(URI.create(this.url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.body());
    }
    @SneakyThrows
    public JSONObject get(){
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(params);

        HttpClient httpClient = buildHttpClient(this.timeout);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .setHeader("Accept", "application/json")
                .setHeader("Content-type", "application/json")
                .uri(URI.create(this.url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.body());
    }



    private HttpClient buildHttpClient(){
        return HttpClient.newBuilder()
                .build();
    }

    private HttpClient buildHttpClient(long timeout){
        if(timeout == 0){
            return buildHttpClient();
        }

        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(timeout))
                .build();
    }
}
