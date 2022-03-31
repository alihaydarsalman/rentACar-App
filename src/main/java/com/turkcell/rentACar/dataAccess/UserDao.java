package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    boolean existsUserByEmail(String email);
    boolean existsByUserId(int userId);
}
