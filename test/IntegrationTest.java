import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class IntegrationTest {

    @Test
    public void browserTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertThat(browser.pageSource()).contains("Your new application is ready.");
        });
    }

    @Test
    public void httpTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            Http.RequestBuilder request = fakeRequest("GET", "/freemarker");
            Result result = route(request);
            assertThat(contentAsString(result)).isEqualTo(StringUtils.join(asList("<table>",
                    "    <thead><tr><th>user</th><th>product</th></tr></thead>",
                    "    <tbody><tr><td>user</td><td>product</td></tr></tbody>",
                    "</table>"), System.getProperty("line.separator")));
        });
    }

}
