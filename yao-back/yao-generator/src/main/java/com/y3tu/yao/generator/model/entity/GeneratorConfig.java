package com.y3tu.yao.generator.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author y3tu
 */
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
     * 包路径
     */
    private String pack;
    /**
     * 表前缀
     */
    private String prefix;
}
