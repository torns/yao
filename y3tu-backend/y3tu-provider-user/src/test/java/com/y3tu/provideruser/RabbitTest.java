package com.y3tu.provideruser;

import com.y3tu.provideruser.rabbitmq.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author y3tu
 * @date 2018/5/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProviderUserApplication.class)
public class RabbitTest {
    @Autowired
    private RabbitSender rabbitSender;

    @Test
    public void hello()throws Exception{
        rabbitSender.send();
    }
}
