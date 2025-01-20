package test;
import controller.EmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Access;
import model.Employee;
import model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerUnitTest {

    @TempDir
    File tempDir;

    private File temp;
    private Employee e;
    private ObservableList<Employee> employeesAll;
    private EmployeeController ec;
    @BeforeEach
    void SetUp() throws IOException {
        temp = new File(tempDir, "employees.dat");
        if (!temp.createNewFile()) {
            System.out.println("Failed to create temp file.");
        }
        employeesAll = FXCollections.observableArrayList();
        ec = new EmployeeController();
    }


    @Test
    @DisplayName("create function unit testing")
    void test1() {
        System.out.println(tempDir.getAbsolutePath());
        assertTrue(tempDir.exists());

        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, Calendar.MAY, 25);
        Date birthDate = calendar.getTime();
        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate);
        Employee e1 = new Employee("librarianE", "12345678ej", "elia", "noor", "elian@gmail.com", Role.LIBRARIAN, "355695214012", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate);
        assertAll(
                ()->assertTrue(temp.exists()),
                () ->assertTrue(ec.create(e, temp,employeesAll)),
                () ->assertEquals(1,employeesAll.size()),
                () ->{
                    ObservableList<Employee> created = FXCollections.observableArrayList();
                    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(temp))) {
                        while (true) {
                            try {
                                created.add((Employee) inputStream.readObject());
                            } catch (EOFException ex) {
                                break;
                            }
                        }
                    }
                    assertEquals(employeesAll.size(),created.size());
                    assertEquals(employeesAll.getFirst(),created.getFirst());
                    assertEquals(employeesAll.getLast(),created.getLast());

                }

        );


    }

    @Test
    @DisplayName("Unit testing loadUsersFromFile function")
    void test2()throws IOException
    {
        System.out.println(tempDir.getAbsolutePath());
        assertTrue(tempDir.exists());

        Employee e;
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(temp))){
            Calendar calendar = Calendar.getInstance();
            calendar.set(1990, Calendar.MAY, 25);
            Date birthDate = calendar.getTime();
            e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate);
            outputStream.writeObject(e);
        }
        ec.loadUsersFromFile(temp,employeesAll);

        assertAll(
                () ->assertTrue(temp.exists()),
                ()->assertTrue(Files.exists(temp.toPath())),
                () -> assertEquals(1,employeesAll.size()),
                () ->assertEquals(e,employeesAll.getFirst()),
                () ->assertTrue(ec.loadUsersFromFile(temp,employeesAll))
        );
    }

    @Test
    @DisplayName("Writing and reading from file test")
    void test3(){
        assertTrue(tempDir.exists());
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, Calendar.MAY, 25);
        Date birthDate = calendar.getTime();
        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate);
        assertTrue(temp.exists());
        assertTrue(Files.exists(temp.toPath()));
        assertTrue(ec.create(e,temp,employeesAll));
        assertEquals(e,employeesAll.getFirst());
        assertEquals(1,employeesAll.size());
        employeesAll.clear();
        assertTrue(ec.loadUsersFromFile(temp,employeesAll));
        assertEquals(e,employeesAll.getFirst());
        assertEquals(1,employeesAll.size());
    }

}