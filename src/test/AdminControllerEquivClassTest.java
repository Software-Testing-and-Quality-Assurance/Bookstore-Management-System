/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * Equivalence Class Testing * * * * * * * *
 * * * * * Test done by: Klaudia Tamburi * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package test;

import controller.AdminController;
import controller.EmployeeController;
import model.Employee;
import model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerEquivClassTest {

    /*
    * Test Cases for function addEmployee
    * - 1 - Librarian w/ valid input - assert True
    * - 2 - Manager w/ valid input - assert True
    * - 3 - Employee w/ invalid input (eg. role = null or salary <0 or password<8) - Throw Exception
    * - 4 - Employee w/ dupe username - Throw Exception
    * */

    private EmployeeController ecMock;
    private AdminController ac;

    @BeforeEach
    public void setUp() {
        ecMock = mock(EmployeeController.class);
        ac = new AdminController(ecMock);
    }

    @Test
    @DisplayName("Equivalence Class Testing - Success Librarian")
    public void test1() {
        String username = "ktamburi";
        String password = "password123";
        String name = "klaudia";
        String surname = "tamburi";
        String email = "klaudiatamburi@gmail.com";
        String phone = "355695315247";
        double salary = 5000.00;
        Role role = Role.LIBRARIAN;
        Date birthday = new Date();

        when(ecMock.employeeFound(username)).thenReturn(false);
        when(ecMock.create(any(Employee.class))).thenReturn(true);

        assertTrue(ac.addEmployee(username, password, name, surname, email, phone, salary, role, birthday));
        verify(ecMock, times(1)).create(argThat(employee ->
                employee.getRole() == Role.LIBRARIAN
        ));
    }

    @Test
    @DisplayName("Equivalence Class Testing - Success Manager")
    public void test2() {
        String username = "ktamburi";
        String password = "password123";
        String name = "klaudia";
        String surname = "tamburi";
        String email = "klaudiatamburi@gmail.com";
        String phone = "355695315247";
        double salary = 5000.00;
        Role role = Role.MANAGER;
        Date birthday = new Date();

        when(ecMock.employeeFound(username)).thenReturn(false);
        when(ecMock.create(any(Employee.class))).thenReturn(true);

        assertTrue(ac.addEmployee(username, password, name, surname, email, phone, salary, role, birthday));
        verify(ecMock, times(1)).create(argThat(employee ->
                employee.getRole() == Role.MANAGER
        ));
    }

    @ParameterizedTest
    @CsvSource({
            "ktamburi, password123, klaudia, tamburi, klaudiatamburi@gmail.com, 355695315247, 0.00, Salary has to be more than 0.",
            ", password123, klaudia, tamburi, klaudiatamburi@gmail.com, 355695315247, 500.00, Don't leave empty fields!",
            "ktamburi, 123, klaudia, tamburi, klaudiatamburi@gmail.com, 355695315247, 500.00, Password has to have 8 or more characters!",
            "ktamburi, password123, klaudia, tamburi, klaudiatamburi@gmail.com, 695315247, 500.00, Enter valid phone number.",
            "ktamburi, password123, klaudia, tamburi, klaudiatamburi, 355695315247, 500.00, Enter valid email address."
    })
    @DisplayName("Equivalence Class Testing - Invalid Input")
    public void test3(String username, String password, String name, String surname, String email, String phone, double salary, String expected) {
        Role role = Role.MANAGER;
        Date birthday = new Date();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ac.addEmployee(username, password, name, surname, email, phone, salary, role, birthday);
        });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    @DisplayName("Equivalence Class Testing - Duplicate Username")
    public void test4() {
        String username = "ktamburi";
        String password = "password123";
        String name = "klaudia";
        String surname = "tamburi";
        String email = "klaudiatamburi@gmail.com";
        String phone = "355695315247";
        double salary = 5000.00;
        Role role = Role.MANAGER;
        Date birthday = new Date();

        when(ecMock.employeeFound(username)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ac.addEmployee(username, password, name, surname, email, phone, salary, role, birthday);
        });
        assertEquals("This username is taken. Try something else.", exception.getMessage());
    }
}