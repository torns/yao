package com.y3tu.consumer.movie;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author y3tu
 * @date 2018/5/7
 */
@Data
public class User {
    private long id;
    private String username;
    private String name;
    private Integer age;
    private BigDecimal balance;

}
