package org.example.service;

import org.example.model.Contact;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    // Метод для генерации PDF из списка контактов
    public ByteArrayOutputStream generatePdfReport(List<Contact> contacts) throws IOException {
        // Создаем ByteArrayOutputStream для записи в PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Создаем PdfWriter, который будет писать в ByteArrayOutputStream
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);

        // Создаем PdfDocument
        PdfDocument pdfDocument = new PdfDocument(writer);

        // Создаем Document (класс для добавления элементов в PDF)
        Document document = new Document(pdfDocument);

        // Добавляем заголовок
        document.add(new Paragraph("Phonebook Contacts Report"));

        // Добавляем контактные данные в отчет
        for (Contact contact : contacts) {
            document.add(new Paragraph("Name: " + contact.getName()));
            document.add(new Paragraph("Phone: " + contact.getPhoneNumber()));
            document.add(new Paragraph("Email: " + contact.getEmail()));
            document.add(new Paragraph("---------------"));
        }

        // Закрываем документ
        document.close();

        return byteArrayOutputStream;
    }
}
