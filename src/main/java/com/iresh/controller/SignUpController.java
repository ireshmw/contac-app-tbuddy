package com.iresh.controller;

import com.iresh.model.Role;
import com.iresh.model.SignUpModel;
import com.iresh.model.User;
import com.iresh.repository.RoleRepository;
import com.iresh.repository.UserRepository;
import com.iresh.validators.SignUpModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by iresh on 12/22/2016.
 */
@Controller
public class SignUpController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SignUpModelValidator signUpModelValidator;



    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public String signUpPage(Model model){
        System.out.println("inside the signup GET.....");
        model.addAttribute("signUpModel",new SignUpModel());
        return "signup";
    }


    @RequestMapping(value = "/signup",method=RequestMethod.POST)
    public String signUpSubmit(@ModelAttribute @Valid SignUpModel signUpModel , BindingResult bindingResult, Model model){
        signUpModelValidator.validate(signUpModel,bindingResult);

        if (!bindingResult.hasErrors()){

            String pass =signUpModel.getPassword();
            String confirmPass = signUpModel.getConfirmPassword();
            if (!pass.equals(confirmPass)){
                bindingResult.rejectValue("confirmPassword","confirmPasswordError","please renter your password");
                return "signup";
            }

            else{

                System.out.println("inside the signup POST....");
                System.out.println(signUpModel.toString());
                signUpModelValidator.validate(signUpModel,bindingResult);
                if (!bindingResult.hasErrors()){
                    model.addAttribute("signUpModel",new SignUpModel());
                    model.addAttribute("success","SignUp Successful!");

                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    String hashPass = bCryptPasswordEncoder.encode(signUpModel.getPassword());

                    Role role = roleRepository.findByName("ROLE_USER");
                    User user =new User(signUpModel.getUserName(),signUpModel.getEmail(),hashPass,true,role);
                    userRepository.save(user);
                    return "signup";
                }

                return "signup";
            }

        }
        else {

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error :fieldErrors){
                System.out.println( error.getField()+""+ error.getDefaultMessage());
            }

            model.addAttribute("signUpModel",signUpModel);
            return "signup";
        }

    }

}
