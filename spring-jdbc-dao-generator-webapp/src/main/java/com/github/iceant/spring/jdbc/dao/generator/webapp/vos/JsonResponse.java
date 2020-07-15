package com.github.iceant.spring.jdbc.dao.generator.webapp.vos;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
    private int statusCode;
    private boolean status;
    private String message;
    private Map<String, Object> results = new HashMap<>();

    public int getStatusCode() {
        return statusCode;
    }

    public JsonResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public JsonResponse setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, Object> getResults() {
        return results;
    }

    public JsonResponse setResults(Map<String, Object> results) {
        this.results = results;
        return this;
    }

    public JsonResponse put(String name, Object value) {
        this.results.put(name, value);
        return this;
    }
}
