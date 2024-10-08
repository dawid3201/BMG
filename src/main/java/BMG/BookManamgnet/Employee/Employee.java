package BMG.BookManamgnet.Employee;

import java.util.Date;
import java.util.List;

public interface Employee {
    Long getId();
    String getFull_name();
    String getAddress();
    Long getPhone_number();
    Date getDate_of_birth();
    String getEmail();
    String getPassword();

    List<String> getRoles();
}
