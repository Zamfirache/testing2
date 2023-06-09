package com.example.unittesting2;

import com.example.unittesting2.domain.Book;
import com.example.unittesting2.repository.BookRepository;
import com.example.unittesting2.service.BookService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;



    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }


    private Book mockBook()
    {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("1234567890");
        book.setAuthor("Author1");
        book.setTitle("Title1");
        book.setPrice(20.0);

        return book;
    }


    @Test
    public void getAllBooks()
    {
        when(bookRepository.findAll()).thenReturn(List.of());
        List<Book> result = (List<Book>) bookService.viewBookList();
        Assertions.assertEquals(0,result.size());
    }

    @Test
    public void getBookByISBN()
    {
        Book book = mockBook();
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book));
        Book result = bookService.viewBookDetails("1234567890");
        Assertions.assertEquals(book,result);

    }


    @Test
    public void addBook()
    {
        Book book = mockBook();
        when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.addBookToCatalog(book);
        Assertions.assertEquals(book, result);
    }

    @Test
    public void deleteBook()
    {

        Book book = mockBook();
        String isbn = book.getIsbn();
        bookService.removeBookFromCatalog(isbn);
        verify(bookRepository,times(1)).deleteByIsbn(isbn);

    }


    @Test
    void createMock()
    {
        List<String> mockList = mock(ArrayList.class);

        mockList.add("value");
        //verify(mockList).add("value");

        Assertions.assertEquals(0,mockList.size());
    }

    @Test
    void createSpy()
    {
        List<String> spyList = spy(ArrayList.class);

        spyList.add("value");
       // verify(spyList).add("value");

        Assertions.assertEquals(1,spyList.size());
    }




}
