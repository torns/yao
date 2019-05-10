package com.y3tu.cloud.auth.authorization.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.y3tu.cloud.common.exception.AuthExceptionEnum;
import com.y3tu.tool.core.pojo.R;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
class CustomOauthException extends OAuth2Exception {

    private final R result;

    CustomOauthException(OAuth2Exception oAuth2Exception) {
        super(oAuth2Exception.getSummary(), oAuth2Exception);
        AuthExceptionEnum exceptionEnum = AuthExceptionEnum.valueOf(oAuth2Exception.getOAuth2ErrorCode().toUpperCase());
        this.result = R.error(exceptionEnum);
    }
}