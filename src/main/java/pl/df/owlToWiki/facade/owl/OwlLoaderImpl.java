package pl.df.owlToWiki.facade.owl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;
import pl.df.owlToWiki.facade.article.ArticleType;
import pl.df.owlToWiki.facade.article.ArticlesFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 24/02/2014
 * Time: 12:56
 */
public class OwlLoaderImpl implements OwlLoader {

    private static Logger LOGGER = Logger.getLogger(OwlLoaderImpl.class);
    private ArticlesFactory articlesFactory;
    private List<String> inputFiles;
    private String rootRDFType;

    @Override
    public List<SimpleArticle> getArticlesToWrite() throws OntLoaderException {
        OntModel model = loadFilesAsModel();
        OntModel ontModel = loadFilesAsOntologyModel();
        checkRootClass(ontModel);
        articlesFactory.setModel(model);
        articlesFactory.setOntModel(ontModel);
        List<SimpleArticle> articles = new LinkedList<>();
        // You might be surprised but it's great deal faster than forEach (in alphabetical order). Don't know why, yet.
        List<SimpleArticle> templateArticles = articlesFactory.buildArticles(ArticleType.TEMPLATE);
        List<SimpleArticle> categoryArticles = articlesFactory.buildArticles(ArticleType.CATEGORY);
        List<SimpleArticle> individualsArticles = articlesFactory.buildArticles(ArticleType.ARTICLE);
        articles.addAll(templateArticles);
        articles.addAll(categoryArticles);
        articles.addAll(individualsArticles);
        return articles;
    }

    /**
     * Checks root ontology class. If class does not exist, OntLoaderException will be thrown.
     *
     * @param model Ontology model
     * @throws OntLoaderException
     */
    private void checkRootClass(OntModel model) throws OntLoaderException {
        OntClass ontClass = model.getOntClass(this.rootRDFType);
        if (ontClass == null) {
            throw new OntLoaderException("Can't find root RDF class. Check your config file.");
        }
    }

    /**
     * Loads all files from properties as a model with a reasoner
     *
     * @return Ontology model
     */
    private OntModel loadFilesAsOntologyModel() {
        OntModel ontModel = ModelFactory.createOntologyModel();
        loadFilesToModel(ontModel);
        LOGGER.debug("Done with loading files.");
        LOGGER.debug("Setting up the reasoner.");
        Reasoner reasoner = ReasonerRegistry.getTransitiveReasoner();
        reasoner = reasoner.bindSchema(ontModel);
        LOGGER.debug("Done with setting up the reasoner.");
        OntModelSpec ontModelSpec = OntModelSpec.OWL_LITE_MEM_TRANS_INF;
        ontModelSpec.setReasoner(reasoner);
        OntModel model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
        LOGGER.debug("Ontology model created.");
        return model;
    }

    /**
     * Loads all files from properties as a model without a reasoner
     *
     * @return Ontology model
     */
    private OntModel loadFilesAsModel() {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        loadFilesToModel(model);
        LOGGER.debug("Done with loading files.");
        LOGGER.debug("Model created.");
        return model;
    }

    private void loadFilesToModel(Model model) {
        for (String filePath : inputFiles) {
            LOGGER.info("Attempting to read " + filePath);
            loadFile(filePath, model);
        }
    }

    /**
     * Loads single file
     *
     * @param filePath Absolute path to file
     * @param ontology Ontology to append mentioned file to
     */
    private void loadFile(String filePath, Model ontology) {
        ontology.read(filePath);
    }

    @Override
    public void setInputFiles(List<String> inputFiles) {
        this.inputFiles = inputFiles;
    }

    @Override
    public void setRootRDFType(String rootRDFType) {
        this.rootRDFType = rootRDFType;
    }

    @Override
    public void setArticlesFactory(ArticlesFactory articlesFactory) {
        this.articlesFactory = articlesFactory;
    }

}
