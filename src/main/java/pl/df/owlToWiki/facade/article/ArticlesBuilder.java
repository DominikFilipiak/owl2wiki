package pl.df.owlToWiki.facade.article;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.RDFNode;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;

/**
 * User: dominikfilipiak
 * Date: 17/03/2014
 * Time: 13:40
 */
public abstract class ArticlesBuilder {

    protected String rootRDFType;

    /**
     * Queries ontology model via SPARQL query
     *
     * @param model       Ontology model
     * @param queryString Query string
     * @return A result set
     */
    protected ResultSet queryModel(OntModel model, String queryString) {
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExe = QueryExecutionFactory.create(query, model);
        return queryExe.execSelect();
    }

    // TODO: Wyciąga coś innego niż SPARQL (więcej). Problem ze zbyt wieloma kategoriami pozostaje.
    protected void addCategoryFooter(SimpleArticle article, RDFNode resource, OntModel model) {
        String queryString;
        ResultSet resultSet;
        queryString = "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "select DISTINCT ?parent\n" +
                "where{\n" +
                "\n" +
                "<" + resource + "> (owl:equivalentClass|(owl:equivalentClass/owl:intersectionOf/rdf:rest*/rdf:first)|rdfs:subClassOf) ?parent.\n" +
                "FILTER (isURI(?parent) && !isBLANK(?parent)).\n" +
                "}";
        resultSet = queryModel(model, queryString);
        while (resultSet.hasNext()) {
            article.addTextnl("[[Category:" + resultSet.next().get("?parent").asResource().getLocalName() + "]]");
        }
    }

    public void setRootRDFType(String rootRDFType) {
        this.rootRDFType = rootRDFType;
    }

}
