//package ro.tuc.ds2020.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import ro.tuc.ds2020.Ds2020TestConfig;
//import ro.tuc.ds2020.dtos.UserDTO;
//import ro.tuc.ds2020.services.UserService;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//public class ClientControllerUnitTest extends Ds2020TestConfig {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService service;
//
//    @Test
//    public void insertPersonTest() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserDTO userDTO = new UserDTO(null, "johny", "pass123", "John", "NY", new Date(1989 - 1900, Calendar.APRIL, 25));
//
//        mockMvc.perform(post("/user")
//                .content(objectMapper.writeValueAsString(userDTO))
//                .contentType("application/json"))
//                .andExpect(status().isCreated());
//    }
//}
