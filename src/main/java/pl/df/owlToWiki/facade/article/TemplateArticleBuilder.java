package pl.df.owlToWiki.facade.article;

import com.hp.hpl.jena.ontology.OntModel;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 17/03/2014
 * Time: 13:41
 */
public class TemplateArticleBuilder extends ArticlesBuilder {

    private Logger LOGGER = Logger.getLogger(TemplateArticleBuilder.class);

    public List<SimpleArticle> getTemplateArticles(OntModel model) {

        return null;
    }
}
