package com.acentura.splashstart.di.module.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/*Custom scope that acts exactly like @Singleton ,
allowing components scoped with this annotation to be instantiated only once amongst the app.*/
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}
