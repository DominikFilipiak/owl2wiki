package pl.df.owlToWiki.facade;

import pl.df.owlToWiki.facade.owl.OwlLoader;
import pl.df.owlToWiki.facade.wiki.WikiWriter;

/**
 * OwlToWikiFacade is a simple and convenient interface for transforming ontology to Wikipedia
 * User: dominikfilipiak
 * Date: 25/02/2014
 * Time: 00:08
 */
public interface OwlToWikiFacade {

    /**
     * Write ontology content to Wikipedia
     */
    public void performOwlToWikiAction();

    /**
     * Sets OwlLoader class, which is responsible for loading .owl files
     *
     * @param owlLoader OwlLoader
     */
    void setOwlLoader(OwlLoader owlLoader);

    /**
     * Sets WikiWriter, a class responsible for writing articles to wiki
     *
     * @param wikiWriter WikiWriter
     */
    void setWikiWriter(WikiWriter wikiWriter);
}
