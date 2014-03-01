package pl.df.owlToWiki.facade.wiki;

import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dominikfilipiak
 * Date: 25/02/2014
 * Time: 14:04
 */
public class WikiWriterImpl implements WikiWriter {

    private String userName;
    private String password;
    private MediaWikiBot mediaWikiBot;
    private List<SimpleArticle> writtenArticles = new LinkedList<>();
    private static Logger LOGGER = Logger.getLogger(WikiWriterImpl.class);

    @Override
    public void writeAll(List<SimpleArticle> articlesToWrite) {
        for (SimpleArticle article : articlesToWrite) {
            LOGGER.info("Writing article " + article.getTitle() + " to wiki...");
            mediaWikiBot.writeContent(article);
            writtenArticles.add(article);
        }
    }

    @Override
    public void connect() {
        LOGGER.info("Connecting to Wikipedia.");
        mediaWikiBot.login(userName, password);
    }


    @Override
    public void setMediaWikiBot(MediaWikiBot mediaWikiBot) {
        this.mediaWikiBot = mediaWikiBot;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public MediaWikiBot getMediaWikiBot() {
        return mediaWikiBot;
    }

    @Override
    public void rollbackCreatedArticles() {
        LOGGER.info("Performing rollback.");
        for (SimpleArticle article : writtenArticles) {
            mediaWikiBot.delete(article.getTitle(), "OWL2WIKI ROLLBACK");
        }
        writtenArticles = new LinkedList<>();
    }
}
