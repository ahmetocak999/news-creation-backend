package com.example.News.Controller;

import com.example.News.Service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pdf")
public class MagazineController {

    @Autowired
    private MagazineService magazineService;


    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestParam List<Integer> newsIds) {
        try {
            byte[] pdfContent = magazineService.createMagazinePdf(newsIds);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment().filename("magazine.pdf").build());
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
