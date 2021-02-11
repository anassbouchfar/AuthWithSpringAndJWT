package com.insea.SpringJwt.models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;
@Entity
public class Token {
    @Id
    private String token;
    private int userId;

    public Token() {
    }

    public Token(String token,int userId) {
        this.token = token;
        this.userId=userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String generateToken() {
        String uuid = UUID.randomUUID().toString();
        uuid= uuid.replace("-", "");
        return  uuid;
    }
}
