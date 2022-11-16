package com.bsi.passwordwalleet3.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserLoginException extends RuntimeException{

    public UserLoginException(String message) {
        super(message);
    }

}
