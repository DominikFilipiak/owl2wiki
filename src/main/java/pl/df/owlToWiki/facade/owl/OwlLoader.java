package pl.df.owlToWiki.facade.owl;

import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import pl.df.owlToWiki.facade.article.ArticlesFactory;

import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 24/02/2014
 * Time: 12:55
 */
public interface OwlLoader {

    public void setInputFiles(List<String> inputFiles);

    /**
     * Gets the collection of all articles fitted to given blueprint
     *
     * @return Articles collection
     */
    public List<SimpleArticle> getArticlesToWrite() throws OntLoaderException;

    void setRootRDFType(String rootRDFType);

    void setArticlesFactory(ArticlesFactory articlesFactory);
}
