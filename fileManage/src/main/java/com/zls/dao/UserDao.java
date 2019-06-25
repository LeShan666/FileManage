package com.zls.dao;

import com.zls.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    public User findUserByUsernameAndUserpass(String username, String userpass);
}
