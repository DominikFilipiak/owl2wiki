package pl.df.owlToWiki.wiki;

import net.sourceforge.jwbf.core.bots.WikiBot;

/**
 * User: dominikfilipiak
 * Date: 25/02/2014
 * Time: 00:09
 */
public class WikiFacadeImpl implements WikiFacade{
    private String URL;
    private String userName;
    private String password;
    private WikiBot wikiBot;


    @Override
    public void setWikiBot(WikiBot wikiBot) {
        this.wikiBot = wikiBot;
    }

    @Override
    public void write() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setURL(String URL) {
        this.URL = URL;
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
