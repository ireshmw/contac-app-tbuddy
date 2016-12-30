package com.iresh.controller;

import com.iresh.model.*;
import com.iresh.repository.ContactRepository;

import com.iresh.repository.UserRepository;
import com.iresh.validators.ContactValidator;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;


/**
 * Created by iresh on 10/11/2016.
 */
@Controller
public class HomeController {

    @Autowired
    ContactRepository contacts;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactValidator contactValidator;


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model, Principal principal) {
        System.out.println(principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("contact", new Contact());
        tablePopulate(model, principal);
        return "home";
    }

//    @RequestMapping(value = "/form" , method= RequestMethod.POST)
//    public @ResponseBody Object homePage(@RequestBody @Valid Contact contact,BindingResult bindingResult,Model model,Principal principal ){
//        System.out.println(contact.getName()+" "+ contact.getPhoneNumb());
//
//        RespondState respondState = new RespondState();
//        Contact savedContact = new Contact();
//
//        if (bindingResult.hasErrors()){
//
//            //RespondState respondState = new RespondState();
//            respondState.setState(0);
////            respondState.setErrorObjects(new ErrorObjects("this is ok" ,"this is test"));
//            List<FieldError> errors = bindingResult.getFieldErrors();
//
//            for (FieldError er:errors) {
//                System.out.println(er.getField() +"--->"+ er.getDefaultMessage());
//
//                ErrorObjects errorObjects = new ErrorObjects();
//                errorObjects.setErrorField(er.getField());
//                errorObjects.setErrorMessage(er.getDefaultMessage());
//                respondState.setErrorObjects(errorObjects);
//            }
//
//            System.out.println(respondState.toString());
//
//            System.out.println("THERE IS AN ERROR.......");
//            return respondState;
//
//        }
//
//        else {
//            System.out.println("if not executed....");
//
//            Contact newContact = new Contact();
//            System.out.println("in the else block...");
//      // Here no need to check that the entered phone number exist or not on the database, because in the database we have set
//      // on phone number column "not duplicate files" constrain . if when we try to enter a duplicate phone number this trow an exception.
//            newContact.setName(contact.getName());
//            newContact.setPhoneNumb(contact.getPhoneNumb());
//            User userObject =userRepository.findByUsername(principal.getName());
//
//            newContact.setUser(userObject);
//
//            try {
//                System.out.println("in the try block..");
//                savedContact =contacts.save(newContact);
//
//            }
//            catch(DataIntegrityViolationException e)
//            {
//
//                respondState.setState(0);
//                respondState.setErrorObjects(new ErrorObjects("phoneNumb","this number already in the database."));
//
////                BindingResult errorResult = bindingResult;
////                System.out.println("an exception occur.. in the catch block...");
////                tablePopulate(model);
////                System.out.println("inside the catch block..data integrity exception");
////                errorResult.rejectValue("phoneNumb","phoneError","this number already in the database.");
////                //errorResult.rejectValue("phoneNumb", "This number already in Your List");
////                List<FieldError> errors = errorResult.getFieldErrors();
//                return respondState;
//
//            }
//            catch(Exception e)
//            {
//                System.out.println("inside the catch block.....");
//                //e.printStackTrace();
//            }
//
//            System.out.println("sucessfully add the data...");
//            tablePopulate(model,principal);
//            respondState.setState(1);
//            respondState.setContact(savedContact);
//            return respondState;
//        }
//
//    }

//    @RequestMapping(value = "/delete",method = RequestMethod.POST)
//    public String deleteRecord(@RequestParam int recId, Model model ){
//        //model.addAttribute("contact",new Contact());
//        System.out.println(recId);
//        contacts.deleteById(recId);
//        //tablePopulate(model);
//        return "redirect:/";
//    }


    @RequestMapping(path = "/form", method = RequestMethod.POST)
    public @ResponseBody RespondState formSubmit( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal) {

       // System.out.println(contact1.toString());

        RespondState respondObj = new RespondState();

        String firstName = httpServletRequest.getParameter("first_name");
        String lastName = httpServletRequest.getParameter("last_name");
        //String[] phoneNumbers = parameters.get("phone");
        String[] phones = httpServletRequest.getParameterValues("phone");
        String[] emails = httpServletRequest.getParameterValues("email");
        String address = httpServletRequest.getParameter("address");
        String message = httpServletRequest.getParameter("comment");
        User userObject = userRepository.findByUsername(principal.getName());


        Contact contact = new Contact(firstName, lastName, address, message, userObject);
        Contact httpReplyContact = new Contact(firstName, lastName, address, message);


        ArrayList<PhoneNumbers> phoneNumbers = new ArrayList<>();
        for (int i = 0; i < phones.length; i++) {
            //System.out.println(phones[i]);
            //contact.getId();
            String phone = phones[i];
            if (!phone.isEmpty()) {
                PhoneNumbers phoneNumbObj = new PhoneNumbers(phones[i], contact);
                phoneNumbers.add(phoneNumbObj);
            }
        }

        ArrayList<Emails> emailList = new ArrayList<>();
        for (int i = 0; i < emails.length; i++) {
            //System.out.println(emails[i]);

            String email = emails[i];
            if (!email.isEmpty()) {
                Emails emailObj = new Emails(emails[i], contact);
                emailList.add(emailObj);
            }
        }

        contact.setPhoneNumbers(phoneNumbers);
        contact.setMails(emailList);
        httpReplyContact.setPhoneNumbers(phoneNumbers);
        httpReplyContact.setMails(emailList);
        System.out.println("Contact Object has build....");
        //System.out.println(contact.toString());

        BindingResult errors = new BeanPropertyBindingResult(contact, "contact");
        contactValidator.validate(contact, errors);
        //System.out.println("before if statement....");

        if (errors.hasErrors()) {

            System.out.println("there is an error of your form");
            List<FieldError> errors1 = errors.getFieldErrors();
            respondObj.setState(0);

            for (FieldError error : errors1) {

                String field = error.getField();
                String msg = error.getDefaultMessage();
                respondObj.setErrorObjects(new ErrorObjects(field, msg));
                //System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());

            }
            respondObj.setContact(httpReplyContact);
            return respondObj;
        }
        else {
            System.out.println("trying to saving the contact...");

            try {

                contacts.save(contact);
                System.out.println("Contact has saved....");
                //System.out.println(savedContact.toString());

                respondObj.setContact(httpReplyContact);
                respondObj.setState(1);
                return respondObj;

            } catch (ConstraintViolationException e) {

//                    System.out.println("inside the catch block");
//                    List<FieldError> errors1 = errors.getFieldErrors();
//
//                    for (FieldError error : errors1 ) {
//                        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
//                    }

                respondObj.setState(0);
                Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//                  [ConstraintViolationImpl{interpolatedMessage='size must be between 4 and 15', propertyPath=fName, rootBeanClass=class com.iresh.model.Contact, messageTemplate='{javax.validation.constraints.Size.message}'}, ConstraintViolationImpl{interpolatedMessage='size must be between 4 and 15', propertyPath=lName, rootBeanClass=class com.iresh.model.Contact, messageTemplate='{javax.validation.constraints.Size.message}'}]

                System.out.println(constraintViolations.toString());
                System.out.println("-----------------------------------------------");


                for (ConstraintViolation constraint : constraintViolations) {

                    String field = String.valueOf(constraint.getPropertyPath());
                    String msg = constraint.getMessage();

                    respondObj.setErrorObjects(new ErrorObjects(field, msg));

//                        ConstraintViolationImpl constraintViolation = (ConstraintViolationImpl) constraint;
//                        System.out.println(constraintViolation.getPropertyPath());
//                        System.out.println(constraint.getMessage());
//                        System.out.println(constraint.getMessageTemplate());
//                        System.out.println("-----------------------------------------------");
                }


            }

        }

        respondObj.setContact(httpReplyContact);
        return respondObj;
    }

    @RequestMapping(value = "/findContactByID", method = RequestMethod.POST)
    @ResponseBody
    public RespondState findContactByID(@RequestBody Contact recId, Principal principal) {
        //model.addAttribute("contact",new Contact());
//        System.out.println("inside the find contact by id..");
//        System.out.println(recId.getId());
        Contact retContact = contacts.findContactById(recId.getId());
        //retContact.getUser().setPassword("");
        retContact.setUser(null);

        //System.out.println(retContact.toString());
        RespondState respondState = new RespondState();
        respondState.setContact(retContact);
        respondState.setState(1);
        return respondState;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public RespondState deleteRecord(@RequestBody Contact recId, Model model) {
        //model.addAttribute("contact",new Contact());
        System.out.println(recId.getId());
        contacts.deleteById(recId.getId());
        RespondState respondState = new RespondState();
        respondState.setState(1);
        //tablePopulate(model);
        return respondState;
    }


    public void tablePopulate(Model model, Principal principal) {
        //principal.getName();
        User user = userRepository.findByUsername(principal.getName());
        ArrayList<Contact> contactList = new ArrayList<>();
        contactList = (ArrayList<Contact>) contacts.findByUser(user);
        model.addAttribute("contactList", contactList);
    }


}
