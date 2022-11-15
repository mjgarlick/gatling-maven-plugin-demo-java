package demosite.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
// example means of blending together actions, with weighting. Probably completely useless to us.
public class TestScenario {

    public static Duration TEST_DURATION = Duration.ofSeconds(
            Integer.parseInt(System.getProperty("DURATION", "30")));

    public static ScenarioBuilder blendActions =
        scenario("Default test of 50:50 behaviour browseSearch/browseLoginSearch")
                .during(TEST_DURATION)
                .on(randomSwitch()
                    .on(
                        Choice.withWeight(50, exec(UserPathway.browseLoginSearchAdd)),
                            Choice.withWeight(50, exec(UserPathway.browseAndSearch))
                    )
                );

    public static ScenarioBuilder browseAndSearch =
            scenario("Browse and search only")
                    .during(TEST_DURATION)
                    .on(exec(UserPathway.browseAndSearch));

    public static ScenarioBuilder browseLoginSearchAdd =
            scenario("Browse, login, search and add books")
                    .during(TEST_DURATION)
                    .on(exec(UserPathway.browseLoginSearchAdd));
}
