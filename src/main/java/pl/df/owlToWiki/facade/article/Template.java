package pl.df.owlToWiki.facade.article;

import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import org.apache.log4j.Logger;
import pl.df.owlToWiki.facade.utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * User: dominikfilipiak
 * Date: 23/03/2014
 * Time: 13:31
 */
public class Template {

    private static Logger LOGGER = Logger.getLogger(Template.class);
    private String title;
    private String pathToTemplate;
    private Map<String, String> templateVariables;
    private MappingParser parser;
    private String pathToMapping;

    /**
     * Parses files related to template (original template & mapping)
     */
    public void parse() {
        this.title = new File(pathToTemplate).getName();
        try {
            templateVariables = parser.parseMapping(pathToMapping);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Makes an article form the template
     *
     * @return MediaWiki Article
     */
    public SimpleArticle asArticle() {
        SimpleArticle templateArticle = new SimpleArticle();
        templateArticle.setTitle("Template:" + title);
        try {
            templateArticle.addTextnl(Utilities.readFile(pathToTemplate, Charset.defaultCharset()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return templateArticle;
    }

    public void setParser(MappingParser parser) {
        this.parser = parser;
    }

    public Map<String, String> getTemplateVariables() {
        return templateVariables;
    }

    public String getTitle() {
        return title;
    }

    public void setPathToTemplate(String pathToTemplate) {
        this.pathToTemplate = pathToTemplate;
    }

    public void setPathToMapping(String pathToMapping) {
        this.pathToMapping = pathToMapping;
    }
}
