Requirement - 
For any automation project ex. Web Application, API Automation, Database Automation or combination of all.
We need at least need separate 3 projects.
1. Framework - Will have only java building blocks which can be used in child project
2. ex-project - Will have only ex-project related action and verification building blocks made by help of java building block
3. module-project (ex sanity) - Will have the testng suite flows made only for sanity.


#framework

This project will be primary library for child projects.


#my-project

This project will be the main library of the test project. 
This projects pom.xml will have framework project dependency as framework methods are needed to create action and verification methods.

This library will be the parent project for other derived projects.
Example - 
Sanity flows can be maintained in Sanity project, hence Sanity project's pom.xml need to have my-projects dependency. 
Regression flows can be maintained in Regression project, hence Regression project's pom.xml need to have my-projects dependency. 
Data driven flows can be maintained in Data driven project, hence Regression project's pom.xml need to have my-projects dependency. 

This project will have package for
	WebElement locators 				- *.objects
	Basic flow							- *.flows
	Actions and Verification of pages 	- *.pages
	