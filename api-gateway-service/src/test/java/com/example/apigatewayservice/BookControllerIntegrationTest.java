package com.example.apigatewayservice;
import com.example.apigatewayservice.domainclientlayer.BookServiceClient;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private BookServiceClient bookServiceClient;

    @Test
    void getAllBooks_ReturnsOkWithBooksList() throws Exception {
        BookResponseModel mockBook = BookResponseModel.builder()
                .bookId("book123")
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(42.99)
                .publishedDate("2008-08-01")
                .build();

        when(bookServiceClient.getAllBooks()).thenReturn(List.of(mockBook));

        mockMvc.perform(get("/api/v1/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value("book123"))
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"))
                .andExpect(jsonPath("$[0].isbn").value("9780132350884"))
                .andExpect(jsonPath("$[0].price").value(42.99))
                .andExpect(jsonPath("$[0].publishedDate").value("2008-08-01"));
    }
}
