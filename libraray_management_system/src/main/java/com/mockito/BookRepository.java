package com.mockito;

public interface BookRepository {
    Book findById(Long id);
    void save(Book book);
}
