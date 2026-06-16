package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(
            String title);

    List<Book> findByAuthorContainingIgnoreCase(
            String author);
    boolean existsByIsbn(String isbn);

    List<Book> findByIsbnContainingIgnoreCase(
            String isbn);
    @Query("""
        SELECT b
        FROM Book b
        WHERE LOWER(b.title)
        LIKE LOWER(CONCAT('%',:keyword,'%'))

        OR LOWER(b.author)
        LIKE LOWER(CONCAT('%',:keyword,'%'))

        OR LOWER(b.isbn)
        LIKE LOWER(CONCAT('%',:keyword,'%'))
    """)
    List<Book> searchBooks(
            @Param("keyword")
            String keyword);
}