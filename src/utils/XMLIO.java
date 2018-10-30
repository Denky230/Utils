
package utils;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Denky
 */
public class XMLIO {
    
    /**
     * @param filePath path from file we want to get the DOM of
     * @return DOM
     */
    static Document getDOMFromFile(File file) {
        Document doc = null;
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Set optional parameters
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Get DOM from file
            doc = builder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("There was an error when trying to access the DOM - " + ex.getMessage());
        }
        
        return doc;
    }
    /**
     * @param filePath file path
     * @return DOM
     * @see XMLIO#getDOMFromFile(java.io.File)
     */
    static Document getDOMFromFile(String filePath) {
        return getDOMFromFile(new File(filePath));
    }
    
    /**
     * Write into an XML file by using a DOM structure.
     * @param doc DOM
     * @param file XML file
     */
    static void writeDOMIntoFile(Document doc, File file) {
        try {
            // Class to give XML format
            OutputFormat format = new OutputFormat(doc);
            // Enable indenting
            format.setIndenting(true);
            // Class to serialize the DOM so we can write it into the file
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(file), format);
            // Write into the file
            serializer.serialize(doc);
        } catch (IOException ex) {
            System.out.println("There was an error when trying to access the file");
        }
    }
    /**
     * @param doc DOM
     * @param filePath XML file path
     * @see XMLIO#writeDOMIntoFile(org.w3c.dom.Document, java.io.File)
     */
    static void writeDOMIntoFile(Document doc, String filePath) {
        XMLIO.writeDOMIntoFile(doc, new File(filePath));
    }
    
    /**
     * Get a NodeList object from a DOM structure by using a <i>query</i>.
     * @param doc DOM
     * @param query query
     * @return NodeList result object
     */
    static NodeList select(Document doc, String query) {
        NodeList result = null;
        
        try {
            // Class to make queries
            XPath xpath = XPathFactory.newInstance().newXPath();
            // Class to compile query so it can be evaluated
            XPathExpression exp = xpath.compile(query);
            // Specifying NODESET as a result allows to cast the otherwise Object into a NodeList
            result = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            System.out.println("There was an error when making the query - " + ex.getMessage());
        }
        
        return result;
    }
}