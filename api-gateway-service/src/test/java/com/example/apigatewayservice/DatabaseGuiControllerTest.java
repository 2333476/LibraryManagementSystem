//package com.example.apigatewayservice;
//import com.example.apigatewayservice.presentationlayer.DatabaseGuiController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.client.RestTemplate;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = DatabaseGuiController.class)
//public class DatabaseGuiControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @SuppressWarnings("removal")
//    @SpyBean
//    private RestTemplate restTemplate; // To avoid NoSuchBeanDefinitionException
//
//    @Test
//    void testPhpMyAdminCustomersProxyReturnsNotFound() throws Exception {
//        mockMvc.perform(get("/db/phpmyadmin-customers/"))
//                .andExpect(status().is4xxClientError()); // since container won't return valid HTML
//    }
//
//    @Test
//    void testPhpMyAdminEmployeesProxyReturnsNotFound() throws Exception {
//        mockMvc.perform(get("/db/phpmyadmin-employees/"))
//                .andExpect(status().is4xxClientError());
//    }
//
//    @Test
//    void testPgAdminBookProxyReturnsNotFound() throws Exception {
//        mockMvc.perform(get("/db/pgadmin-book/"))
//                .andExpect(status().is4xxClientError());
//    }
//
//    @Test
//    void testMongoExpressProxyReturnsNotFound() throws Exception {
//        mockMvc.perform(get("/db/mongo-express/"))
//                .andExpect(status().is4xxClientError());
//    }
//}
