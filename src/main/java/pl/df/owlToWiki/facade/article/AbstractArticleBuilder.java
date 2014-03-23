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
public abstract class AbstractArticleBuilder {

    protected String rootRDFType;
    protected QueryExecution queryExecution;

    /**
     * Queries ontology model via SPARQL query
     *
     * @param model       Ontology model
     * @param queryString Query string
     * @return A result set
     */
    protected ResultSet queryModel(OntModel model, String queryString) {
        Query query = QueryFactory.create(queryString);
        queryExecution = QueryExecutionFactory.create(query, model);
        return queryExecution.execSelect();
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
            article.addTextnl("[[Category:" + localName + "]]");
        }
        queryExecution.close();
    }

    public void setRootRDFType(String rootRDFType) {
        this.rootRDFType = rootRDFType;
    }

}
