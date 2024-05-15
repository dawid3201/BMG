package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitle(String title);
    Book findBookById(Long bookId);

}
