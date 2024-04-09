package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.Book;
import org.apache.el.stream.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookDAO extends MongoRepository<Book, Integer> {
    Book findBookByTitle(String title);

}
