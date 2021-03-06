package com.acentura.splashstart.util;

import timber.log.Timber;

public class TimberDebugTree extends Timber.DebugTree {
    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return String.format("(%s:%s)| %s |> ",
                element.getFileName(),
                element.getLineNumber(),
                element.getMethodName());
    }
}
