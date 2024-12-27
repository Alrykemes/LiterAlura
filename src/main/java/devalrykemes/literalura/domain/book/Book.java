package devalrykemes.literalura.domain.book;

import devalrykemes.literalura.domain.author.Author;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "authors")
    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private List<Author> authors;

    @ElementCollection
    @Column(name = "genres")
    private List<String> genres;

    @Column(name = "languages")
    private String languages;

    @Column(name = "dowloads_count")
    private Long dowloads;

    public Book() {
        this.genres = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Long getDowloads() {
        return dowloads;
    }

    public void setDowloads(Long dowloads) {
        this.dowloads = dowloads;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        List<String> nameOfAuthors = new ArrayList<>();

        for (Author author : authors) {
            nameOfAuthors.add(author.getName());
        }

        return "\n" + "---------------Livro---------------" + "\n" +
                "Título: " + this.title + "\n" +
                "Autores: " + nameOfAuthors.stream()
                .map( a -> a.replace("\"", ""))
                .collect(Collectors.joining(", ")) + "\n" +
                "Generos: " + this.genres.stream()
                .map( a -> a.replace("\"", ""))
                .collect(Collectors.joining(", "))  + "\n" +
                "languages: " + this.languages + "\n" +
                "dowloads: " + this.dowloads + "\n" +
                "-----------------------------------" + "\n";
    }
}
