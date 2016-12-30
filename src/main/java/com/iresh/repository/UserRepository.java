package com.iresh.repository;

import com.iresh.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iresh on 11/23/2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User , Long> {
  public User findByUsername(String name);
  //public List<User> findByUsername(String name);
  public List<User> findByEmail(String email);
}
