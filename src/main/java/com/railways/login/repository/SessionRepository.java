package com.railways.login.repository;

import com.railways.login.entity.Sessions;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Sessions,String> {
}
