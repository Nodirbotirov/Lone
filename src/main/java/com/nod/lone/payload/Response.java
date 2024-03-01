package com.nod.lone.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {

    private String message;

    private boolean success;

    private Object object;

    private long totalElements;
    private long totalPages;
    private long page;


    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Response(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public Response(String message, boolean success, Object object, long totalElements, long totalPages, long page) {
        this.message = message;
        this.success = success;
        this.object = object;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.page = page;
    }
}
