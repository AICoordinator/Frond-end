package com.example.frontapp.Communication;

public class TokenRepository {
    private static TokenRepository tokenRepository = null;
    private static String authToken = "";


    public static TokenRepository getInstance()
    {
        if (tokenRepository == null)
            tokenRepository = new TokenRepository();

        return tokenRepository ;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        TokenRepository.authToken = "token "+ authToken;
    }


}