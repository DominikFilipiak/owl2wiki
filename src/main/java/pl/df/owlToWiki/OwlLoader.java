package pl.df.owlToWiki;

import java.util.List;

/**
 * User: dominikfilipiak
 * Date: 24/02/2014
 * Time: 12:55
 */
public interface OwlLoader {
    public void loadFiles();
    public void setInputFiles(List<String> inputFiles);
}
