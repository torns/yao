package com.y3tu.cloud.log.starter.annotation;

import com.y3tu.cloud.log.starter.constant.SaveModeEnum;

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

    /**
     * 保存日志方式 默认保存到数据库
     */
    SaveModeEnum saveMode() default SaveModeEnum.NONE;

}
