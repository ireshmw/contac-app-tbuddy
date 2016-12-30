package com.iresh.repository;

import com.iresh.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by iresh on 11/23/2016.
 */
public interface RoleRepository extends CrudRepository <Role,Long > {


    public Role findByName(String roleName);
}
