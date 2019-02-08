package rfp.loyality.cucumber.stepdefs;

import rfp.loyality.RfpApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = RfpApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
