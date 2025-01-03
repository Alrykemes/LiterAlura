package devalrykemes.literalura.domain.author;

import java.util.List;

public record AuthorPrintDto(String name, int yearOfBirth, int yearOfDeath, List<String> titlesWrite) {

    public AuthorPrintDto(Author author, List<String> titlesWrite) {
        this(author.getName(), author.getYearOfBirth(), author.getYearOfDeath(), titlesWrite);
    }

    @Override
    public String toString() {
        return "\n" + "---------------Author---------------" + "\n" +
                "Nome: " + this.name + "\n" +
                "Nascimento: " + this.yearOfBirth + "\n" +
                "Morte: " + this.yearOfDeath + "\n" +
                "Títulos: " + this.titlesWrite + "\n" +
                "-----------------------------------" + "\n";
    }
}
