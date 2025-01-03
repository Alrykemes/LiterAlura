package devalrykemes.literalura.repositories;

import devalrykemes.literalura.domain.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(nativeQuery = true,
            value = "SELECT a.author_id, a.name, a.year_of_birth, a.year_of_death FROM author AS a \n" +
            "INNER JOIN book_authors AS ba \n" +
            "ON ba.authors_author_id = a.author_id \n" +
            "WHERE book_book_id = ?1")
    List<Author> getAllByIdBook(Long idBook);

    @Query(nativeQuery = true, value = "SELECT * FROM author")
    List<Author> getAllAuthors();

    @Query(nativeQuery = true,
            value = "SELECT a.author_id, a.name, a.year_of_birth, a.year_of_death FROM author AS a \n" +
                    "WHERE a.year_of_death >= ?1 AND a.year_of_birth <= ?1")
    List<Author> getAllStayAliveInYear(int year);
}
