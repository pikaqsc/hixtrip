package com.hixtrip.sample.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @Description 接口返回数据封装
 * @Author qyc
 * @Date 2024-03-15 16:06
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    public String code;
    public String msg;
    public T result;

    public static Response success() {
        return success(null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>("200", null, data);
    }

    public static <T> Response<T> fail(String msg) {
        return fail("500", msg);
    }

    public static <T> Response<T> fail(String errCode, String msg) {
        errCode = StringUtils.hasText(errCode) ? errCode : "500";
        return new Response<>(errCode, msg, null);
    }
}
