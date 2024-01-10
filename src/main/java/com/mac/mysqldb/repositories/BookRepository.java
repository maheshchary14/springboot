package com.mac.mysqldb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.mac.mysqldb.entities.BookEntity;

@Repository
public interface BookRepository extends
        CrudRepository<BookEntity, String>,
        PagingAndSortingRepository<BookEntity, String> {
}
