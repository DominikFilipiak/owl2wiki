package pl.df.owlToWiki.facade.article;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: dominikfilipiak
 * Date: 23/03/2014
 * Time: 11:46
 */
public class MappingParserImpl implements MappingParser {

    @Override
    public Map<String, String> parseMapping(String mappingFilePath) throws IOException {
        HashMap<String, String> stringToProperties = new HashMap<>();
        Properties properties = new java.util.Properties();
        properties.load(new FileInputStream(mappingFilePath));
        for (final String name : properties.stringPropertyNames())
            stringToProperties.put(name, properties.getProperty(name));
        return stringToProperties;
    }

}
