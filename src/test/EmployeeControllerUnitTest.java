package test;
import controller.EmployeeController;
import controller.ObjectInputStreamWrapper;
import controller.ObjectOutputStreamWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import java.io.*;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeControllerUnitTest {

    @TempDir
    File tempDir;

    private File temp;
    private File mocked;
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
        mocked = mock(File.class);
    }


    @Test
    @DisplayName("create function unit testing")
    void test1() {
        System.out.println(tempDir.getAbsolutePath());
        assertTrue(tempDir.exists());
        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));
        Employee e1 = new Employee("librarianE", "12345678ej", "elia", "noor", "elian@gmail.com", Role.LIBRARIAN, "355695214012", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));
        assertAll(
                ()->assertTrue(temp.exists()),
                () ->assertTrue(ec.create(e, temp,employeesAll)),
                () ->assertTrue(ec.create(e1, temp,employeesAll)),
                () ->assertEquals(2,employeesAll.size()),
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

        Main.employeesAll.clear();
    }

    @Test
    @DisplayName("Unit testing loadUsersFromFile function")
    void test2()throws IOException
    {
        System.out.println(tempDir.getAbsolutePath());
        assertTrue(tempDir.exists());


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
        Main.employeesAll.clear();
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
        Main.employeesAll.clear();
    }
    @Test
    @DisplayName("UpdateAll() - Unit Test")
    void test4() throws FileNotFoundException {
        Main.DATA_FILE = temp;
        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));
        Employee e1 = new Employee("librarianE", "12345678ej", "elia", "noor", "elian@gmail.com", Role.LIBRARIAN, "355695214012", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));

        Main.employeesAll.add(e);
        Main.employeesAll.add(e1);
        boolean checkUpdate = ec.updateAll();
        assertTrue(Main.DATA_FILE.exists());
        assertTrue(Files.exists(Main.DATA_FILE.toPath()));
        assertTrue(checkUpdate);
        ObservableList<Employee> written = FXCollections.observableArrayList();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(temp))) {
            while (true) {
                try {
                    written.add((Employee) inputStream.readObject());
                } catch (EOFException ee) {
                    break;
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        assertEquals(Main.employeesAll.size(), written.size());
        assertEquals(Main.employeesAll.getFirst(), written.getFirst());
        assertEquals(Main.employeesAll.getLast(), written.getLast());
        Main.employeesAll.clear();
    }

    //Created test cases for create and loadUsersFromFile with mocked lists
    //so there are no external interactions
    @Test
    @DisplayName("create function with mocked file unit testing")
    void test1MockFile() throws IOException {
        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));
        FileOutputStream mockFileOutputStream = mock(FileOutputStream.class);
        ObjectOutputStreamWrapper mockObjectOutputStreamWrapper = mock(ObjectOutputStreamWrapper.class);

        File mockFile = mock(File.class);
        when(mockFile.length()).thenReturn(0L);
        doNothing().when(mockObjectOutputStreamWrapper).writeObject(any(Employee.class));
        EmployeeController ec = spy(new EmployeeController());


        doReturn(mockFileOutputStream).when(ec).createFileOutputStream(mockFile);
        doReturn(mockObjectOutputStreamWrapper).when(ec).createObjectOutputStream(mockFileOutputStream);

        boolean res = ec.create(e, mockFile, employeesAll);
        assertTrue(res);
        assertEquals(1, employeesAll.size());
        assertEquals("librarianKeit", employeesAll.getFirst().getUsername());
        //see if it was called once
        verify(mockObjectOutputStreamWrapper, times(1)).writeObject(e);
        verify(mockObjectOutputStreamWrapper, times(1)).close();
        verify(mockFileOutputStream, times(1)).close();
    }
    @Test
    @DisplayName("load users from file by mocking file unit testing")
    void test2MockFileForLoadUsers() throws Exception {
        // mocking FileInputStream
        FileInputStream mockFileInputStream = mock(FileInputStream.class);
        // mocking ObjectInputStreamWrapper
        ObjectInputStreamWrapper mockInputStream = mock(ObjectInputStreamWrapper.class);

        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));
        Employee e1 = new Employee("librarianE", "12345678ej", "elia", "noor", "elian@gmail.com", Role.LIBRARIAN, "355695214012", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));

        when(mockInputStream.readObject())
                .thenReturn(e)
                .thenReturn(e1)
                .thenThrow(new EOFException());

        doNothing().when(mockInputStream).close();
        EmployeeController ec = Mockito.spy(new EmployeeController());
        Mockito.doReturn(mockInputStream).when(ec).createObjectInputStreamWrapper(any(FileInputStream.class));
        Mockito.doReturn(mockFileInputStream).when(ec).createFileInputStream(any(File.class));
        boolean result = ec.loadUsersFromFile(mocked, employeesAll);
        assertTrue(result);
        assertEquals(2, employeesAll.size());
        assertEquals("librarianKeit", employeesAll.get(0).getUsername());
        assertEquals("librarianE", employeesAll.get(1).getUsername());
        verify(mockInputStream, times(3)).readObject();
        verify(mockInputStream, times(1)).close();
    }

}
