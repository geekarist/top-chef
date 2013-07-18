import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class CrawlerServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String gameConsole = request.getParameter("gameConsole");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://www.jeuxvideo.com/articles/listes/tests-"+ gameConsole + "-type-0-note-0-tri-0-0.htm").openConnection().getInputStream()))) {
            StringBuilder
                    streamContent = new StringBuilder();
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                streamContent.append(currentLine);
            }
            response.getOutputStream().write(toJson(streamContent.toString()).getBytes());
        }
    }

    String toJson(String streamContent) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        Document parsedDoc = Jsoup.parse(streamContent.toString());
        Elements tr1Elements = parsedDoc.getElementsByClass("tr1");
        for (Element element : tr1Elements) {
            appendGame(result, element);
        }
        Elements tr2Elements = parsedDoc.getElementsByClass("tr2");
        for (Element element : tr2Elements) {
            appendGame(result, element);
        }
        result.append("]");
        return result.toString();
    }

    private void appendGame(StringBuilder result, Element tr1Element) {
        if (result.length() > 1) {
            result.append(",");
        }
        Element a = tr1Element.getElementsByTag("a").get(0);
        String name = a.text();
        Elements tds = tr1Element.getElementsByTag("td");
        String tdDate = tds.size() > 3 ? tds.get(3).text() : "no date";
        String tdNote = tds.size() > 5 ? tds.get(5).text() : "0";
        result.append("{\"name\":\"").append(name).append("\",\"note\":")
        .append(tdNote)
        .append(",\"date\":\"")
        .append(tdDate)
        .append("\"}");
    }
}
