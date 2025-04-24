package com.example.bookservice.businesslogiclayer;


import com.example.bookservice.dataaccesslayer.Book;
import com.example.bookservice.dataaccesslayer.BookIdentifier;
import com.example.bookservice.dataaccesslayer.BookRepository;
import com.example.bookservice.datamapperlayer.BookRequestMapper;
import com.example.bookservice.datamapperlayer.BookResponseMapper;
import com.example.bookservice.presentationlayer.BookRequestModel;
import com.example.bookservice.presentationlayer.BookResponseModel;
import com.example.bookservice.utils.exceptions.DuplicateISBNException;
import com.example.bookservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookRequestMapper bookRequestMapper;
    private final BookResponseMapper bookResponseMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           BookRequestMapper bookRequestMapper,
                           BookResponseMapper bookResponseMapper) {
        this.bookRepository = bookRepository;
        this.bookRequestMapper = bookRequestMapper;
        this.bookResponseMapper = bookResponseMapper;
    }

    @Override
    public List<BookResponseModel> getAllBooks() {
        return bookResponseMapper.entityToResponseModelList(bookRepository.findAll());
    }

    @Override
    public BookResponseModel getBookByBookId(String bookId) {
        Book book = bookRepository.findBookByBookIdentifier_BookId(bookId);
        if (book == null) throw new NotFoundException("Book with ID "+ bookId +" not found");
        return bookResponseMapper.entityToResponseModel(book);
    }

    @Override
    public BookResponseModel addBook(BookRequestModel bookRequestModel) {

        if(bookRepository.existsByIsbn(bookRequestModel.getIsbn())){

            throw new DuplicateISBNException("each isbn must be unique...");
        }

            BookIdentifier bookIdentifier = new BookIdentifier();
            Book book = bookRequestMapper.requestModelToEntity(bookRequestModel, bookIdentifier);
            return bookResponseMapper.entityToResponseModel(bookRepository.save(book));

    }

    @Override
    public BookResponseModel updateBook(String bookId, BookRequestModel bookRequestModel) {
        Book existingBook = bookRepository.findBookByBookIdentifier_BookId(bookId);
        if (existingBook == null)
            throw new NotFoundException("Book with ID " + bookId + " not found");

        Book duplicateBook = bookRepository.findBookByIsbn(bookRequestModel.getIsbn());

        if (duplicateBook != null && !duplicateBook.getBookIdentifier().getBookId().equals(bookId)) {
            throw new DuplicateISBNException("ISBN must be unique. Another book already uses this ISBN.");
        }

        existingBook.setTitle(bookRequestModel.getTitle());
        existingBook.setAuthor(bookRequestModel.getAuthor());
        existingBook.setIsbn(bookRequestModel.getIsbn());
        existingBook.setPrice(bookRequestModel.getPrice());
        existingBook.setPublishedDate(bookRequestModel.getPublishedDate());

        return bookResponseMapper.entityToResponseModel(bookRepository.save(existingBook));
    }


    @Override
    public void deleteBook(String bookId) {
        Book book = bookRepository.findBookByBookIdentifier_BookId(bookId);
        if (book == null) throw new NotFoundException("Book with ID "+ bookId +" not found");
        bookRepository.delete(book);
    }
}