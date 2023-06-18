package technical.test.fahmi.ali.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technical.test.fahmi.ali.model.Book;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByStatus(Integer status);

    Book findByBookId(String bookId);
}

