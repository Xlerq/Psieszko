package edu.prz.psieszko.foundation.domain;

public class NotExistsException extends DomainException {

    protected NotExistsException(String message) {
        super(message);
    }

    public static NotExistsException of(String message) {
        return new NotExistsException(message);
    }
}
