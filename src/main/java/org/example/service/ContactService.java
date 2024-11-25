package org.example.service;



import jakarta.validation.Valid;
import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public void saveContact(@Valid Contact contact) {
        contactRepository.save(contact);
    }

    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }

    public Contact getContactById(String id) {
        return contactRepository.findById(id).orElse(null);
    }
}

