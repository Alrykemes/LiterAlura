package devalrykemes.literalura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import devalrykemes.literalura.service.GutendexAPI;

import java.io.IOException;
import java.util.Scanner;

@Controller
public class ApiController {

    @Autowired
    GutendexAPI gutendexAPI;

    private Scanner sc = new Scanner(System.in);

    public void SearchBookByTitle() {

        System.out.println("\n Digite o título do livro a buscar abaixo:");

        try {
            gutendexAPI.RequestBooksByTitle(sc.nextLine());
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }

    }
}
