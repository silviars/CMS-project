package com.sstoyanova.cms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sstoyanova.cms.Entity.Contact;
import com.sstoyanova.cms.Entity.Permission;
import com.sstoyanova.cms.Entity.User;
import com.sstoyanova.cms.Repository.ContactRepository;
import com.sstoyanova.cms.Repository.UserRepository;
import com.sstoyanova.cms.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CmsApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    @WithMockUser(authorities = {"read_user"})
    public void testReadUsers()throws Exception {
    mockMvc.perform(get("/view-users")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"create_user"})
    public void testCreateUser() throws Exception{
       User user = new User("test", passwordEncoder.encode("pass"), "test", null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String jsonBody = objectWriter.writeValueAsString(user);

        mockMvc.perform(post("/create-user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBody))
                .andExpect(status().isOk());

        User toDelete = userRepository.findByUsername("test");
        userRepository.deleteById(toDelete.getId());
    }

    @Test
    @WithMockUser(authorities = {"create_user"})
    public void testCreateNullUser() throws Exception{
        //User user1 = new User(null, null, null, null);
        User user = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String jsonBody = objectWriter.writeValueAsString(user);

        mockMvc.perform(post("/create-user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBody))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(authorities = {"update_user"})
    public void testUpdateUser() throws Exception{
        User user = new User("test", passwordEncoder.encode("pass"), "test", null);
        userRepository.save(user);
        User editedUser = new User("updated", passwordEncoder.encode("pass"),"updated", null);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        try {
            String jsonBody = objectWriter.writeValueAsString(editedUser);
            mockMvc.perform(put("/update-user")
                    .param("id", user.getId().toString())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(jsonBody))
                    .andExpect(status().isOk());
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        User toDelete = userRepository.findByUsername("updated");
        userRepository.deleteById(toDelete.getId());
    }

    @Test
    @WithMockUser(authorities = {"delete_user"})
    public void testDeleteUser() throws Exception{
        User user = new User("test", passwordEncoder.encode("pass"), "test", null);
        userRepository.save(user);

        mockMvc.perform(delete("/delete-user")
                .param("id", user.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"SUPER-ADMIN"})
    public void testUpdatePermissions() throws Exception{
        User user = new User("test", passwordEncoder.encode("pass"), "test", null);
        userRepository.save(user);

        Set<Permission> permissions = new HashSet<>();
        permissions.add(new Permission("read_contact"));
        permissions.add(new Permission("read_user"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String jsonBody = objectWriter.writeValueAsString(permissions);

        mockMvc.perform(put("/update-permissions")
                .param("id", user.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBody))
                .andExpect(status().isOk());

        User toDelete = userRepository.findByUsername("test");
        userRepository.deleteById(toDelete.getId());
    }

    @Test
    @WithMockUser(authorities = {"read_contact"})
    public void testReadContacts() throws Exception{
        mockMvc.perform(get("/view-contacts")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"create_contact"})
    public void testCreateContact() throws Exception{
        Contact contact = new Contact("test", "test",878129971L, "test");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String jsonBody = objectWriter.writeValueAsString(contact);

        mockMvc.perform(post("/create-contact")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBody))
                .andExpect(status().isOk());

        Contact toDelete = contactRepository.findContactByFirstName("test");
        contactRepository.deleteById(toDelete.getId());
    }

    @Test
    @WithMockUser(authorities = {"create_contact"})
    public void testCreateNullContact() throws Exception{
        //first name and mobile should not be null
        Contact contact = new Contact(null, null,878129974L, null);
        Contact contact1 = new Contact("test", null,null,null);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String nullName = objectWriter.writeValueAsString(contact);
        String nullMobile = objectWriter.writeValueAsString(contact1);


        assertThrows(NestedServletException.class, ()-> {
            mockMvc.perform(post("/create-contact")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(nullName));
                  //  .andExpect(status().isInternalServerError());
        });
        assertThrows(NestedServletException.class, ()-> {
            mockMvc.perform(post("/create-contact")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(nullMobile));
        });
    }

    @Test
    @WithMockUser(authorities = {"update_contact"})
    public void testUpdateContact() throws Exception{
        Contact contact = new Contact("test", "test", 12345L, "test");
        contactRepository.save(contact);
        Contact editedContact = new Contact("updated", "test", 543L, "updated");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        try {
            String jsonBody = objectWriter.writeValueAsString(editedContact);
            mockMvc.perform(put("/update-contact")
                    .param("id", contact.getId().toString())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(jsonBody))
                    .andExpect(status().isOk());
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        Contact toDelete = contactRepository.findContactByFirstName("updated");
        contactRepository.deleteById(toDelete.getId());
    }

    @Test
    @WithMockUser(authorities = {"delete_contact"})
    public void testDeleteContact() throws Exception{
        Contact contact = new Contact("test", "test", 1234567L, "test");
        contactRepository.save(contact);

        mockMvc.perform(delete("/delete-contact")
                .param("id", contact.getId().toString()))
                .andExpect(status().isOk());
    }
}