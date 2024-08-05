package BMG.BookManamgnet.Book;

import BMG.BookManamgnet.Book.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitle(String title);
    Book findBookById(Long bookId);

}
