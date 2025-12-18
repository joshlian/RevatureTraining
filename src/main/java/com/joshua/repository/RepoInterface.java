package com.joshua.repository;

import java.sql.SQLException;
import java.util.List;

public interface RepoInterface <T>{
    //CRUD
    List<T> findAll() throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean delete(Integer id) throws SQLException;
}
