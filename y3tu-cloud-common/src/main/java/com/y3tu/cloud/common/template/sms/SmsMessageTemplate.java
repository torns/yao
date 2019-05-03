package com.y3tu.cloud.common.template.sms;

import com.y3tu.cloud.common.template.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsMessageTemplate extends MessageTemplate implements Serializable {

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 模板
     */
    private String template;

    /**
     * 腾讯云参数回填
     */
    private String[] params;
}
