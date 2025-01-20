package test;

import controller.EmployeeController;
import main.Main;
import model.Access;
import model.Employee;
import model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
class EmployeeControllerUnitTest {

    private File temp;
    private FileInputStream mockFileInput;
    private ObjectInputStream mockInputStream;
    private Employee e;
    private EmployeeController ec;
    @BeforeEach
    void SetUp() throws IOException {
        Main.employeesAll.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, Calendar.MAY, 25);
        Date birthDate = calendar.getTime();
        e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate);
        Main.employeesAll.add(e);
        temp = File.createTempFile("testing",".dat");
        temp.deleteOnExit();
        ec = new EmployeeController();


    }


    @Test
    void loadUsersFromFile()throws IOException, ClassNotFoundException
    {


    }

    @Test
    void create() {

    }
}