package dev.kcterala.tinyurl.interceptors;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value=RUNTIME)
public @interface Unauthenticated {
}
