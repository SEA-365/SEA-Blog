package com.sea.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.Valid;

/**
 * @author: sea
 * @date: 2023/12/21 17:39
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class PageRequestApi<T> {

    @Valid
    private T body = null;

    public PageRequestApi() {
    }

    public PageRequestApi(T body) {
        this.body = body;
    }

    public static <T> PageRequestApi<T> instance(T body) {
        return new PageRequestApi<>(body);
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(final T body) {
        this.body = body;
    }

}