package pl.df.owlToWiki.facade.article;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 17/03/2014
 * Time: 13:41
 */
public class CategoryArticleBuilder extends AbstractArticleBuilder {

    private Logger LOGGER = Logger.getLogger(CategoryArticleBuilder.class);

    /**
     * Prepares list of category articles.
     *
     * @param model Ontology model
     */
    public List<SimpleArticle> getCategoryArticles(OntModel model) {
        List<SimpleArticle> articles = new LinkedList<>();
        String queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "select ?subclass\n" +
                "where {\n" +
                " {\n" +
                "  ?subclass ((owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf)+ <" + rootRDFType + ">.\n" +
                " } UNION {\n" +
                " }\n" +
                "}";
        ResultSet resultSet = queryModel(model, queryString);
        while (resultSet.hasNext()) {
            QuerySolution next = resultSet.next();
            RDFNode categoryClass = next.get("?subclass");
            if (categoryClass != null) {
                LOGGER.info(categoryClass.toString());
                try {
                    SimpleArticle simpleArticle = prepareCategoryArticle(model, categoryClass);
                    articles.add(simpleArticle);
                } catch (ArticleBuilderException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return articles;
    }

    /**
     * Prepares single category article
     *
     * @param model    Ontology model
     * @param resource Statement
     * @return Article
     */
    private SimpleArticle prepareCategoryArticle(OntModel model, RDFNode resource) throws ArticleBuilderException {
        SimpleArticle article = new SimpleArticle();
        final String title = resource.asResource().getLocalName();
        article.setTitle("Category:" + title);
        return prepareArticle(model, resource, article);
    }

    protected void addCategoryFooter(SimpleArticle article, RDFNode resource, OntModel model) {
        String queryString;
        ResultSet resultSet;
        queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "select DISTINCT ?parent\n" +
                "\n" +
                "where{\n" +
                "  {    \n" +
                "    <" + resource + ">\n" +
                "    (owl:equivalentClass|(owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf)\n" +
                "    ?parent . \n" +
                "   \n" +
                "  }\n" +
                "   FILTER(!(?parent = <" + resource + ">)).\n" +
                "   FILTER (isURI(?parent) && !isBLANK(?parent)).\n" +
                "}";
        resultSet = queryModel(model, queryString);
        while (resultSet.hasNext()) {
            final String localName = resultSet.next().get("?parent").asResource().getLocalName();
            if (!localName.equals("Resource")) {
                article.addTextnl("[[Category:" + localName + "]]");
            }
        }
        queryExecution.close();
    }

}
