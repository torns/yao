package com.y3tu.cloud.upms.util;

import com.y3tu.tool.db.ds.DsFactoryEnum;
import com.y3tu.tool.setting.Setting;
import com.y3tu.tool.web.codegen.util.GenUtils;
import com.y3tu.tool.web.util.JasyptUtil;

/**
 * @author y3tu
 * 代码生成
 */
public class CodeGenerator {
    public static void main(String[] args) {
        Setting setting = new Setting("config/generator.properties");
        setting.set("password",JasyptUtil.decyptPwd("y3tu",setting.getStr("password")));

        GenUtils.startGeneratorCode("sys_user",setting, DsFactoryEnum.Druid);
    }
}
