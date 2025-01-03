package devalrykemes.literalura;

import devalrykemes.literalura.controller.AppController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    AppController controller;

    private String opcoes = "\n1 - Pesquise o livro pelo título. \n"
            + "2 - Listar Livros Registrados. \n"
            + "3 - Listar Autores Registrados. \n"
            + "4 - Listar Autores Registrados vivos em um ano. \n"
            + "5 - Listar livros registrados de um determinado idioma. \n"
            + "6 - Limpar Registros de Livros. \n"
            + "0 - Sair \n";

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int select = 0;
        int parada = 0;

        while (parada == 0) {
            System.out.println(opcoes);
            select = sc.nextInt();
            switch (select) {
                case 1: {
                    controller.searchBookByTitle();
                    break;
                }
                case 2: {
                    controller.returnBooksInDb();
                    break;
                }
                case 3: {
                    controller.returnAuthorsInDb();
                    break;
                }
                case 4: {
                    controller.returnAuthorsInYear();
                    break;
                }
                case 5: {
                    controller.returnBooksInLanguage();
                    break;
                }
                case 6: {
                    controller.cleanDb();
                    break;
                }
                case 0: {
                    parada = 1;
                    break;
                }
                default: {
                    System.out.println("Opção Invalida!");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

}
