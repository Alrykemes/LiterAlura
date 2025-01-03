package devalrykemes.literalura.repositories;

import devalrykemes.literalura.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(nativeQuery = true, value = "SELECT bg.genres FROM book_genres AS bg \n" +
            "INNER JOIN book AS b\n" +
            "ON b.book_id = bg.book_book_id\n" +
            "WHERE book_book_id = ?1")
    List<String> getGenresBookById(Long idBook);

    @Query(nativeQuery = true,
            value = "SELECT b.title from book AS b \n" +
            "INNER JOIN book_authors AS ba ON\n" +
            "ba.book_book_id = b.book_id\n" +
            "WHERE ba.authors_author_id = ?1")
    List<String> findBooksByAuthorId(Long idAuthor);

    @Query(nativeQuery = true, value = "SELECT * FROM book AS b WHERE b.languages = ?1")
    List<Book> findBooksByLanguages(String language);

    @Query(nativeQuery = true, value = "BEGIN;\n" +
            "\n" +
            "DELETE FROM book_genres;\n" +
            "DELETE FROM book_authors;\n" +
            "DELETE FROM book;\n" +
            "DELETE FROM author;")
    void cleanDb();
}
