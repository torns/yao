package com.y3tu.yao.common.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息发送基础模板
 *
 * @author y3tu
 * @date 2019-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplate implements Serializable {

    /**
     * 消息通道
     */
    private String channel;
}
