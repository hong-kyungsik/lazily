package com.example.lazily.paper.book.repository;

import com.example.lazily.paper.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByPublishedDateBefore(LocalDateTime publishedDate);
}
