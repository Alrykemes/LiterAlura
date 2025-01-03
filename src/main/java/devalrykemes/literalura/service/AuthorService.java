package devalrykemes.literalura.service;

import devalrykemes.literalura.domain.author.AuthorPrintDto;
import devalrykemes.literalura.repositories.AuthorRepository;
import devalrykemes.literalura.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    public List<AuthorPrintDto> findAll() {
        List<AuthorPrintDto> authorPrintDtos = new ArrayList<>();

        authorRepository.getAllAuthors().forEach(author -> {
            authorPrintDtos.add(new AuthorPrintDto(author, bookRepository.findBooksByAuthorId(author.getId())));
        });
        return authorPrintDtos;
    }

    public List<AuthorPrintDto> findByYearAlive(int yearAlive) {
        List<AuthorPrintDto> authorPrintDtos = new ArrayList<>();

        authorRepository.getAllStayAliveInYear(yearAlive).forEach(author -> {
            authorPrintDtos.add(new AuthorPrintDto(author, bookRepository.findBooksByAuthorId(author.getId())));
        });
        return authorPrintDtos;
    }
}
