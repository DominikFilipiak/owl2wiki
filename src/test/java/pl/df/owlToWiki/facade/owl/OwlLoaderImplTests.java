package pl.df.owlToWiki.facade.owl;

import com.hp.hpl.jena.shared.JenaException;
import junit.framework.Assert;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class OwlLoaderImplTests {
    //    List<String> predicates = new LinkedList<>();
    List<String> inputFiles = new LinkedList<>();
    @Autowired
    private OwlLoader owlLoader;

    @Before
    public void setUp() {
//        owlLoader.setPredicates(predicates);
        inputFiles.add("ancestors.owl");
//        inputFiles.add("/Users/dominikfilipiak/Downloads/DMOP/DMOP.owl");
        owlLoader.setInputFiles(inputFiles);
//        owlLoader.setRootRDFType("http://www.e-lico.eu/ontologies/dmo/DMOP/DMOP.owl#DM-Algorithm");
        owlLoader.setRootRDFType("http://www.workingontologist.org/Examples/Chapter7/Ancestry.owl#Person");
    }

    @Test
    public void testGetArticlesToWrite() throws OntLoaderException {
        setUp();
        final List<SimpleArticle> articlesToWrite = owlLoader.getArticlesToWrite();
        Assert.assertTrue(articlesToWrite.size() == 1);
    }

    @Test(expected = OntLoaderException.class)
    public void badRootCategoryTest() throws OntLoaderException {
        setUp();
        owlLoader.setRootRDFType("this.should.not.work");
        owlLoader.getArticlesToWrite();
    }

    @Test(expected = JenaException.class)
    public void badFilePathTest() throws OntLoaderException {
        setUp();
        final LinkedList<String> badInputFiles = new LinkedList<>();
        badInputFiles.add("/a/path/to/nowhere.owl");
        owlLoader.setInputFiles(badInputFiles);
        owlLoader.getArticlesToWrite();
    }
}
