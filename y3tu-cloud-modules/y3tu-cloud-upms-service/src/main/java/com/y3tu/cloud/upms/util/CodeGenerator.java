package com.y3tu.cloud.upms.util;

import com.y3tu.tool.setting.Props;
import com.y3tu.tool.web.util.JasyptUtil;
import com.y3tu.tool.web.util.MpGenerator;

/**
 * @author y3tu
 * 代码生成
 */
public class CodeGenerator {
    public static void main(String[] args) {
        Props props = new Props("config/generator.properties");
        props.setProperty("password", JasyptUtil.decyptPwd("y3tu", props.getProperty("password")));
        MpGenerator mpGenerator = new MpGenerator();
        props.toBean(mpGenerator, MpGenerator.class);
        mpGenerator.executeCode();
    }
}
