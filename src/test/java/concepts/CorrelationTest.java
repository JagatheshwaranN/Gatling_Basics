package concepts;

import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class CorrelationTest extends Simulation {

  private final HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://computer-database.gatling.io")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
  
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


  private final ScenarioBuilder scenario = scenario("CDAddSearchDeleteSimulationTest")
    .exec(
      http("Load Computers")
        .get("/computers")
        .headers(headers_0),
      pause(5),
      http("New Computer")
        .get("/computers/new")
        .headers(headers_1),
      pause(5),
      http("Add Computer")
        .post("/computers")
        .headers(headers_2)
        .formParam("name", "Ishape")
        .formParam("introduced", "2023-04-12")
        .formParam("discontinued", "2023-10-20")
        .formParam("company", "16"),
      pause(5),
      http("Search Computer")
        .get("/computers?f=ACE")
        .check(regex("computers\\/\\d+").exists().saveAs("computerId"))
        .headers(headers_1),
      pause(5),
      http("Select Computer #{computerId}")
        .get("/#{computerId}")
        .headers(headers_1),
      pause(5),
      http("Delete Computer #{computerId}")
        .post("/#{computerId}/delete")
        .headers(headers_2)
    );

  {
	  setUp(scenario.injectOpen(atOnceUsers(5))).protocols(httpProtocol);
  }

}
