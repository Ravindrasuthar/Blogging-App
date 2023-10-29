package com.ravindra.BloggingApp.Repo;

import com.ravindra.BloggingApp.Model.AuthenticationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepo extends CrudRepository<AuthenticationToken,Integer> {
    AuthenticationToken findFirstByTokenValue(String tokenValue);
}
