package demosite.simulation;

import io.gatling.javaapi.core.PopulationBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

public class PopulationTypes {

    private static final int USER_COUNT = Integer.parseInt(System.getProperty("USERS", "30"));

    private static final Duration RAMP_DURATION = Duration.ofSeconds(
            Integer.parseInt(System.getProperty("RAMP_DURATION", "15")));

    // slight delay on starting - nothingFor.
    public static PopulationBuilder immediateUsers =
            TestScenario.blendActions
                    .injectOpen( nothingFor(2), atOnceUsers(USER_COUNT));

    public static PopulationBuilder rampUpUsers =
            TestScenario.blendActions
                    .injectOpen( nothingFor(2), rampUsers(USER_COUNT).during(RAMP_DURATION));


}
