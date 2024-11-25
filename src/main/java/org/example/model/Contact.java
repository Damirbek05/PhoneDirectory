package org.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Contact {
    @Id
    private String id;

    private String name;

    @Pattern(regexp = "^[+]?\\(?\\d{1,4}?\\)?[\\s\\.-]?\\d{1,3}[\\s\\.-]?\\d{1,4}[\\s\\.-]?\\d{1,4}[\\s\\.-]?\\d{1,9}$", message = "Invalid phone number")
    private String phoneNumber;

    @Email(regexp = "^[\\w-\\.]+@gmail\\.com$", message = "Email must be a valid Gmail address")
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
