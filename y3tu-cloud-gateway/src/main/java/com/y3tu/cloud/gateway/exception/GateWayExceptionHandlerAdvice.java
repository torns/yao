package com.y3tu.cloud.gateway.exception;

import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.ServerCallException;
import com.y3tu.tool.core.pojo.R;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author y3tu
 * @date 2019-05-09
 */
@Slf4j
@Component
public class GateWayExceptionHandlerAdvice {
    @ExceptionHandler(value = {ResponseStatusException.class})
    public R handle(ResponseStatusException ex) {
        log.error("response status exception:{}", ex.getMessage());
        return R.error("GATEWAY-ERROR-001", ex.getMessage());
    }

    @ExceptionHandler(value = {ConnectTimeoutException.class})
    public R handle(ConnectTimeoutException ex) {
        log.error("connect timeout exception:{}", ex.getMessage());
        return R.error("GATEWAY-ERROR-002", "连接超时");
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R handle(NotFoundException ex) {
        log.error("not found exception:{}", ex.getMessage());
        return R.error("GATEWAY-ERROR-003", ex.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handle(RuntimeException ex) {
        log.error("runtime exception:{}", ex.getMessage());
        return R.error("GATEWAY-ERROR-004", ex.getMessage());
    }

    @ExceptionHandler(value = {NoPermissionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R handle(NoPermissionException ex) {
        log.error("NoPermissionException exception:{}", ex.getMessage());
        return R.error("GATEWAY-ERROR-005", ex.getMessage());
    }

    @ExceptionHandler(value = {ServerCallException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R handle(ServerCallException ex) {
        log.error("ServerCallException exception:{}", ex.getMessage());
        return R.error(ex.getMessage(), ErrorEnum.SERVICE_CALL_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handle(Exception ex) {
        log.error("exception:{}", ex.getMessage());
        return R.error();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handle(Throwable throwable) {
        R result = R.error();
        if (throwable instanceof ResponseStatusException) {
            result = handle((ResponseStatusException) throwable);
        } else if (throwable instanceof ConnectTimeoutException) {
            result = handle((ConnectTimeoutException) throwable);
        } else if (throwable instanceof NotFoundException) {
            result = handle((NotFoundException) throwable);
        } else if (throwable instanceof NoPermissionException) {
            result = handle((NoPermissionException) throwable);
        } else if (throwable instanceof ServerCallException) {
            result = handle((ServerCallException) throwable);
        } else if (throwable instanceof RuntimeException) {
            result = handle((RuntimeException) throwable);
        } else if (throwable instanceof Exception) {
            result = handle((Exception) throwable);
        }
        return result;
    }
}
