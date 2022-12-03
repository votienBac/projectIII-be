package vn.noron.service;

import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
public class OkHttpService {
    private final OkHttpClient client;

    public OkHttpService(OkHttpClient client) {
        this.client = client;
    }

    public String post(String url, String jsonBody) throws Exception {
        return post(url, jsonBody, null);
    }

    public String post(String url, String jsonBody, String accessToken) throws Exception {
        MediaType json = MediaType.parse("application/json;charset=utf-8");
        Request request;
        RequestBody body = jsonBody == null ? RequestBody.create(json, "") : RequestBody.create(json, jsonBody);

        if (accessToken == null) {
            request = new Request.Builder().url(url).post(body).build();
        } else {
            request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + accessToken)
                    .post(body).build();
        }
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            response.body().close();
            return result;
        }
    }

    public String post(String url, String jsonBody, String accessToken, String csrfToken) throws Exception {
        MediaType json = MediaType.parse("application/json;charset=utf-8");
        Request request;
        RequestBody body = jsonBody == null ? RequestBody.create(json, "") : RequestBody.create(json, jsonBody);

        if (accessToken == null) {
            request = new Request.Builder().url(url).post(body).build();
        } else {
            request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Csrf-Token", csrfToken)
                    .post(body).build();
        }

        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            response.body().close();
            return result;
        }
    }

    public String put(String url, String accessToken) throws Exception {
        return put(url, null, accessToken);
    }

    public String put(String url, String jsonBody, String accessToken) throws Exception {
        MediaType json = MediaType.parse("application/json;charset=utf-8");
        Request request;
        RequestBody body = jsonBody == null ? RequestBody.create(json, "") : RequestBody.create(json, jsonBody);

        if (accessToken == null) {
            request = new Request.Builder().url(url).put(body).build();
        } else {
            request = new Request.Builder().url(url)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .put(body).build();
        }

        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            response.body().close();
            return result;
        }
    }

    public String get(String url) throws Exception {
        return get(url, null);
    }

    public String get(String url, String accessToken) throws Exception {

        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .get().build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            response.body().close();
            return result;
        }
    }

    public String delete(String url, String accessToken) throws Exception {

        Request request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + accessToken)
                .delete().build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            response.body().close();
            return result;
        }
    }


}
