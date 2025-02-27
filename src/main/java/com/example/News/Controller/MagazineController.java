package com.example.News.Controller;

import com.example.News.Service.MagazineService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/magazine")
public class MagazineController {

    private final MagazineService magazineService;

    public MagazineController(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generate(@RequestParam List<Integer> newsIds) {
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
