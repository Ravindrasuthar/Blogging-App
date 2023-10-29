package com.ravindra.BloggingApp.Service;

import com.ravindra.BloggingApp.Model.AuthenticationToken;
import com.ravindra.BloggingApp.Repo.AuthenticationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {

    @Autowired
    AuthenticationTokenRepo authenticationTokenRepo;

    public void createToken(AuthenticationToken token) {
        authenticationTokenRepo.save(token);
    }

    public boolean authenticate(String email, String tokenValue)
    {
        AuthenticationToken token = authenticationTokenRepo.findFirstByTokenValue(tokenValue);
        if(token != null)
        {
            return token.getUser().getUserEmail().equals(email);
        }
        else
        {
            return false;
        }
    }

    public void deleteToken(String tokenValue) {
        AuthenticationToken token = authenticationTokenRepo.findFirstByTokenValue(tokenValue);
        authenticationTokenRepo.delete(token);
    }
}
