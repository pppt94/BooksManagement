package com.example.bookManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>{


    @Query("SELECT DISTINCT b.publisher FROM Book b")
    List<String> findPublishers();

    List<Book> findByUsersId(Long id);

    List<Book> findByUsersWishlistId(Long id);

    List<Book> findByTitleAndPublisher(String title, String publisher);

    List<Book> findByBookReadersReaderId(Long id);

}
