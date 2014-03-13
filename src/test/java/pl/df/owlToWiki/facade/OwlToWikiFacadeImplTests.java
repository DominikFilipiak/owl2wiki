package pl.df.owlToWiki.facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.df.owlToWiki.facade.owl.OwlLoader;
import pl.df.owlToWiki.facade.wiki.WikiWriterImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 01/03/2014
 * Time: 12:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class OwlToWikiFacadeImplTests {

    @Autowired
    private OwlToWikiFacadeImpl owlToWikiFacade;
    @Autowired
    private WikiWriterImpl wikiWriter;
    @Autowired
    private OwlLoader owlLoader;

    List<String> predicates = new LinkedList<>();
    List<String> inputFiles = new LinkedList<>();


    @Before
    public void setUp() throws Exception {
        owlLoader.setPredicates(predicates);
        inputFiles.add("/Users/dominikfilipiak/Downloads/DMOP/DMOP.owl");
//        inputFiles.add("ancestors.owl");
        owlLoader.setRootRDFType("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#DM-Algorithm");
//        owlLoader.setRootRDFType("http://www.workingontologist.org/Examples/Chapter7/Ancestry.owl#Person");
        owlLoader.setInputFiles(inputFiles);

    }

    @After
    public void tearDown() throws Exception {
        wikiWriter.rollbackCreatedArticles();
    }

    @Test
    public void testPerformOwlToWikiAction() throws Exception {
        owlToWikiFacade.performOwlToWikiAction();
        Assert.assertNotNull(wikiWriter.getMediaWikiBot().getArticle("Category:Person"));
        Assert.assertNotNull(wikiWriter.getMediaWikiBot().getArticle("WillemAlexander"));
    }
}
