//package ro.tuc.ds2020.services;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.jdbc.Sql;
//import ro.tuc.ds2020.Ds2020TestConfig;
//import ro.tuc.ds2020.dtos.UserDTO;
//
//import static org.springframework.test.util.AssertionErrors.assertEquals;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/test-sql/create.sql")
//@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/test-sql/delete.sql")
//public class ClientServiceIntegrationTests extends Ds2020TestConfig {
//
//    @Autowired
//    UserService userService;
//
//    @Test
//    public void testGetCorrect() {
//        List<UserDTO> clientDTOList = userService.findAllUsers();
//        assertEquals("Test Insert Person", 1, clientDTOList.size());
//    }
//
//    @Test
//    public void testInsertCorrectWithGetById() {
//        UserDTO u = new UserDTO(null, "johny", "pass123", "John", "NY", new Date(1989 - 1900, Calendar.APRIL, 25));
//        UUID insertedID = userService.insert(u).getId();
//        UserDTO insertedUser = new UserDTO(insertedID, u.getUsername(), u.getPassword(), u.getName(), u.getAddress(), u.getBirthDate());
//        UserDTO fetchedUser = userService.findUserById(insertedID);
//        assertEquals("Test Inserted Person", insertedUser, fetchedUser);
//    }
//
//    @Test
//    public void testInsertCorrectWithGetAll() {
//        UserDTO u = new UserDTO(null, "johny", "pass123", "John", "NY", new Date(1989 - 1900, Calendar.APRIL, 25));
//        userService.insert(u);
//        List<UserDTO> userDTOList = userService.findAllUsers();
//        assertEquals("Test Inserted Users", 2, userDTOList.size());
//    }
//}
