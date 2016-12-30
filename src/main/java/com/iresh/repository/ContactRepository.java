package com.iresh.repository;

import com.iresh.model.Contact;

import com.iresh.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ContactRepository extends CrudRepository<Contact, Long> {


    List<Contact> findByUser(User user);
    Long deleteById(int id);
    List<Contact> findByfName(String fName);
    List<Contact> findByphoneNumbers(String phoneNumbers);
    Contact findContactById(int id);
}
