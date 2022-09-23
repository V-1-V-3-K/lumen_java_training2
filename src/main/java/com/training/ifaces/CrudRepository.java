package com.training.ifaces;

import java.util.*;

import com.training.exception.ElementNotFoundException;

public interface CrudRepository<T> {
	public boolean save(T obj);
	public Collection<T> findAll();
	public boolean delete(String name);
	public boolean update(T obj);
}
