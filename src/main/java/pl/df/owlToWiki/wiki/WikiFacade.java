package pl.df.owlToWiki.wiki;

import net.sourceforge.jwbf.core.bots.WikiBot;

/**
 * User: dominikfilipiak
 * Date: 25/02/2014
 * Time: 00:08
 */
public interface WikiFacade {
    public void write();
    public void setURL(String URL);
    public void setUserName(String userName);
    public void setPassword(String password);
    public void setWikiBot(WikiBot wikiBot);
}
