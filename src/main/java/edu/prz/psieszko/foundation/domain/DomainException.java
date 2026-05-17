package edu.prz.psieszko.foundation.domain;

public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}