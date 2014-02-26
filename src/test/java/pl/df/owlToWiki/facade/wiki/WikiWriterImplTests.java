package pl.df.owlToWiki.facade.wiki;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: dominikfilipiak
 * Date: 26/02/2014
 * Time: 21:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class WikiWriterImplTests extends TestCase {
    @Autowired
    private WikiWriterImpl wikiWriter;

    @Before
    public void setUp() {

    }

    public void testWriteAll() throws Exception {

    }

    public void testConnect() throws Exception {

    }
}
