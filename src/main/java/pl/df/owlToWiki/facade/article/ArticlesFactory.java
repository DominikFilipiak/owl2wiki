package pl.df.owlToWiki.facade.article;

import com.hp.hpl.jena.ontology.OntModel;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 13/03/2014
 * Time: 21:31
 */
public class ArticlesFactory {

    private static Logger LOGGER = Logger.getLogger(ArticlesFactory.class);
    private CategoryArticleBuilder categoryArticleBuilder;
    private IndividualArticleBuilder individualArticleBuilder;
    private TemplateArticleBuilder templateArticleBuilder;

    private OntModel model;

    public List<SimpleArticle> buildArticles(ArticleType type) {
        switch (type) {
            case ARTICLE:
                return individualArticleBuilder.getIndividualsArticles(model);
            case CATEGORY:
                return categoryArticleBuilder.getCategoryArticles(model);
            case TEMPLATE:
                return templateArticleBuilder.getTemplateArticles(model);
        }
        return null;
    }

    public void setModel(OntModel model) {
        this.model = model;
    }

    public void setCategoryArticleBuilder(CategoryArticleBuilder categoryArticleBuilder) {
        this.categoryArticleBuilder = categoryArticleBuilder;
    }

    public void setIndividualArticleBuilder(IndividualArticleBuilder individualArticleBuilder) {
        this.individualArticleBuilder = individualArticleBuilder;
    }

    public void setTemplateArticleBuilder(TemplateArticleBuilder templateArticleBuilder) {
        this.templateArticleBuilder = templateArticleBuilder;
    }
}
