package com.y3tu.config;

import com.y3tu.cloud.config.ConfigApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author y3tu
 * @date 2018/5/23
 * jasypt 加密解密测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfigApplication.class)
public class EncryptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void testEnvironmentProperties() {

        System.out.println("加密后的密文:"+stringEncryptor.encrypt("y3tu"));
    }
}
