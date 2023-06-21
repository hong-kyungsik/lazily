package com.example.lazily.paper.expiration.service;

import com.example.lazily.paper.book.entity.Book;
import com.example.lazily.paper.book.entity.Edition;
import com.example.lazily.paper.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExpireServiceTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ExpireService service;

    @BeforeEach
    void init(){
        Book book1 = new Book(
            null, "첫번째 책", LocalDateTime.now().minusYears(11), new ArrayList<>());

        book1.getEditions().add(new Edition(null, 1, book1));
        book1.getEditions().add(new Edition(null, 2, book1));
        book1.getEditions().add(new Edition(null, 3, book1));

        repository.save(book1);
    }

    @Test
    void expire() {
        service.expire();
    }
}