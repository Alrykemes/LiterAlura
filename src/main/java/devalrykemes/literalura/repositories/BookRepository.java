package devalrykemes.literalura.repositories;

import devalrykemes.literalura.domain.author.Author;
import devalrykemes.literalura.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> getAllBooks();

    public List<Author> getAllAuthors();

    List<Book> getAllBooksByLanguages(String languages);

    public void dropDB();
}
