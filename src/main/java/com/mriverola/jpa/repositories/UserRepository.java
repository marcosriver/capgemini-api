package com.mriverola.jpa.repositories;

import com.mriverola.jpa.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public interface UserRepository extends CrudRepository<User, Integer> {

}
