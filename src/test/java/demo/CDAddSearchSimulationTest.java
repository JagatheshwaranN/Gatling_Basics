package demo;

import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class CDAddSearchSimulationTest extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://computer-database.gatling.io")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("en-US,en;q=0.9")
            .upgradeInsecureRequestsHeader("1")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");

    // Optional Fields
    private final Map<CharSequence, String> headers_0 = Map.ofEntries(
            Map.entry("priority", "u=0, i"),
            Map.entry("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126"),
            Map.entry("sec-ch-ua-mobile", "?0"),
            Map.entry("sec-ch-ua-platform", "Windows"),
            Map.entry("sec-fetch-dest", "document"),
            Map.entry("sec-fetch-mode", "navigate"),
            Map.entry("sec-fetch-site", "none"),
            Map.entry("sec-fetch-user", "?1")
    );

    // Optional Fields
    private final Map<CharSequence, String> headers_1 = Map.ofEntries(
            Map.entry("priority", "u=0, i"),
            Map.entry("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126"),
            Map.entry("sec-ch-ua-mobile", "?0"),
            Map.entry("sec-ch-ua-platform", "Windows"),
            Map.entry("sec-fetch-dest", "document"),
            Map.entry("sec-fetch-mode", "navigate"),
            Map.entry("sec-fetch-site", "same-origin"),
            Map.entry("sec-fetch-user", "?1")
    );

    // Optional Fields
    private final Map<CharSequence, String> headers_2 = Map.ofEntries(
            Map.entry("origin", "https://computer-database.gatling.io"),
            Map.entry("priority", "u=0, i"),
            Map.entry("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126"),
            Map.entry("sec-ch-ua-mobile", "?0"),
            Map.entry("sec-ch-ua-platform", "Windows"),
            Map.entry("sec-fetch-dest", "document"),
            Map.entry("sec-fetch-mode", "navigate"),
            Map.entry("sec-fetch-site", "same-origin"),
            Map.entry("sec-fetch-user", "?1")
    );


    private final ScenarioBuilder scenario = scenario("CDAddSearchSimulationTest")
            .exec(
                    http("Load HomePage")
                            .get("/computers")
                            .headers(headers_0),
                    pause(5),
                    http("Load NewComputer Page")
                            .get("/computers/new")
                            .headers(headers_1),
                    pause(5),
                    http("Create Computer")
                            .post("/computers")
                            .headers(headers_2)
                            .formParam("name", "Smart Assist")
                            .formParam("introduced", "2021-10-01")
                            .formParam("discontinued", "2023-10-25")
                            .formParam("company", "10"),
                    pause(5),
                    http("Search Computer")
                            .get("/computers?f=Smart+Assist")
                            .headers(headers_1)
            );

    {
        setUp(scenario.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
