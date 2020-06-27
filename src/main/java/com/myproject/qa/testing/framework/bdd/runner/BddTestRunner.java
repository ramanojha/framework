package com.myproject.qa.testing.framework.bdd.runner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;


@CucumberOptions(features = "Features", 
				 glue = { "com/myproject/qa/sanity/bdd/stepdefs/" }, 
				 plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty",
							"json:target/cucumber-reports/CucumberTestReport.json", 
							"rerun:target/cucumber-reports/rerun.txt" 
						  },
				monochrome = true, 
				strict = true,
				dryRun = false,
				snippets = SnippetType.CAMELCASE
				
				)
//tags = {"@Smoke", "@Regression"} // AND Condition
//tags = {"@Smoke, @Regression"}   // OR Condition
//tags = {"~@Smoke, @Regression"}  // OR Condition where @Smoke will be ignored

public class BddTestRunner extends AbstractTestNGCucumberTests{

}
