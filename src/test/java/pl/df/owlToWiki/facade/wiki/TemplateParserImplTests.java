package pl.df.owlToWiki.facade.wiki;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.df.owlToWiki.facade.utilities.Utilities;

import java.nio.charset.Charset;

/**
 * User: dominikfilipiak
 * Date: 23/03/2014
 * Time: 11:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class TemplateParserImplTests {

    @Autowired
    private MappingParser mappingParser;

    @Test
    public void testParseTemplate() throws Exception {
        mappingParser.parseMapping(Utilities.readFile("/Users/dominikfilipiak/IdeaProjects/owlToWiki/src/main/resources/template", Charset.defaultCharset()));
    }

}
