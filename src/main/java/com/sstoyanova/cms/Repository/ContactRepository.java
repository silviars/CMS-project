package com.sstoyanova.cms.Repository;

import com.sstoyanova.cms.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findContactByFirstName(String name);
    Contact findContactById(Long id);
}
