package com.y3tu.provideruser.user;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author y3tu
 * @date 2018/5/2
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;
    @Column
    private String name;
    @Column
    private Integer age;
    @Column
    private BigDecimal balance;

}
