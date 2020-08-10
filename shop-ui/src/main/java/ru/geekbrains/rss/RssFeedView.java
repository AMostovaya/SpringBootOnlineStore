package ru.geekbrains.rss;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class RssFeedView extends AbstractRssFeedView {

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Item itemEntry = new Item();
        itemEntry.setTitle("JUnit 5 @Test Annotation");
        itemEntry.setAuthor("[email protected]");
        itemEntry.setLink("http://localhost:8092/store");
        itemEntry.setPubDate(Date.from(Instant.parse("2020-07-31T00:00:00Z")));
        return Arrays.asList(itemEntry);
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle("Baeldung RSS Feed");
        feed.setDescription("Learn how to program in Java");
        feed.setLink("http://localhost:8092");
    }
}
