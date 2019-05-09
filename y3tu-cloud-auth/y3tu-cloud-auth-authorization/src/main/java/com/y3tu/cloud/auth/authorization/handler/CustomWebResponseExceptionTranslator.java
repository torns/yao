package com.y3tu.cloud.auth.authorization.handler;

import com.y3tu.cloud.auth.authorization.exception.CustomOauth2Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * oauth2异常处理器
 */

@Component("customWebResponseExceptionTranslator")
@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {


    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");

        //todo 记录异常日志
        log.error(e.getStackTrace().toString());
        if (!(e instanceof OAuth2Exception)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CustomOauth2Exception(e.getMessage()));
        }
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new CustomOauth2Exception(oAuth2Exception.getMessage()));
    }
}
