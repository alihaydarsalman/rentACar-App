package com.turkcell.rentACar.core.utilities.results;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ErrorResult extends Result {

    public ErrorResult(String message) {
        super(false, message);

    }

    public ErrorResult() {
        super(false);
    }

}


