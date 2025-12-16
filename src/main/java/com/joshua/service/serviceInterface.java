package com.joshua.service;

import java.util.List;
import java.util.Optional;

public interface serviceInterface <T, U>{

    Optional<T> getById(Integer id);
    List<T> getAll();
    boolean deletebyId(Integer id);

    Optional<U> convertEntityToModel(T entity);
    Optional<U> getModelById(Integer id);
    List<U> getAllModels();
}
