package oncall;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class OncallDemoTest extends Simulation {

    private static FeederBuilder.FileBased<String> usersFeeder =
            csv("data/users.csv").random();

    private static final String DOMAIN = "";
    private final int PAUSE = 2;

    private static FeederBuilder.FileBased<String> linkFeeder =
            csv("data/menulink.csv").circular();

    private HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://" + DOMAIN)
                    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico"))
                    .acceptEncodingHeader("gzip, deflate")
                    .acceptLanguageHeader("en-GB,en;q=0.9");

    private static ChainBuilder visitHomepage =
            exec(http("Visit homepage")
                    .get("/oncall/") // is this the correct loc?
                    .check(status().is(200))
                    .check(header("location").saveAs("callbackurl")));

    private static ChainBuilder doLogin =
            feed(usersFeeder)
                    .exec(http("Login to site")
                            .post("/oncall.identity/Account/Login")
                            .formParam("username", "#{username}")
                            .formParam("password", "#{password}")
                            .formParam("returnUrl", "#{callbackurl}")
                            .formParam("__RequestVerificationToken", "#{reqVerToken")
                            .check(status().is(200)));

    private static ChainBuilder setLocale =
            exec(http("Setting the locale")
                    .post("/oncall/Account/SetLocale")
                    .formParam("language", "en-gb"));

    private static ChainBuilder visitMenuItem =
            feed(linkFeeder)
                    .exec(http("Visiting #{menuitem}")
                            .get("#{link}")
                            .check(status().is(200)));

    private ScenarioBuilder scn = scenario("Initial tests")
            .exec(visitHomepage)
            .pause(2)
            .exec(doLogin)
            .pause(2);


    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}
