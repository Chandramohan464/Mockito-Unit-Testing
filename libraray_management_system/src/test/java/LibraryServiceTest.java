import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mockito.Book;
import com.mockito.BookRepository;
import com.mockito.LibraryService;

public class LibraryServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBorrowBook_SuccessfulBorrow() {
        Long bookId = 1L;
        Book book = new Book(bookId, true);
        when(bookRepository.findById(bookId)).thenReturn(book);

        String result = libraryService.borrowBook(bookId);

        assertEquals("Book borrowed successfully.", result);
        assertEquals(false, book.isAvailable());
        verify(bookRepository).save(book);
    }

    @Test
    public void testBorrowBook_AlreadyBorrowed() {
        Long bookId = 1L;
        Book book = new Book(bookId, false);
        when(bookRepository.findById(bookId)).thenReturn(book);

        String result = libraryService.borrowBook(bookId);

        assertEquals("Book is already borrowed.", result);
        verify(bookRepository, never()).save(book);
    }

    @Test
    public void testReturnBook_SuccessfulReturn() {
        Long bookId = 1L;
        Book book = new Book(bookId, false);
        when(bookRepository.findById(bookId)).thenReturn(book);

        String result = libraryService.returnBook(bookId);

        assertEquals("Book returned successfully.", result);
        assertEquals(true, book.isAvailable());
        verify(bookRepository).save(book);
    }

    @Test
    public void testReturnBook_NotBorrowed() {
        Long bookId = 1L;
        Book book = new Book(bookId, true);
        when(bookRepository.findById(bookId)).thenReturn(book);

        String result = libraryService.returnBook(bookId);

        assertEquals("Book was not borrowed.", result);
        verify(bookRepository, never()).save(book);
    }
}
