package com.mockito;

public class LibraryService {
    private final BookRepository bookRepository;

    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId);
        if (book.isAvailable()) {
            book.setAvailable(false);
            bookRepository.save(book);
            return "Book borrowed successfully.";
        } else {
            return "Book is already borrowed.";
        }
    }

    public String returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId);
        if (!book.isAvailable()) {
            book.setAvailable(true);
            bookRepository.save(book);
            return "Book returned successfully.";
        } else {
            return "Book was not borrowed.";
        }
    }
}
    
