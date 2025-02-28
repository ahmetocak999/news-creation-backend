package com.example.News.Service;

import com.example.News.Entity.NewsEntity;
import com.example.News.Repo.NewsRepo;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;


@Service
public class MagazineService {
    private final NewsRepo newsRepo;
    private final TemplateService templateService;
    private static final Logger logger = LoggerFactory.getLogger(MagazineService.class);

    @Autowired
    public MagazineService(NewsRepo newsRepo, TemplateService templateService) {
        this.newsRepo = newsRepo;
        this.templateService = templateService;
    }

    public byte[] createMagazinePdf(List<Integer> newsIds) throws Exception {
        List<NewsEntity> newsList = newsRepo.findAllById(newsIds);

        // Locale'i belirtmek önemli
        Locale locale = new Locale("tr", "TR");  // Türkçe Locale

        // HTML içeriğini Velocity şablonundan al
        String htmlContent = templateService.generateHtml("news.vm", newsList, locale);

        // Debug için HTML içeriğini dosyaya yazabilirsiniz
        try {
            Files.write(Paths.get("debug-output.html"), htmlContent.getBytes(StandardCharsets.UTF_8));
            logger.info("Debug HTML dosyaya yazıldı");
        } catch (Exception e) {
            logger.warn("Debug HTML dosyası yazılamadı");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Eski flying-saucer/OpenPDF yerine iText 7 kullanmayı değerlendirebilirsiniz
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();

            // Font ayarları - burada tam dosya yolu kullanın
            try {
                fontResolver.addFont(new ClassPathResource("fonts/DejaVuSans.ttf").getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                fontResolver.addFont(new ClassPathResource("fonts/DejaVuSans-Bold.ttf").getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                fontResolver.addFont(new ClassPathResource("fonts/DejaVuSerif.ttf").getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                fontResolver.addFont(new ClassPathResource("fonts/DejaVuSerif-Bold.ttf").getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (IOException e) {
                logger.error("Font yükleme hatası: ", e);
                throw e;
            }
            // PDF oluşturma öncesi log
            logger.info("PDF oluşturmaya başlanıyor...");

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, true); // true = output streaming

            logger.info("PDF başarıyla oluşturuldu");
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.error("PDF oluşturma hatası: ", e);
            throw e;
        } finally {
            byteArrayOutputStream.close();
        }
    }
}



//    private String createHtmlContent(List<NewsEntity> newsList) {
//        StringBuilder htmlContent = new StringBuilder();
//
//        // Güncel tarih bilgisi
//        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("tr", "TR"));
//        String formattedDate = dateFormat.format(new java.util.Date());
//
//        htmlContent.append("<!DOCTYPE html>");
//        htmlContent.append("<html><head>");
//        htmlContent.append("<meta charset='UTF-8'/>");
//        htmlContent.append("<style>");
//
//        // Temel stiller
//        htmlContent.append("@page { margin: 0; }");
//        htmlContent.append("body { font-family: 'DejaVu Sans', sans-serif; background-color: #ffffff; color: #333; margin: 0; padding: 0; }");
//        htmlContent.append("h1, h2, h3 { font-family: 'DejaVu Serif', serif; color: #2c3e50; }");
//        htmlContent.append("p { font-family: 'DejaVu Sans', sans-serif; font-size: 14px; line-height: 1.6; }");
//
//        // Kapak sayfası stilleri
//        htmlContent.append(".cover { height: 100%; background: linear-gradient(135deg, #3498db, #1a5276); color: white; padding: 0; margin: 0; position: relative; }");
//        htmlContent.append(".cover-overlay { position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.3); }");
//        htmlContent.append(".cover-content { position: relative; z-index: 2; padding: 70px 50px; text-align: center; display: flex; flex-direction: column; justify-content: center; height: 100%; box-sizing: border-box; }");
//        htmlContent.append(".magazine-title { font-size: 48px; font-weight: bold; margin: 0 0 20px 0; color: white; text-transform: uppercase; letter-spacing: 3px; text-shadow: 2px 2px 4px rgba(0,0,0,0.5); }");
//        htmlContent.append(".magazine-subtitle { font-size: 24px; margin: 0 0 40px 0; color: rgba(255,255,255,0.9); font-weight: normal; }");
//        htmlContent.append(".issue-info { font-size: 18px; margin-top: 50px; color: rgba(255,255,255,0.8); }");
//
//        // İçindekiler sayfası stilleri
//        htmlContent.append(".toc-container { background-color: #ffffff; padding: 50px; }");
//        htmlContent.append(".toc-header { border-bottom: 2px solid #2c3e50; padding-bottom: 20px; margin-bottom: 30px; }");
//        htmlContent.append(".toc-title { font-size: 32px; font-weight: bold; color: #2c3e50; letter-spacing: 1px; margin: 0; }");
//        htmlContent.append(".toc-list { list-style-type: none; padding-left: 0; margin-left: 0; }");
//        htmlContent.append(".toc-item { font-size: 16px; margin-bottom: 15px; padding-bottom: 15px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; }");
//        htmlContent.append(".toc-page { font-weight: bold; color: #3498db; }");
//
//        // Makale stilleri
//        htmlContent.append(".article { padding: 50px; }");
//        htmlContent.append(".article-header { margin-bottom: 30px; }");
//        htmlContent.append(".article-title { font-size: 28px; color: #2c3e50; margin-bottom: 15px; border-bottom: 2px solid #3498db; padding-bottom: 10px; }");
//        htmlContent.append(".article-summary { font-size: 16px; font-style: italic; color: #555; margin-bottom: 25px; padding: 15px; background-color: #f7f9fa; border-left: 4px solid #3498db; }");
//        htmlContent.append(".article-body { column-count: 2; column-gap: 30px; text-align: justify; }");
//        htmlContent.append(".article-body p { margin-bottom: 15px; }");
//        htmlContent.append(".article-body p:first-letter { font-size: 28px; font-weight: bold; color: #3498db; float: left; line-height: 1; margin-right: 6px; }");
//
//        // Sayfa numarası stilleri
//        htmlContent.append(".page-number { position: running(pageNumber); font-size: 12px; color: #777; text-align: center; }");
//        htmlContent.append("@page { @bottom-center { content: element(pageNumber); } }");
//
//        // Sayfa kesme stileri
//        htmlContent.append(".page-break { page-break-before: always; }");
//
//        htmlContent.append("</style>");
//        htmlContent.append("</head><body>");
//
//        // KAPAK SAYFASI
//        htmlContent.append("<div class='cover'>");
//        htmlContent.append("<div class='cover-overlay'></div>");
//        htmlContent.append("<div class='cover-content'>");
//        htmlContent.append("<h1 class='magazine-title'>📖 Haber Dergisi</h1>");
//        htmlContent.append("<h2 class='magazine-subtitle'>Seçili Haberler Koleksiyonu</h2>");
//        htmlContent.append("<div class='issue-info'>Sayı 1 • ").append(formattedDate).append("</div>");
//        htmlContent.append("</div>");
//        htmlContent.append("</div>");
//
//        // İÇİNDEKİLER SAYFASI
//        htmlContent.append("<div class='page-break toc-container'>");
//        htmlContent.append("<div class='toc-header'>");
//        htmlContent.append("<h2 class='toc-title'>İçindekiler</h2>");
//        htmlContent.append("</div>");
//        htmlContent.append("<ul class='toc-list'>");
//
//        int pageNumber = 3; // Kapak ve içindekiler sayfasından sonra
//        for (NewsEntity news : newsList) {
//            htmlContent.append("<li class='toc-item'>");
//            htmlContent.append("<span>").append(news.getTitle()).append("</span>");
//            htmlContent.append("<span class='toc-page'>").append(pageNumber).append("</span>");
//            htmlContent.append("</li>");
//            pageNumber++;
//        }
//
//        htmlContent.append("</ul>");
//        htmlContent.append("</div>");
//
//        // MAKALELER
//        for (NewsEntity news : newsList) {
//            htmlContent.append("<div class='page-break article'>");
//            htmlContent.append("<div class='article-header'>");
//            htmlContent.append("<h2 class='article-title'>").append(news.getTitle()).append("</h2>");
//            htmlContent.append("<div class='article-summary'>").append(news.getSummary()).append("</div>");
//            htmlContent.append("</div>");
//            htmlContent.append("<div class='article-body'>").append(news.getBody()).append("</div>");
//            htmlContent.append("<div class='page-number'></div>");
//            htmlContent.append("</div>");
//        }
//
//        // ARKA KAPAK
//        htmlContent.append("<div class='page-break cover'>");
//        htmlContent.append("<div class='cover-overlay'></div>");
//        htmlContent.append("<div class='cover-content'>");
//        htmlContent.append("<h2 class='magazine-subtitle'>Teşekkürler</h2>");
//        htmlContent.append("<p style='color: white; margin-top: 20px;'>© 2025 Haber Dergisi Tüm Hakları Saklıdır</p>");
//        htmlContent.append("</div>");
//        htmlContent.append("</div>");
//
//        htmlContent.append("</body></html>");
//        return htmlContent.toString();
//    }
//}
