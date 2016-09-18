import org.junit.Test;
import play.mvc.Result;
import play.test.FakeRequest;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
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
            FakeRequest request = fakeRequest("GET", "/freemarker");
            Result result = route(request);
            assertThat(contentAsString(result)).isEqualTo("<table>\n" +
                    "    <thead><tr><th>user</th><th>product</th></tr></thead>\n" +
                    "    <tbody><tr><td>user</td><td>product</td></tr></tbody>\n" +
                    "</table>");
        });
    }

}
