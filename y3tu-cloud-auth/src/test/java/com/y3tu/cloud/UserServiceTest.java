package com.y3tu.cloud;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceTest {


    @Test
    public void testPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("admin");
        System.out.println("=============="+password);
    }


}