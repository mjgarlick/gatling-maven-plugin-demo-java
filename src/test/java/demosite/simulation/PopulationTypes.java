package demosite.simulation;

import io.gatling.javaapi.core.PopulationBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;

public class PopulationTypes {

    public static PopulationBuilder immediateUsers =
            TestScenario.defaultTest
                    .injectOpen( nothingFor(2), atOnceUsers(1));

    public static PopulationBuilder rampUpUsers =
            TestScenario.defaultTest
                    .injectOpen( nothingFor(2), rampUsers(10).during(20));
}
