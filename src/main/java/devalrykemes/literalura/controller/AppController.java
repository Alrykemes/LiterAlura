package devalrykemes.literalura.controller;

import com.google.gson.JsonObject;
import devalrykemes.literalura.domain.author.Author;
import devalrykemes.literalura.domain.book.Book;
import devalrykemes.literalura.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import devalrykemes.literalura.service.GutendexAPI;

import java.util.Scanner;

@Controller
public class AppController {

    @Autowired
    GutendexAPI gutendexAPI;
    @Autowired
    BookRepository bookRepository;


    private Scanner sc = new Scanner(System.in);

    public void SearchBookByTitle() {
        Book newBook = new Book();

        System.out.println("\n Digite o título do livro a buscar abaixo:");

        try {
            JsonObject json = gutendexAPI.RequestBooksByTitle(sc.nextLine());
            json.getAsJsonObject()
                    .get("results")
                    .getAsJsonArray()
                    .forEach(book -> {
                        book.getAsJsonObject()
                                .get("authors")
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

                        newBook.setTitle(book.getAsJsonObject().get("title").getAsString());
                        newBook.setLanguages(book.getAsJsonObject().get("languages").getAsJsonArray().get(0).getAsString());
                        book.getAsJsonObject()
                                .get("bookshelves")
                                .getAsJsonArray()
                                .asList()
                                .forEach(bookshelve -> newBook.getGenres().add(
                                        bookshelve.toString().replace("Browsing: ", "")
                                ));
                        newBook.setDowloads(book.getAsJsonObject().get("download_count").getAsLong());

                        System.out.println(newBook);
            });

            String savebook = "";

            while (true) {
                System.out.println("Gostaria de salvar o livro pesquisado? (y/n)");

                savebook = sc.nextLine();
                savebook = savebook.toLowerCase();

                if (savebook.equalsIgnoreCase("y")) {
                    // logica para salvar no bd
                    bookRepository.save(newBook);
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

        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }

    public void ReturnBooksInDB() {
        System.out.println(bookRepository.findAll());
    }
}
