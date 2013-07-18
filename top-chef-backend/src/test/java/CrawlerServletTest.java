import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

public class CrawlerServletTest {

    @Test
    @Ignore
    public void should() throws Exception {
        String file360 = FileUtils.readFileToString(new File(this.getClass().getClassLoader().getResource("360.html").toURI()));
        assertThat(new CrawlerServlet().toJson(file360)).isEqualTo("[{\"name\":\"'Splosion Man\"}]");
    }
}
