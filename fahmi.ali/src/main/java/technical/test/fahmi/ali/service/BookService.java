package technical.test.fahmi.ali.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technical.test.fahmi.ali.common.ConstantUtil;
import technical.test.fahmi.ali.model.Book;
import technical.test.fahmi.ali.model.BorrowRequest;
import technical.test.fahmi.ali.repository.BookRepository;
import technical.test.fahmi.ali.response.BaseResponse;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    public BaseResponse saveBook(Book book) {
        BaseResponse response = new BaseResponse();
        try {
            Book inquiryBookByBookId = bookRepository.findByBookId(book.getBookId());
            if(inquiryBookByBookId==null){
                Book createdBook = bookRepository.save(book);
                response.result= ConstantUtil.CREATED_STATUS;
                response.message=ConstantUtil.CREATED;
                response.payload=createdBook;
            }else{
                response.result=ConstantUtil.CONFLICT_STATUS;
                response.message=ConstantUtil.CONFLICT;
            }
        }catch (Exception e){
            response.result=ConstantUtil.FAILED_STATUS;
            response.message=ConstantUtil.FAILED;
        }
        return response;
    }

    public BaseResponse updateBook(String bookId, Book bookDetails) {
        BaseResponse response = new BaseResponse();
        try {
            Book book = bookRepository.findByBookId(bookId);
            if(book==null){
                response.result=ConstantUtil.NOT_FOUND_STATUS;
                response.message=ConstantUtil.BOOK_NOT_FOUND;
            }else{
                book.setTitle(bookDetails.getTitle());
                book.setAuthor(bookDetails.getAuthor());
                book.setPublisher(bookDetails.getPublisher());
                book.setReleaseDate(bookDetails.getReleaseDate());
                book.setPageCount(bookDetails.getPageCount());
                book.setStatus(bookDetails.getStatus());
                book.setBorrower(bookDetails.getBorrower());
                book.setBorrowDate(bookDetails.getBorrowDate());
                book.setReturnDate(bookDetails.getReturnDate());

                Book updatedBook = bookRepository.save(book);
                response.result=ConstantUtil.SUCCESS_STATUS;
                response.message=ConstantUtil.SUCCESS;
                response.payload=updatedBook;
            }
        }catch (Exception e){
            response.result=ConstantUtil.FAILED_STATUS;
            response.message=ConstantUtil.FAILED;
        }
        return response;
    }

    public BaseResponse borrowBook(String bookId, BorrowRequest borrowRequest) {
        BaseResponse response = new BaseResponse();
        try {
            Book book = bookRepository.findByBookId(bookId);
            if(book==null){
                response.result=ConstantUtil.NOT_FOUND_STATUS;
                response.message=ConstantUtil.BOOK_NOT_FOUND;
            }else {
                if(book.getStatus()==1){
                    response.result=ConstantUtil.NOT_FOUND_STATUS;
                    response.message=ConstantUtil.BOOK_BORROWED;
                }else{
                    book.setStatus(1);
                    book.setBorrower(borrowRequest.getBorrower());
                    book.setBorrowDate(borrowRequest.getBorrowDate());
                    book.setReturnDate(borrowRequest.getReturnDate());
                    Book borrowedBook = bookRepository.save(book);
                    response.result=ConstantUtil.SUCCESS_STATUS;
                    response.message=ConstantUtil.SUCCESS;
                    response.payload=borrowedBook;
                }
            }
        }catch (Exception e){
            response.result=ConstantUtil.FAILED_STATUS;
            response.message=ConstantUtil.FAILED;
        }
        return response;
    }

    public BaseResponse deleteBook(String bookId) {
        BaseResponse response = new BaseResponse();
        try {
            Book book = bookRepository.findByBookId(bookId);
            if(book==null){
                response.result=ConstantUtil.NOT_FOUND_STATUS;
                response.message=ConstantUtil.BOOK_NOT_FOUND;
            }else{
                bookRepository.delete(book);
                response.result=ConstantUtil.SUCCESS_STATUS;
                response.message=ConstantUtil.BOOK_SUCCESSFULLY_DELETED;
            }
        }catch (Exception e){
            response.result=ConstantUtil.FAILED_STATUS;
            response.message=ConstantUtil.FAILED;
        }
        return response;
    }
}
