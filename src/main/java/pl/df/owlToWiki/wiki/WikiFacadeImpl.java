package pl.df.owlToWiki.wiki;

import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

/**
 * User: dominikfilipiak
 * Date: 25/02/2014
 * Time: 00:09
 */
public class WikiFacadeImpl implements WikiFacade{
    private String userName;
    private String password;
    private MediaWikiBot mediaWikiBot;

    @Override
    public void write() {
        connect();
        SimpleArticle article = new SimpleArticle("TEST TEST TEST");
        article.addText("IL Y AURA UNE GRANDE WIKIPEDIA");
        mediaWikiBot.writeContent(article);

    }

    private void connect() {
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
}
