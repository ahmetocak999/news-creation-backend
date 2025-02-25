package com.example.News.Service;

import com.example.News.Entity.NewsEntity;
import com.example.News.Repo.NewsRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class MagazineService {

    @Autowired
    private NewsRepo newsRepo;

    public byte[] createMagazinePdf(List<Integer> newsIds) throws Exception {
        List<NewsEntity> newsList = newsRepo.findAllById(newsIds);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 18);
        Font newsHeadingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Font summaryFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 14);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("📖 Haber Dergisi", titleFont));
        document.add(new Paragraph("Seçili Haberler", subtitleFont));

        for (NewsEntity news : newsList) {
            document.add(new Paragraph("📰 " + news.getTitle(), newsHeadingFont));
            document.add(new Paragraph(news.getSummary(), summaryFont));
            document.add(new Paragraph(news.getBody(), bodyFont));
            document.add(new Paragraph("\n--------------------------------------\n"));
        }

        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}