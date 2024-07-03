package concepts;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class FeedersWithRequestTest extends Simulation {

    private final HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("https://reqres.in/api");

    private final FeederBuilder.Batchable<String> csvFeeder = csv("data/user.csv").circular();

    private final ScenarioBuilder feederWithRepeat = scenario("Feeders Demo")
            .repeat(3).on(
                    feed(csvFeeder)
                            .exec(
                                    http("POST API Demo")
                                            .post("/users")
                                            .body(StringBody("{\n" +
                                                    "  \"name\": \"#{name}\",\n" +
                                                    "  \"job\": \"#{designation}\"\n" +
                                                    "}"))
                                            .check(status().is(201))
                            ));

    {

        setUp(feederWithRepeat.injectOpen(atOnceUsers(1))).protocols(httpProtocolBuilder);
    }

}

