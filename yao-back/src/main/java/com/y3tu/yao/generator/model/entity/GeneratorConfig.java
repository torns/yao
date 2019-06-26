package com.y3tu.yao.generator.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("generator_config")
@Accessors(chain = true)
public class GeneratorConfig extends BaseEntity<GeneratorConfig> {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 作者
     */
    private String author;
    /**
     * 是否覆盖
     */
    private boolean cover;
    /**
     * 模块名称
     */
    @TableField("module_name")
    private String moduleName;
    /**
     * 至于哪个包下
     */
    private String pack;
    /**
     * 前端代码生成路径
     */
    private String path;
    private String apiPath;
    private String prefix;
}
