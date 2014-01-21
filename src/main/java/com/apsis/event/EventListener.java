package com.apsis.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The Method-Tag used in scanning a file for Listeners
 *
 * @author Cal Stephens
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
}
