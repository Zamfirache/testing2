package com.example.unittesting2.service;


import com.example.unittesting2.domain.Book;
import com.example.unittesting2.exception.BookAlreadyExistsException;
import com.example.unittesting2.exception.BookNotFoundException;
import com.example.unittesting2.model.UpdateBookRequest;
import com.example.unittesting2.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
      .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, UpdateBookRequest book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.getId(),
                            existingBook.getIsbn(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getPrice());
                    return bookRepository.save(bookToUpdate);
                }).orElseThrow(() -> new BookNotFoundException(isbn));

    }
}
