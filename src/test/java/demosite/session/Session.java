package demosite.session;


import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// Arbitrary in current example. But enables to clear/setup per user etc.
public class Session {
    public static ChainBuilder initSession =
            exec(flushCookieJar())
                    .exec(session -> session.set("loggedIn", false));
}
