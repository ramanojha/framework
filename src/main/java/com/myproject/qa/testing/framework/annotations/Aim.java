package com.myproject.qa.testing.framework.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import java.lang.annotation.Target;


@Target({METHOD, CONSTRUCTOR})
public @interface Aim {
	
	/*Annotate the method with description*/
	public String[] value();
}
