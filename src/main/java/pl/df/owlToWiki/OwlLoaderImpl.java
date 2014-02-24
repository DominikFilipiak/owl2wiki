package pl.df.owlToWiki;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.OWL2;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 24/02/2014
 * Time: 12:56
 */
public class OwlLoaderImpl implements OwlLoader {
    private static Logger LOGGER = Logger.getLogger(OwlLoaderImpl.class);
    List<String> inputFiles;

    @Override
    public void loadFiles() {
        OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);

        InfModel infmodel = null;
        Reasoner reasoner = ReasonerRegistry.getTransitiveReasoner();//getOWLReasoner();
        for (String filePath : inputFiles) {
            try {
//                InputStream in = FileManager.get().open(filePath);
                LOGGER.info("Attempting to read " + filePath);
                ontology.read(filePath);
                reasoner = reasoner.bindSchema(ontology);
                infmodel = ModelFactory.createInfModel(reasoner, ontology);
                LOGGER.info("Done.");
            } catch (JenaException je) {
                System.out.println("ERROR" + je.getMessage());
                je.printStackTrace();
            }

        }

        assert infmodel != null;
        Resource algorithm = infmodel.getResource("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#DM-Algorithm");
        Resource rapidMiner = infmodel.getResource("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#A*");
        Property property = RDFS.subClassOf;
        Property definition = infmodel.getProperty("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#definition");
        System.out.println("algorithm :");
        printStatements(infmodel, null, RDF.type, OWL2.NamedIndividual);

    }


    public void printStatements2(Model m, Resource s, Property p, Resource o) {
        for (ResIterator i = m.listResourcesWithProperty(RDFS.subClassOf, o); i.hasNext(); ) {
            Resource stmt = i.nextResource();

            System.out.println(" - " + PrintUtil.print(stmt));
//            System.out.println(" - " + PrintUtil.print(stmt.getProperty(p)));
//            System.out.println(" --- " + PrintUtil.print(stmt.getSubject().asResource().getProperty(p)));
        }
    }

    public void printStatement(Model m, Resource s){
        Property definition = m.getProperty("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#definition");

//        for(StmtIterator i =  s.listProperties(); i.hasNext(); ){
            System.out.println(" --- " + PrintUtil.print(m.getProperty(s, RDF.type)));
            System.out.println(" --- " + PrintUtil.print(m.getProperty(s, definition)));
//        }
    }

    public void printStatements(Model m, Resource s, Property p, Resource o) {
        int j = 0;
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
            j++;
            printStatement(m, stmt.getSubject());
        }
        System.out.println("Found " + j + " statements.");
    }

    @Override
    public void setInputFiles(List<String> inputFiles) {
        this.inputFiles = inputFiles;
    }
}
