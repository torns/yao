package com.y3tu.cloud.auth.authorization.annotation;

import com.y3tu.cloud.auth.authorization.store.AuthRedisTokenStore;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 认证服务器:在启动类上添加该注解来----开启通过Redis存储令牌
 *
 * @author y3tu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AuthRedisTokenStore.class)
public @interface EnableAuthRedisTokenStore {
}
