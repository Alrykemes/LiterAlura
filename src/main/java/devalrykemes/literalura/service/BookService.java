package devalrykemes.literalura.service;

import devalrykemes.literalura.domain.book.Book;
import devalrykemes.literalura.repositories.AuthorRepository;
import devalrykemes.literalura.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public void save(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findAll() {

        List<Book> booksList = new ArrayList<>(bookRepository.findAll());

        booksList.forEach(book -> {
            book.newListInAuthors();
            book.getAuthors().addAll(authorRepository.getAllByIdBook(book.getId()));
            book.newListInGenres();
            book.getGenres().addAll(bookRepository.getGenresBookById(book.getId()));
        });

        return booksList;
    }

    public List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        this.findAll().forEach(book -> { titles.add(book.getTitle()); });
        return titles;
    }

    public List<String> findAllTitlesByIdAuthor(Long idAuthor) {
        return bookRepository.findBooksByAuthorId(idAuthor);
    }

    public List<Book> findAllBooksByLanguage(String language) {
        List<Book> booksList = new ArrayList<>(bookRepository.findBooksByLanguages(language));

        booksList.forEach(book -> {
            book.newListInAuthors();
            book.getAuthors().addAll(authorRepository.getAllByIdBook(book.getId()));
            book.newListInGenres();
            book.getGenres().addAll(bookRepository.getGenresBookById(book.getId()));
        });


        return booksList;
    }

    public void cleanDb() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

}