package com.y3tu.yao.authorization.exception;

import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.pojo.R;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * oauth2异常转换
 */
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<R> translate(Exception e) {
        if (e instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            AuthExceptionEnum exceptionEnum = AuthExceptionEnum.valueOf(oAuth2Exception.getOAuth2ErrorCode().toUpperCase());
            return ResponseEntity
                    .status(Integer.valueOf(exceptionEnum.getCode()))
                    .body(R.error(exceptionEnum));
        } else {
            return ResponseEntity
                    .status(500)
                    .body(R.error(e.getMessage(), ErrorEnum.SERVICE_CALL_ERROR));
        }
    }
}
