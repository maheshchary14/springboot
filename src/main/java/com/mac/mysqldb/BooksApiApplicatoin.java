package com.mac.mysqldb;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class BooksApiApplicatoin {

	private final DataSource dataSource;
	
	public BooksApiApplicatoin(DataSource dataSource){ this.dataSource = dataSource; }
	public static void main(String[] args) {
		SpringApplication.run(BooksApiApplicatoin.class, args);
	}

}
