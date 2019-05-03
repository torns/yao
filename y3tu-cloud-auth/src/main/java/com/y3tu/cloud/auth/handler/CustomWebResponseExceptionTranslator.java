package com.y3tu.cloud.auth.handler;

import com.y3tu.cloud.auth.exception.CustomOauth2Exception;
import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.common.dto.SysLogDTO;
import com.y3tu.cloud.common.enums.OperationStatusEnum;
import com.y3tu.cloud.common.enums.SysLogTypeEnum;
import com.y3tu.tool.core.exception.ExceptionUtil;
import com.y3tu.tool.http.IpUtil;
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
 * @description: oauth2异常处理器
 */

@Component("customWebResponseExceptionTranslator")
@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {


    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");
        SysLogDTO sysLogDTO = new SysLogDTO();
        sysLogDTO
                .setCreateBy(username)
                .setRequestUri(request.getRequestURI())
                .setUserAgent(IpUtil.getIpAddr(request))
                .setType(SysLogTypeEnum.LOGIN.getCode())
                .setStatus(OperationStatusEnum.FAIL.getCode())
                .setModuleName("auth认证模块")
                .setActionName("登录")
                .setServiceId(ServiceNameConstants.AUTH)
                .setRemoteAddr(IpUtil.getIpAddr(request))
                .setMethod(request.getMethod())
                .setException(ExceptionUtil.getFormatMessage(e));
//        rabbitTemplate.convertAndSend(MqQueueNameConstant.SYS_LOG_QUEUE, sysLogDTO);
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
