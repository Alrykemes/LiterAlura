package devalrykemes.literalura.domain.book;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import devalrykemes.literalura.domain.author.Author;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(@JsonAlias("title") String title,
                       @JsonAlias("authors") Author author,
                       @JsonAlias("languages") String Languages,
                       @JsonAlias("subjects") List<String> subjects,
                       @JsonAlias("dowloads_count") Long dowloads) {
}
