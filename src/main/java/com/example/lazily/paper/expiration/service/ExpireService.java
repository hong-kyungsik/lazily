package com.example.lazily.paper.expiration.service;

import com.example.lazily.paper.book.entity.Book;
import com.example.lazily.paper.book.service.BookDeleteService;
import com.example.lazily.paper.book.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpireService {
    private static final int EXPIRATION_YEAR = 10;

    private final BookSearchService bookSearchService;
    private final BookDeleteService bookDeleteService;

    public void expire(){
        LocalDateTime old = LocalDateTime.now().minusYears(EXPIRATION_YEAR);
        List<Book> oldBooks = bookSearchService.findOldBook(old);
        for (Book oldBook : oldBooks) {
            bookDeleteService.deleteBook(oldBook);
        }
    }
}
