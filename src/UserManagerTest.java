import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @org.junit.jupiter.api.Test
    void userLogin() {
        User testUser = new User("TestUser", "TestPassword");
        UserManager.userList.add(testUser);

        // Mock user input
        System.setIn(new java.io.ByteArrayInputStream("TestUser\nTestPassword\n".getBytes()));

        // Call the method to test
        assertTrue(UserManager.userLogin(), "User login should be successful");

        // Clean up
        System.setIn(System.in);
        UserManager.userList.clear();

    }

    @org.junit.jupiter.api.Test
    void userRegister() {
        // Mock user input
        System.setIn(new java.io.ByteArrayInputStream("TestUser\nTestPassword\n".getBytes()));

        // Call the method to test
        assertTrue(UserManager.userRegister(), "User registration should be successful");

        // Clean up
        System.setIn(System.in);
        UserManager.userList.clear();
    }

    @org.junit.jupiter.api.Test
    void managerLogin() {
        // Mock user input
        System.setIn(new java.io.ByteArrayInputStream("123456\nManager\n".getBytes()));

        // Call the method to test
        assertTrue(UserManager.ManagerLogin(), "Manager login should be successful");

        // Clean up
        System.setIn(System.in);
        UserManager.userList.clear();
    }
}