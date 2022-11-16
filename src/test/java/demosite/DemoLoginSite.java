package demosite;
import demosite.simulation.PopulationTypes;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class DemoLoginSite extends Simulation {

    private static final String DOMAIN = System.getProperty("DOMAIN", "http://localhost:3000/api");

    private static final String TYPE_OF_TEST = System.getProperty("TYPE_OF_TEST", "INSTANT_USERS");

    private HttpProtocolBuilder httpProtocol =
            http.baseUrl(DOMAIN)
                    .acceptHeader("application/json")
                    .header("cache-control", "no-cache"); // get 302's on repeated calls otherwise.

    {
        switch(TYPE_OF_TEST) {
            case "RAMP_USERS":
                setUp(PopulationTypes.rampUpUsers).protocols(httpProtocol)
                        .assertions(
                                global().responseTime().mean().lt(30),
                                global().successfulRequests().percent().is(100.0),
                                global().responseTime().max().lt(500)
                        );
                break;
            default:
                setUp(PopulationTypes.immediateUsers).protocols(httpProtocol)
                        .assertions(
                                global().responseTime().mean().lt(30),
                                global().successfulRequests().percent().is(100.0),
                                global().responseTime().max().lt(500)
                        );
                break;
        }
    }


}
