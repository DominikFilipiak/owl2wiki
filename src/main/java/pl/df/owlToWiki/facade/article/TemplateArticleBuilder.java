package pl.df.owlToWiki.facade.article;

import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 17/03/2014
 * Time: 13:41
 */
public class TemplateArticleBuilder extends AbstractArticleBuilder {

    private Logger LOGGER = Logger.getLogger(TemplateArticleBuilder.class);

    public List<SimpleArticle> getTemplateArticles() {
        LOGGER.info("Preparing template");
        final ArrayList<SimpleArticle> templates = new ArrayList<>();
        templates.add(template.asArticle());
        return templates;
    }

}
