package com.mac.mysqldb.mappers;

public interface Mapper<A,B> {

    B mapTo(A a);

    A mapFrom(B b);
}
