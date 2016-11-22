package scary;

import org.junit.Test;
import play.twirl.api.Content;

import static org.fest.assertions.Assertions.assertThat;


public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        Content html = scary.views.html.index.render("Your new application is ready.");
        assertThat(html.contentType()).isEqualTo("text/html");
        assertThat(html.body()).contains("Your new application is ready.");
    }


}
