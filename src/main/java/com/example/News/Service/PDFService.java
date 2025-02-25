package com.example.News.Service;

import com.example.News.Entity.NewsEntity;
import com.example.News.Repo.NewsRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFService {

    @Autowired
    private NewsRepo newsRepo;

    public byte[] createMagazinePdf(List<Integer> newsIds) throws Exception {
        List<NewsEntity> newsList = newsRepo.findAllById(newsIds);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        document.add(new Paragraph("📖 Haber Dergisi").setBold().setFontSize(24));
        document.add(new Paragraph("Seçili Haberler").setFontSize(18));

        String coverImagePath = "src/main/resources/static/cover.jpg";
        ImageData imageData = ImageDataFactory.create(coverImagePath);
        Image coverImage = new Image(imageData);
        document.add(coverImage);

        // **📌 Haberleri Ekle**
        for (NewsEntity news : newsList) {
            document.add(new Paragraph("📰 " + news.getTitle()).setBold().setFontSize(20));
            document.add(new Paragraph(news.getSummary()).setItalic().setFontSize(14));
            document.add(new Paragraph(news.getBody()).setFontSize(12));
            document.add(new Paragraph("\n--------------------------------------\n"));
        }

        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}

