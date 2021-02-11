package com.insea.SpringJwt.Repositories;

import com.insea.SpringJwt.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,String> {
    public Token findByUserId(int id);
}
