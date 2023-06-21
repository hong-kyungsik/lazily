package com.example.lazily.paper.book.service;

import com.example.lazily.paper.book.entity.Book;
import com.example.lazily.paper.book.entity.Edition;
import com.example.lazily.paper.book.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookDeleteService {

    private final BookRepository repository;

    @Transactional
    public void deleteBook(Book book){
        book = repository.findById(book.getId()).orElseThrow(EntityNotFoundException::new);
        for (Edition edition : book.getEditions()) {
            log.info("edition delete. {}", edition.getId());
        }
        repository.delete(book);
    }
}
