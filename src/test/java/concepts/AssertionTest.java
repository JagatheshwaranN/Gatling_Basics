package concepts;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class AssertionTest extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://computer-database.gatling.io")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("en-US,en;q=0.9")
            .upgradeInsecureRequestsHeader("1")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");


    ChainBuilder loadNewComputer = exec(
            http("Load NewComputer Page")
                    .get("/computers/new"),
            pause(5)
    );

    ChainBuilder createComputer = exec(
            http("Create Computer")
                    .post("/computers")
                    .formParam("name", "Smart Assist")
                    .formParam("introduced", "2021-10-01")
                    .formParam("discontinued", "2023-10-25")
                    .formParam("company", "10"),
            pause(5)
    );

    ChainBuilder searchComputer = exec(
            http("Search Computer")
                    .get("/computers?f=Smart+Assist")
    );

    ScenarioBuilder adminUser = scenario("AdminSimulationTest")
            .exec(loadNewComputer, createComputer, searchComputer);
    ScenarioBuilder normalUser = scenario("UserSimulationTest")
            .exec(loadNewComputer, createComputer);

    {
        setUp(
                adminUser.injectOpen(
                        nothingFor(5),
                        atOnceUsers(5),
                        rampUsers(10).during(2),
                        rampUsersPerSec(1).to(15).during(5),
                        constantUsersPerSec(10).during(10)
                ),
                normalUser.injectOpen(rampUsers(10).during(10))
        ).protocols(httpProtocol)
                .assertions(global().responseTime().max().lt(200),
                        global().successfulRequests().percent().gt(95.0),
                        global().responseTime().percentile(99.0).lt(200),
                        global().failedRequests().count().lt(10L)
                );
    }

}
