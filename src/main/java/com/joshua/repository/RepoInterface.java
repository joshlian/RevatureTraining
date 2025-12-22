package com.joshua.repository;

import java.sql.SQLException;
import java.util.List;

public interface RepoInterface <T>{
    //CRUD
    List<T> getAll() throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean deletebyId(Integer id) throws SQLException;
}
