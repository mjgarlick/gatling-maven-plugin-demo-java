package demosite;
import demosite.simulation.UserPathway;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class DemoLoginSite extends Simulation {

    private static final String DOMAIN = "http://localhost:3000/api";

    private HttpProtocolBuilder httpProtocol =
            http.baseUrl(DOMAIN).acceptHeader("application/json");

    private ScenarioBuilder demoLoginScn = scenario("Login and browse the demo site.")
            .exec(UserPathway.loginOnSite);

    private ScenarioBuilder demoSearchScn = scenario("Searching for things")
            .exec(UserPathway.searchComputers);

    {
        setUp(
                demoLoginScn.injectOpen(atOnceUsers(1)),
                demoSearchScn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}
