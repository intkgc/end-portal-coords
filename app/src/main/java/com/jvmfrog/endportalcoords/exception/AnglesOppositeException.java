package com.jvmfrog.endportalcoords.exception;

public class AnglesOppositeException extends Exception {
    public AnglesOppositeException() {
        super("Angles cannot be opposite");
    }
}
