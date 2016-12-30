package com.iresh.validators;

import com.iresh.model.Contact;
import com.iresh.model.Emails;
import com.iresh.model.PhoneNumbers;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by iresh on 12/7/2016.
 */
@Component
public class ContactValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Contact.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contact contact = (Contact) target;

        System.out.println("inside the validator");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fName", "error.fName", "First name is required.");

        if (!Pattern.matches("\\w{3,15}",contact.getfName())){
            errors.rejectValue("fName", "contact.fName", "first name can have more than 3 characters and less than 15 ");
        }

        if (!contact.getlName().isEmpty()) {
            if (!Pattern.matches("\\w{3,15}", contact.getlName())) {
                errors.rejectValue("lName", "contact.lName", "last name can have more than 3 characters and less than 15 ");
            }
        }

        List<PhoneNumbers> phoneNumbers = contact.getPhoneNumbers();
        if (phoneNumbers.isEmpty()) {
            errors.rejectValue("phoneNumbers", "contact.phoneNumbers", "not a valid phone number");
        } else {
            for (PhoneNumbers phone : phoneNumbers) {
                System.out.println(phone.getPhoneNumber());
                if (!Pattern.matches("\\d{10}", phone.getPhoneNumber())) {
                    errors.rejectValue("phoneNumbers", "contact.phoneNumbers", "not a valid phone number");
                }

            }
        }


        List<Emails> emails = contact.getMails();

        if (!emails.isEmpty()){
            //errors.rejectValue("mails", "contact.mails", "not a valid Email");
                for (Emails mail : emails) {
                    System.out.println(mail.getEmail());
                    if (!Pattern.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b", mail.getEmail())) {
                        errors.rejectValue("mails", "contact.mails", "not a valid Email");
                    }

                }
        }


//        if (contact.getfName().isEmpty()){
//            System.out.println("inside the validator's if statement ...");
//            errors.rejectValue("fName","fname","fname is empty......");
//        }


    }
}
