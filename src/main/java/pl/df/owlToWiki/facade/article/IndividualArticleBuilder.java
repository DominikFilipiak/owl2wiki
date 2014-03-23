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
public class IndividualArticleBuilder extends AbstractArticleBuilder {
    private Logger LOGGER = Logger.getLogger(IndividualArticleBuilder.class);

    public List<SimpleArticle> getIndividualsArticles(OntModel model) {
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
        addCategoryFooter(article, individual, model);
        return article;

    }


}
