package pl.df.owlToWiki.facade.article;

import com.hp.hpl.jena.ontology.OntModel;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;

import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 13/03/2014
 * Time: 21:31
 */
public class ArticlesFactory {

    private CategoryArticleBuilder categoryArticleBuilder;
    private IndividualArticleBuilder individualArticleBuilder;
    private TemplateArticleBuilder templateArticleBuilder;

    private OntModel ontModel;
    private OntModel model;

    /**
     * Builds all articles for given type
     *
     * @param type Type of articles
     * @return List of articles
     */
    public List<SimpleArticle> buildArticles(ArticleType type) {
        switch (type) {
            case ARTICLE:
                return individualArticleBuilder.getIndividualsArticles(ontModel, model);
            case CATEGORY:
                return categoryArticleBuilder.getCategoryArticles(ontModel);
            case TEMPLATE:
                return templateArticleBuilder.getTemplateArticles();
        }
        return null;
    }

    public void setOntModel(OntModel ontModel) {
        this.ontModel = ontModel;
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
