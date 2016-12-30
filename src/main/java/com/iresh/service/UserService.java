package com.iresh.service;

import com.iresh.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by iresh on 11/23/2016.
 */

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}