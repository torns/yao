package com.y3tu.cloud.log.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 服务id
     *
     * @return
     */
    String serviceId();

    /**
     * 模块名
     *
     * @return
     */
    String moduleName();

    /**
     * 操作名
     *
     * @return
     */
    String actionName();

}
