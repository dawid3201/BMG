package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookDAO extends MongoRepository<Book, String> {
    Book findBookByTitle(String title);
    Book findBookByID(String bookId);

}
