package pl.df.owlToWiki.facade.wiki;

import org.junit.Before;
import org.junit.Test;
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
public class WikiWriterImplTests {
    @Autowired
    private WikiWriterImpl wikiWriter;

    @Before
    public void setUp() {

    }

    @Test
    public void testWriteAll() throws Exception {

    }

    @Test
    public void testConnect() throws Exception {

    }
}
