package devalrykemes.literalura.controller;

import com.google.gson.JsonObject;
import devalrykemes.literalura.domain.author.Author;
import devalrykemes.literalura.domain.book.Book;
import devalrykemes.literalura.service.AuthorService;
import devalrykemes.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import devalrykemes.literalura.service.GutendexAPI;
import java.util.Scanner;

@Controller
public class AppController {

    @Autowired
    GutendexAPI gutendexAPI;
    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;


    private Scanner sc = new Scanner(System.in);

    public void searchBookByTitle() {
        Book newBook = new Book();

        System.out.println("\n Digite o título do livro a buscar abaixo:");

        try {
            JsonObject json = gutendexAPI.requestBooksByTitle(sc.nextLine());
            json = json.getAsJsonObject()
                    .get("results")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject();

            json.get("authors")
                    .getAsJsonArray()
                    .forEach(author -> {
                        newBook.getAuthors().add(
                                new Author(
                                        author.getAsJsonObject().get("name").toString(),
                                        Integer.parseInt(author.getAsJsonObject().get("birth_year").toString()),
                                        Integer.parseInt(author.getAsJsonObject().get("death_year").toString())
                                )
                        );
                    });

            newBook.setTitle(json.get("title").getAsString());
            newBook.setLanguages(json.get("languages").getAsJsonArray().get(0).getAsString());
            json.get("bookshelves")
                    .getAsJsonArray()
                    .asList()
                    .forEach(bookshelve -> newBook.getGenres().add(
                            bookshelve.toString().replace("Browsing: ", "")
                    ));
            newBook.setDowloads(json.get("download_count").getAsLong());
            System.out.println(newBook);

            if (bookService.getTitles().contains(newBook.getTitle())) {
                System.out.println("Livro existente no banco de dados!");
            } else {
                while (true) {
                    String savebook = "";

                    System.out.println("Gostaria de salvar o livro pesquisado? (y/n)");

                    savebook = sc.nextLine();
                    savebook = savebook.toLowerCase();

                    if (savebook.equalsIgnoreCase("y")) {
                        bookService.save(newBook);
                        System.out.println("Livro salvo!");
                        break;
                    } else if (savebook.equalsIgnoreCase("n")) {
                        // nada kkkk
                        System.out.println("Livro não salvo!");
                        break;
                    } else {
                        System.out.println("Opção Invalida!");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }

    public void returnBooksInDb() {
        try {
            bookService.findAll().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }

    public void returnAuthorsInDb() {
        try {
            authorService.findAll().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }

    public void returnAuthorsInYear() {

        System.out.println("\n Digite o um ano para buscar autor salvo no banco de dados: (ex: 1789)");

        int ano = sc.nextInt();

        if (ano < 0 || ano > 2025) {
            System.out.println("ano invalido!");
        } else {
            try {
                authorService.findByYearAlive(ano).forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("erro: " + e.getMessage());
            }
        }
    }

    public void returnBooksInLanguage() {
            System.out.println("Digite um idioma para buscar livro salvo no banco de dados: (ex: en)\n");
            String language = sc.nextLine();
        try {
            bookService.findAllBooksByLanguage(language.toLowerCase()).forEach(System.out::println);
        } catch (Exception e) {
                System.out.println("erro: " + e.getMessage());
        }
    }

    public void cleanDb() {
        while (true) {
            String option = "";

            System.out.println("Gostaria de limpar o banco de dados? (y/n)");

            option = sc.nextLine();

            if (option.equalsIgnoreCase("y")) {
                try {
                    bookService.cleanDb();
                } catch (Exception e) {
                    System.out.println("erro: " + e.getMessage());
                }
                System.out.println("Dados apagados, banco de dados limpo!");
                break;
            } else if (option.equalsIgnoreCase("n")) {
                // nada kkkk
                System.out.println("Dados apagados mantidos, banco de dados não limpo!");
                break;
            } else {
                System.out.println("Opção Invalida!");
            }
        }
    }
}
