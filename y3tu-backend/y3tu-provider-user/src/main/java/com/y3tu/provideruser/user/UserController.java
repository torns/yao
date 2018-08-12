package com.y3tu.provideruser.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


/**
 * @author y3tu
 * @date 2018/5/2
 */
@RestController
@RefreshScope
public class UserController {
    @Value("${profile}")
    private String profile;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @GetMapping("/profile")
    public String getProfile() {
        return this.profile;
    }

}
