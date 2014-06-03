package pl.df.owlToWiki.facade.article;

import java.io.IOException;
import java.util.Map;

/**
 * User: dominikfilipiak
 * Date: 23/03/2014
 * Time: 11:45
 */
public interface MappingParser {
    /**
     * Parses file with mapping.
     *
     * @param mappingFilePath Path to the file
     * @return A map containing pairs of values (property from template -> URI)
     * @throws IOException
     */
    public Map<String, String> parseMapping(String mappingFilePath) throws IOException;
}
