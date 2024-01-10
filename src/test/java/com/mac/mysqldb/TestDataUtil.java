package com.mac.mysqldb;

import com.mac.mysqldb.dto.AuthorDto;
import com.mac.mysqldb.dto.BookDto;
import com.mac.mysqldb.entities.AuthorEntity;
import com.mac.mysqldb.entities.BookEntity;

public class TestDataUtil {
    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .name("Mahesh")
                .age(21)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .name("Mahesh")
                .age(21)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .name("Kaushik")
                .age(31)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .name("Sai Krishna")
                .age(40)
                .build();
    }

    public static BookEntity createTestBookA(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("111-222-333")
                .title("Ikigai")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                .isbn("111-222-333")
                .title("Ikigai")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("222-333-444")
                .title("Atomic Habits")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("333-444-555")
                .title("Jaden")
                .author(author)
                .build();
    }
}
