package com.y3tu.cloud.gen.service;


import com.y3tu.cloud.gen.model.dto.BuildConfigDTO;

public interface SysGenService {

    /**
     * 根据表名生成代码
     * @param buildConfigDTO
     * @return
     */
    byte[] genCodeByTableName(BuildConfigDTO buildConfigDTO);

}
