package com.iresh.validators;

import com.iresh.model.SignUpModel;
import com.iresh.model.User;
import com.iresh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Created by iresh on 12/23/2016.
 */
@Component
public class SignUpModelValidator implements Validator {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpModel signUpModel = (SignUpModel) target;
        String mail = signUpModel.getEmail();
        List<User> mails = userRepository.findByEmail(mail);
        if (!mails.isEmpty()){
            errors.rejectValue("email","user.mail","Pleas enter a unique Email");
        }

        String userName = signUpModel.getUserName();
        User user = userRepository.findByUsername(userName);
        //System.out.println(user.getUsername().equalsIgnoreCase(userName) );
        if (user!=null&&user.getUsername().toLowerCase() ==userName.toLowerCase() ){

            errors.rejectValue("userName","user.userName","User Name already taken ");

        }

    }
}
