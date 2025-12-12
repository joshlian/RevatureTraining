package com.joshua.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RepoInterface <T>{
    //CRUD
    //void create(T entity) throws SQLException;

    List<T> findAll() throws SQLException;

    Optional<T> findById(Integer id) throws SQLException;

    //void update(int id, String newName) throws SQLException;

    boolean delete(Integer id) throws SQLException;
}
