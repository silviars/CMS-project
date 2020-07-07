package com.sstoyanova.cms.Service;

import com.sstoyanova.cms.Entity.Contact;
import com.sstoyanova.cms.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<Contact> readContacts(){
        return contactRepository.findAll();
    }

    @Override
    public void createContact(Contact contact){
        contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Long id) throws Exception{
        if(contactRepository.existsById(id)){
            contactRepository.deleteById(id);
        } else{
            throw new Exception("Contact with id " + id + " doesn't exist");
        }
    }

    @Override
    public void updateContact(Long id, Contact contact) throws Exception{
        if(contactRepository.existsById(id)){
            contact.setId(id);
            contactRepository.save(contact);
        } else{
            throw new Exception("Contact with id " + id + " doesn't exist");
        }
    }

    @Override
    public Contact readContactById(Long id){
           return contactRepository.findContactById(id);
    }
}