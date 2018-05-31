package logic;

import dto.Article;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupWorks {
    private static final Logger logger = Logger.getLogger(JsoupWorks.class);

    public List<Article> getAllUrls(String url, String urlMain) throws IOException {
        logger.info("getAllUrls()");
        return getLinks(url, urlMain);
    }

    private List<Article> getLinks(final String url, final String urlMain) throws IOException{
        logger.info("getLinks() start");
        List<Article> list = new ArrayList<Article>();
        Long timeReq = System.currentTimeMillis();
        Document doc = Jsoup.connect(url).get();
        logger.info("TimeReqest(getLinks()): "
                + (((float)(System.currentTimeMillis() - timeReq)) / 1000) + " с.");
        Elements links = doc.getElementsByAttributeValue("class", "c-events__item c-events__item_col");
        logger.info("Size links: " + links.size());
        links.forEach(lin -> {
            Elements divElement = lin.getElementsByAttributeValue("class", "c-events__item");
            divElement.forEach(divEl -> {
                Element aElement = divEl.child(4);
                String urlAElement = aElement.attr("href");
                String title = aElement.child(0).text();
                list.add(new Article(urlMain + urlAElement, title));
            });
        });
        return list;
    }

    public  List<Document> getDocFromStr(List<String> list){
        logger.info("getDocFromStr() start");
        List<Document> listDoc = new ArrayList<>();
        list.forEach(li->{
           Document doc =  Jsoup.parse(li);
           listDoc.add(doc);
        });
        return listDoc;
    }
}