package com.example.lazily.paper.book.service;

import com.example.lazily.paper.book.entity.Book;
import com.example.lazily.paper.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookRepository repository;

    @Transactional(readOnly = true)
    public List<Book> findOldBook(LocalDateTime old){
        return repository.findByPublishedDateBefore(old);
    }

}
