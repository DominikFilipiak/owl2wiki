package pl.df.owlToWiki.facade.utilities;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User: dominikfilipiak
 * Date: 23/03/2014
 * Time: 12:58
 */
public class Utilities {

    /**
     * Reads file to string
     *
     * @param path     Path to file
     * @param encoding Encoding
     * @return String with file content
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

}
