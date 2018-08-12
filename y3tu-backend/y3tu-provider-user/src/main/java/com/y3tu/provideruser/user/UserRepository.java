package com.y3tu.provideruser.user;

import com.y3tu.provideruser.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author y3tu
 * @date 2018/5/2
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
}
