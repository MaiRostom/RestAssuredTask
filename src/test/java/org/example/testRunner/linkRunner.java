package org.example.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"C:\\Users\\EGYPT SRORE\\IdeaProjects\\RestAssured\\src\\main\\resources\\Features"},
        glue = {"org.example.stepDefinitions"},
        tags = "@kk",
        plugin = {
                "pretty",
                "html:target/cucumber.html",
                "json:target/cucumber.json",
                "junit:target/cukes.xml",
                "rerun:target/rerun.txt"
        }
)
public class linkRunner extends AbstractTestNGCucumberTests {
}
