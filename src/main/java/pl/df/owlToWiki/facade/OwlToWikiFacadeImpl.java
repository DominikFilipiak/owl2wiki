package pl.df.owlToWiki.facade;

import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;
import pl.df.owlToWiki.facade.owl.OntLoaderException;
import pl.df.owlToWiki.facade.owl.OwlLoader;
import pl.df.owlToWiki.facade.wiki.WikiWriter;

import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 25/02/2014
 * Time: 00:09
 */
public class OwlToWikiFacadeImpl implements OwlToWikiFacade {

    private OwlLoader owlLoader;
    private WikiWriter wikiWriter;
    private static Logger LOGGER = Logger.getLogger(OwlToWikiFacadeImpl.class);

    @Override
    public void performOwlToWikiAction() {
        LOGGER.info("Performing owl to wiki action...");
        final List<SimpleArticle> articlesToWrite;
        try {
            articlesToWrite = owlLoader.getArticlesToWrite();
            wikiWriter.connect();
            wikiWriter.writeAll(articlesToWrite);
            LOGGER.info("Done with owl to wiki action.");
        } catch (OntLoaderException e) {
            e.printStackTrace();
        }
        LOGGER.info("Finished.");
    }

    @Override
    public void setOwlLoader(OwlLoader owlLoader) {
        this.owlLoader = owlLoader;
    }

    @Override
    public void setWikiWriter(WikiWriter wikiWriter) {
        this.wikiWriter = wikiWriter;
    }
}
