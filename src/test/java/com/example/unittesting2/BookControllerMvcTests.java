package com.example.unittesting2;


import com.example.unittesting2.domain.Book;
import com.example.unittesting2.exception.BookNotFoundException;
import com.example.unittesting2.service.BookService;
import com.example.unittesting2.web.BookController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;


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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);
        mockMvc
                .perform(get("/books/" + isbn))
      .andExpect(status().isNotFound());
    }

    @Test
    void whenGetBookExistsShouldReturnOk() throws Exception {
        Book book = mockBook();
        String isbn = "1234567890";

        given(bookService.viewBookDetails(isbn)).willReturn(book);

        mockMvc.perform(get("/books/" +isbn))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void whenPostBookShouldReturnOk() throws Exception
    {
        Book book = mockBook();
        given(bookService.addBookToCatalog(book)).willReturn(book);

        mockMvc.perform(post("/books")
                .content(asJsonString(book))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

}
