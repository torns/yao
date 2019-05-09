package com.y3tu.cloud.auth.authorization.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.y3tu.cloud.auth.authorization.serializer.CustomOauthExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * oauth2自定义异常
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {

    public CustomOauth2Exception(String msg) {
        super(msg);
    }
}
