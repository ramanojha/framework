package com.myproject.qa.testing.framework.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target({METHOD})
public @interface StepAim {
	/*Annotate the method with description*/
	public String[] value() default "";;
	
	/*Annotate the method with step and expected*/
	public String step() default "";
	public String expected() default "";
		
}	
