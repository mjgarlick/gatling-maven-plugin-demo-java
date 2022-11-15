package demosite.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
// example means of blending together actions, with weighting. Probably completely useless to us.
public class TestScenario {


    public static ScenarioBuilder defaultTest =
        scenario("Default run load test")
                .during(30)
                .on(randomSwitch()
                    .on(
                        Choice.withWeight(50, exec(UserPathway.searchComputers)),
                            Choice.withWeight(50, exec(UserPathway.loginOnSite))
                    )
                );
}
