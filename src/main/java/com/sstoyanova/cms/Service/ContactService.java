package com.sstoyanova.cms.Service;

import com.sstoyanova.cms.Entity.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> readContacts();
    void createContact(Contact contact);
    void deleteContact(Long id) throws Exception;
    void updateContact(Long id, Contact contact) throws Exception;
    Contact readContactById(Long id);
}