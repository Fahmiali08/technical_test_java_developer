package technical.test.fahmi.ali.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technical.test.fahmi.ali.common.ConstantUtil;
import technical.test.fahmi.ali.model.Book;
import technical.test.fahmi.ali.model.BorrowRequest;
import technical.test.fahmi.ali.repository.BookRepository;

import technical.test.fahmi.ali.response.BaseResponse;
import technical.test.fahmi.ali.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    BookService bookService;

    /**
     * Get a list off all books
     * @param status (optional) Book status filter: 1 (borrowed) or 0 (available)
     * @return List of books that match the filter
     */
    @GetMapping
    public ResponseEntity<BaseResponse> getAllBooks(@RequestParam(required = false) Integer status) {
        List<Book> books;
        BaseResponse response = new BaseResponse();
        try {
            if (status != null) {
                books = bookRepository.findByStatus(status);
            } else {
                books = bookRepository.findAll();
            }
            response.message= ConstantUtil.SUCCESS;
            response.result=ConstantUtil.SUCCESS_STATUS;
            response.payload=books;
        }catch (Exception e){
            response.message= ConstantUtil.FAILED;
            response.result=ConstantUtil.FAILED_STATUS;
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.result));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createBook(@RequestBody Book book) {
        BaseResponse response = new BaseResponse();
        response = bookService.saveBook(book);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.result));
    }


    /**
     * Create a new book
     * @param bookId The ID of the book to be updated
     * @param bookDetails object to be created
     * @return response a list status, message and book was posted
     */
    @PutMapping("/{bookId}")
    public ResponseEntity<BaseResponse> updateBook(@PathVariable String bookId, @RequestBody Book bookDetails) {
        BaseResponse response = new BaseResponse();
        response =  bookService.updateBook(bookId, bookDetails);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.result));
    }


    /**
     * Borrow a book based on the book ID.
     * @param bookId The ID of the book to be borrowed
     * @param borrowRequest borrower
     * @return response a list status, message and book was posted
     */
    @PutMapping("/{bookId}/borrow")
    public ResponseEntity<BaseResponse> borrowBook(@PathVariable String bookId, @RequestBody BorrowRequest borrowRequest) {

        BaseResponse response = new BaseResponse();
        response = bookService.borrowBook(bookId, borrowRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.result));
    }


    /**
     * Delete a book based on the book ID.
     * @param bookId The ID of the book to be deleted
     * @return ResponseEntity containing the BaseResponse with the result of the operation
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<BaseResponse> deleteBook(@PathVariable String bookId) {

        BaseResponse response = new BaseResponse();
        response = bookService.deleteBook(bookId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.result));
    }
}