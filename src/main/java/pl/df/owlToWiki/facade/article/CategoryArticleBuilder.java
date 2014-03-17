package pl.df.owlToWiki.facade.article;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
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
public class CategoryArticleBuilder extends ArticlesBuilder {

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
                "}";          // TODO: sprawdzić, czy potrzebne będzie samo equivalentclass

        ResultSet resultSet = queryModel(model, queryString);

        while (resultSet.hasNext()) {
            QuerySolution next = resultSet.next();
            RDFNode categoryClass = next.get("?subclass");
            if (categoryClass != null) {
                LOGGER.info(categoryClass.toString());
                SimpleArticle simpleArticle = prepareCategoryArticle(model, categoryClass);
                articles.add(simpleArticle);
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
    private SimpleArticle prepareCategoryArticle(OntModel model, RDFNode resource) {
        final String title = resource.asResource().getLocalName();
        SimpleArticle article = new SimpleArticle();
        article.setTitle("Category:" + title);
        Property definition = model.getProperty("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#definition");
        String queryString =
                "select ?definition\n" +
                        "where{\n" +
                        " <" + resource.toString() + "> <" + definition + "> ?definition\n" +
                        "}";
        ResultSet resultSet = queryModel(model, queryString);
        if (resultSet.hasNext()) {
            article.addTextnl(resultSet.next().get("?definition").toString());
        }

        // TODO: bad query -> SET IT TO RDF NODE
//        queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
//                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
//                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "select DISTINCT ?parent\n" +
//                "where{\n" +
//                "\n" +
//                "<" + resource + "> (owl:equivalentClass|(owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf) ?parent.\n" +
//                "FILTER (isURI(?parent) && !isBLANK(?parent)).\n" +
//                "}";
//        resultSet = queryModel(model, queryString);
//        while (resultSet.hasNext()) {
//            article.addTextnl("[[Category:" + resultSet.next().get("?parent").asResource().getLocalName() + "]]");
//        }
        addCategoryFooter(article, resource, model);
//        model.getProperty(resource, definiotion);
//        article.addTextnl();

//        for (String predicate : predicates) {
        // TODO:
//            final Property property = model.getProperty(predicate);
//            final RDFNode propertyResourceValue = resource.getPropertyValue(property);
//            article.addText(propertyResourceValue.asLiteral().getString());
//        }
        return article;
    }
}
