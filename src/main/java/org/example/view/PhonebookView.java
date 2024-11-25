package org.example.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;
import org.example.model.Contact;
import org.example.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.example.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Route
public class PhonebookView extends VerticalLayout {

    private final ContactService contactService;
    private final Grid<Contact> grid;
    private final Binder<Contact> binder;
    private final TextField nameField;
    private final TextField phoneField;
    private final EmailField emailField;
    private final Button saveButton;
    private final Button deleteButton;

    private final PdfService pdfService;
    private final Button generatePdfButton;

    @Autowired
    public PhonebookView(ContactService contactService, PdfService pdfService) {
        this.contactService = contactService;
        this.pdfService = pdfService;

        // Инициализация элементов интерфейса
        grid = new Grid<>(Contact.class);
        binder = new Binder<>(Contact.class);
        nameField = new TextField("Name");
        phoneField = new TextField("Phone");
        emailField = new EmailField("Email");

        saveButton = new Button("Save", e -> saveContact());
        deleteButton = new Button("Delete", e -> deleteContact());

        binder.bind(nameField, Contact::getName, Contact::setName);
        binder.bind(phoneField, Contact::getPhoneNumber, Contact::setPhoneNumber);
        binder.bind(emailField, Contact::getEmail, Contact::setEmail);

        FormLayout formLayout = new FormLayout(nameField, phoneField, emailField, saveButton, deleteButton);
        add(formLayout, grid);

        // Отображение контактов в сетке
        grid.setItems(contactService.getAllContacts());
        grid.addColumn(Contact::getEmail).setHeader("Email");
        grid.addColumn(Contact::getId).setHeader("Id");
        grid.addColumn(Contact::getName).setHeader("Name");
        grid.addColumn(Contact::getPhoneNumber).setHeader("Phone Number");

        // Кнопка для генерации PDF
        generatePdfButton = new Button("Generate PDF Report", e -> generatePdf());
        add(generatePdfButton);
    }

    private void saveContact() {
        if (binder.validate().isOk()) {
            Contact contact = new Contact();
            contact.setName(nameField.getValue());
            contact.setPhoneNumber(phoneField.getValue());
            contact.setEmail(emailField.getValue());
            contactService.saveContact(contact);
            grid.setItems(contactService.getAllContacts());
            Notification.show("Contact saved!");
        } else {
            Notification.show("Invalid input.");
        }
    }

    private void deleteContact() {
        Contact selected = grid.getSelectedItems().stream().findFirst().orElse(null);
        if (selected != null) {
            contactService.deleteContact(selected.getId());
            grid.setItems(contactService.getAllContacts());
            Notification.show("Contact deleted.");
        }
    }

    private void generatePdf() {
        try {
            // Генерация PDF из контактов
            ByteArrayOutputStream pdfReport = pdfService.generatePdfReport(contactService.getAllContacts());

            // Создание ресурса для загрузки PDF
            StreamResource resource = new StreamResource("contacts_report.pdf", () -> new ByteArrayInputStream(pdfReport.toByteArray()));

            // Создание Anchor для ссылки на скачивание
            Anchor downloadAnchor = new Anchor(resource, "Download PDF");
            downloadAnchor.getElement().setAttribute("download", true);  // Устанавливаем атрибут для скачивания

            // Добавляем Anchor (ссылку на скачивание) на экран
            add(downloadAnchor);

            Notification.show("PDF Report generated successfully!");

        } catch (Exception e) {
            Notification.show("Error generating PDF report.");
            e.printStackTrace();
        }
    }





}
