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
public class IndividualArticleBuilder extends AbstractArticleBuilder {
    private Logger LOGGER = Logger.getLogger(IndividualArticleBuilder.class);


    public List<SimpleArticle> getIndividualsArticles(OntModel ontModel, OntModel model) {
        LOGGER.info("Searching for individuals...");
        List<SimpleArticle> articles = new LinkedList<>();
        String queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "select DISTINCT ?individual\n" +
                "where{\n" +
                "?individual a owl:NamedIndividual.\n" +
                "?individual rdf:type ?type.\n" +
                "?type ((owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf)+ <" + rootRDFType + ">.\n" +
                "FILTER (isURI(?individual) && !isBLANK(?individual)).\n" +
                "}";

        ResultSet resultSet = queryModel(ontModel, queryString);

        resultSet.getRowNumber();
        try {
            while (resultSet.hasNext()) {
                QuerySolution next = resultSet.next();
                RDFNode individual = next.get("?individual");
                if (individual != null) {
                    LOGGER.info(individual.toString());
                    try {
                        SimpleArticle simpleArticle = prepareIndividualArticle(ontModel, model, individual);
                        articles.add(simpleArticle);
                    } catch (ArticleBuilderException e) {
                        LOGGER.warn(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.info(e.getStackTrace());
            LOGGER.info(e.getCause());
        }
        return articles;
    }

    private SimpleArticle prepareIndividualArticle(OntModel ontModel, OntModel model, RDFNode resource) throws ArticleBuilderException {
        SimpleArticle article = new SimpleArticle();
        final String title = resource.asResource().getLocalName();
        article.setTitle(title);
        return prepareArticle(ontModel, model, resource, article);
    }

    /**
     * Adds category footer to article.
     *
     * @param article  Article to add footer to
     * @param resource Current resource which article is based on
     * @param model    OWL model ___WITHOUT___ the reasoner
     */
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
                "    (rdf:type)\n" +
                "    ?parent . \n" +
                "   \n" +
                "  }\n" +
                "   FILTER(!(?parent = <" + resource + ">)).\n" +
                "   FILTER(!(?parent = owl:NamedIndividual)).\n" +
                "   FILTER(!(?parent = owl:Individual)).\n" +
                "   FILTER(!(?parent = owl:Thing)).\n" +
                "   FILTER (isURI(?parent) && !isBLANK(?parent)).\n" +
                "}";
        resultSet = queryModel(model, queryString);
        while (resultSet.hasNext()) {
            final String localName = resultSet.next().get("?parent").asResource().getLocalName();
            article.addTextnl("[[Category:" + localName + "]]");
        }
        queryExecution.close();
    }


}
