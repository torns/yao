package com.y3tu.cloud.upms.mapper;

import com.y3tu.cloud.upms.model.entity.User;
import com.y3tu.tool.web.base.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 用户表 Mapper 接口
 * </p>
 *
 * @author y3tu
 * @date  2018-08-05
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAll();
}