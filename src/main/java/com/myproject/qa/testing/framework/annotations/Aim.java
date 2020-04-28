package com.myproject.qa.testing.framework.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;


@Target({METHOD})
public @interface Aim {
	public String[] value();
}
