package com.joshua.service;

import java.util.List;
import java.util.Optional;

public interface serviceInterface <T, U>{

    List<T> getAll();
    boolean deletebyId(Integer id);

    Optional<U> convertEntityToModel(T entity);
    List<U> getAllModels();
}
