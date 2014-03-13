package pl.df.owlToWiki.facade.owl;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 13/03/2014
 * Time: 21:31
 */
public class ArticlesFactory {

    static Logger LOGGER = Logger.getLogger(ArticlesFactory.class);
    private String rootRDFType;
    private OntModel model;

    public List<SimpleArticle> buildArticles(ArticleType type) {
        switch (type) {
            case ARTICLE:
                return getIndividualsArticles(model);
            case CATEGORY:
                return getCategoryArticles(model);
        }
        return null;
    }

    /**
     * Prepares list of category articles.
     *
     * @param model Ontology model
     */
    private List<SimpleArticle> getCategoryArticles(OntModel model) {
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
     * Queries ontology model via SPARQL query
     *
     * @param model       Ontology model
     * @param queryString Query string
     * @return A result set
     */
    private ResultSet queryModel(OntModel model, String queryString) {
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExe = QueryExecutionFactory.create(query, model);
        return queryExe.execSelect();
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
        queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "select DISTINCT ?parent\n" +
                "where{\n" +
                "\n" +
                "<" + resource + "> (owl:equivalentClass|(owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf)+ ?parent.\n" +
                "FILTER (isURI(?parent) && !isBLANK(?parent)).\n" +
                "}";
        resultSet = queryModel(model, queryString);
        while (resultSet.hasNext()) {
            article.addTextnl("[[Category:" + resultSet.next().get("?parent").asResource().getLocalName() + "]]");
        }

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

    private List<SimpleArticle> getIndividualsArticles(OntModel model) {
        LOGGER.info("Searching for individuals...");
        List<SimpleArticle> articles = new LinkedList<>();
        String queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "select DISTINCT ?individual\n" +
                "where{\n" +
                "?individual a owl:NamedIndividual.\n" +
                "?individual rdf:type ?type.\n" +
                "?type ((owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf)* <" + rootRDFType + ">.\n" +
                "FILTER (isURI(?individual) && !isBLANK(?individual)).\n" +
                "}";

        ResultSet resultSet = queryModel(model, queryString);

        while (resultSet.hasNext()) {
            QuerySolution next = resultSet.next();
            RDFNode individual = next.get("?individual");
            if (individual != null) {
                LOGGER.info(individual.toString());
                SimpleArticle simpleArticle = prepareIndividualArticle(model, individual);
                articles.add(simpleArticle);
            }
        }
        return articles;
    }

    private SimpleArticle prepareIndividualArticle(OntModel model, RDFNode individual) {
        final String title = individual.asResource().getLocalName();
        SimpleArticle article = new SimpleArticle();
        article.setTitle(title);
        Property definition = model.getProperty("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#definition");
        String queryString =
                "select ?definition\n" +
                        "where{\n" +
                        " <" + individual.toString() + "> <" + definition + "> ?definition\n" +
                        "}";
        ResultSet resultSet = queryModel(model, queryString);
        if (resultSet.hasNext()) {
            article.addTextnl(resultSet.next().get("?definition").toString());
        }

        // TODO: bad query -> SET IT TO RDF NODE
        queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "select DISTINCT ?parent\n" +
                "where{\n" +
                "\n" +
                "<" + individual + "> (owl:equivalentClass|(owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf)+ ?parent.\n" +
                "FILTER (isURI(?parent) && !isBLANK(?parent)).\n" +
                "}";
        resultSet = queryModel(model, queryString);
        while (resultSet.hasNext()) {
            article.addTextnl("[[Category:" + resultSet.next().get("?parent").asResource().getLocalName() + "]]");
        }
        return article;

    }

    public void setRootRDFType(String rootRDFType) {
        this.rootRDFType = rootRDFType;
    }

    public void setModel(OntModel model) {
        this.model = model;
    }
}
