package onlinesale.onlinetree.controller;

import lombok.Data;

@Data
public class APIResponse {
    private int status;
    private String message;
    private Object data;
}
