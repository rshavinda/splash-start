package com.acentura.splashstart.di.types;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface DialogActionType {
    String OK = "OK";
    String RETRY = "Retry";
    String CONTINUE = "Continue";
    String CANCEL = "Cancel";
    String SELECT = "Select";
    String CONFIRM = "Confirm";

    String OK_CANCEL = "OK_CANCEL";
    String CONTINUE_CANCEL = "CONTINUE_CANCEL";
    String CONFIRM_CANCEL = "CONFIRM_CANCEL";
    String SELECT_CANCEL = "SELECT_CANCEL";
    String RETRY_CANCEL = "RETRY_CANCEL";
    String NO_ACTION = "NO_ACTION";
}
