package com.y3tu.cloud.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 日志记录VO
 *
 * @author liuht
 * @date 2017/11/20
 */
@Data
public class LogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SysLogVO sysLog;
    private String username;
}
