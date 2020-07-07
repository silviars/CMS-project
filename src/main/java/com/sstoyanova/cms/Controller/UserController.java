package com.sstoyanova.cms.Controller;

import com.sstoyanova.cms.Entity.Contact;
import com.sstoyanova.cms.Entity.Permission;
import com.sstoyanova.cms.Entity.User;
import com.sstoyanova.cms.Service.ContactService;
import com.sstoyanova.cms.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ContactService contactService;

    @GetMapping(path = {"home"})
    @ResponseBody
    public String homepage(){
        return "home page";
    }

    @GetMapping(path = {"view-users"}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    @ResponseBody
    public List<User> readUsers() {
        return userService.readUsers();
    }

    @PostMapping(path = {"create-user"})
    @ResponseBody
    public void addUser(@RequestBody User user){
            userService.createUser(user);
    }

    @DeleteMapping(path = "delete-user")
    @ResponseBody
    public void deleteUser(@RequestParam Long id){
        try {
            userService.deleteUser(id);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PutMapping(path = {"update-user"})
    @ResponseBody
    public void updateUser(@RequestBody User user, @RequestParam Long id){
        try{
            userService.updateUser(id, user);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping(path = {"view-contacts"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Contact> readContacts(){
        return contactService.readContacts();
    }

    @PostMapping(path = {"create-contact"})
    @ResponseBody
    public void addContact(@RequestBody Contact contact){
        contactService.createContact(contact);
    }

    @DeleteMapping(path = {"delete-contact"})
    @ResponseBody
    public void deleteContact(@RequestParam Long id){
        try{
            contactService.deleteContact(id);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @PutMapping(path = {"update-contact"})
    @ResponseBody
    public void updateContact(@RequestBody Contact contact, @RequestParam Long id){
        try{
            contactService.updateContact(id,contact);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PutMapping(path = {"update-permissions"})
    @ResponseBody
    public void updatePermissions(@RequestBody Set<Permission> permissionSet, @RequestParam Long id){
        try {
            userService.updatePermission(id,permissionSet);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping(path = {"search-contact"})
    @ResponseBody
    public Contact viewContactById(@RequestParam Long id){
        return contactService.readContactById(id);
    }
}