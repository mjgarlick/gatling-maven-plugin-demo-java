package demosite.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// Completely faux action, driven from a users.csv file. The mongo db contains those users.
public class Login {

    private static FeederBuilder.FileBased<String> usersFeeder =
            csv("demositedata/users.csv").random();

    public static ChainBuilder login =
            feed(usersFeeder)
                    .exec(http("Navigate to login page")
                            .get("/login")
                            .check(status().is(200)))
                    .exec(http("Login to site as #{username}")
                            .post("/login")
                           // .formParam("token", "#{csrfToken}")
                            .formParam("token", "#{token}")
                            .formParam("user", "#{username}")
                            .formParam("pass", "#{password}")
                            .check(status().is(200))
                    ).exec(session -> {
                        session.set("loggedIn", true);
                        return session;
                    });

    public static ChainBuilder logout =
            doIf(session -> session.getBoolean("loggedIn")).then(
                    exec(http("Logout user #{username}")
                            //.post("/logout")
                            .get("/logout")
                            .queryParam("token", "abc123abc")
                            .queryParam("user", "user1")
                            .check(status().is(200)))
                    .exec(session -> session.set("loggedIn", false))
            );

}
