package com.mac.mysqldb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.mysqldb.TestDataUtil;
import com.mac.mysqldb.dto.BookDto;
import com.mac.mysqldb.entities.BookEntity;
import com.mac.mysqldb.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private BookService bookService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String  bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200ok() throws Exception {
        BookEntity testBookEntitytA = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(
                testBookEntitytA.getIsbn(), testBookEntitytA
        );

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBookEntity.getIsbn());
        String  bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String  bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntitytA = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(
                testBookEntitytA.getIsbn(), testBookEntitytA
        );

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBookEntity.getIsbn());
        bookDto.setTitle("UPDATED");
        String  bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatListBooksReturnHttpStatus200ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnBook() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].isbn").value("111-222-333")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value("Ikigai")
        );
    }

    @Test
    public void testThatGetBooksReturnsHttpStatus200WhenBookExist() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/111-222-333")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBooksReturnsHttpStatus404WhenNoBookExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/999-999-999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBooksReturnsBookWhenBookExist() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/111-222-333")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookEntityA.getTitle())
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200ok() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(testBookEntityA.getIsbn());
        bookDto.setTitle("UPDATED");
        String  bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(testBookEntityA.getIsbn());
        bookDto.setTitle("UPDATED");
        String  bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForNotExistingBook() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/999-999-999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForExistingBook() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
