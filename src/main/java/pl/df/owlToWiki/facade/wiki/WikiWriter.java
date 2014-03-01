package pl.df.owlToWiki.facade.wiki;

import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dominikfilipiak
 * Date: 25/02/2014
 * Time: 14:03
 */
public interface WikiWriter {

    /**
     * Write all articles to connected Wikipedia
     *
     * @param articlesToWrite Articles to write
     */
    void writeAll(List<SimpleArticle> articlesToWrite);

    /**
     * Connects to the Wikipedia
     */
    void connect();

    /**
     * Sets Wikipedia user name
     *
     * @param userName user name
     */
    public void setUserName(String userName);

    /**
     * Sets Wikipedia user password
     *
     * @param password user password
     */
    public void setPassword(String password);

    /**
     * Sets MediaWiki Bot which is responsible for communication with Wikipedia
     *
     * @param mediaWikiBot MediaWiki Bot
     */
    public void setMediaWikiBot(MediaWikiBot mediaWikiBot);

    MediaWikiBot getMediaWikiBot();

    void rollbackCreatedArticles();
}
