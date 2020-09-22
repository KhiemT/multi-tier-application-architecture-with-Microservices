package com.mycompany.app.transaction.exception;

/**
 * Interface for objects which can be translated to rest errors
 */

public interface TranslatableToRestError {
    RestError toRestError();
}
