package com.y3tu.cloud.log.server.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author y3tu
 */
@Data
public class SearchDto implements Serializable {

    private String startDate;

    private String endDate;
}
