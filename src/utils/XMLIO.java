
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

    private File file;

    public XMLIO(File file) {
        this.file = file;
    }
    public XMLIO(String filePath) {
        this(new File(filePath));
    }

    /**
     * @return DOM
     */
    public Document getDOM() throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Set optional parameters
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            // Get DOM from file
            Document dom = builder.parse(file);
            return dom;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new IOException("There was an error when trying to access the persistence file");
        }
    }

    /**
     * Write into the XML file from a DOM structure.
     * @param doc DOM
     */
    public void writeFromDOM(Document doc) {
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
     * Get a NodeList object from a DOM structure by using a <i>query</i>.
     * @param query query
     * @return NodeList result object
     */
    public NodeList select(String query) throws IOException {
        Document dom = getDOM();

        try {
            // Class to make queries
            XPath xpath = XPathFactory.newInstance().newXPath();
            // Class to compile query so it can be evaluated
            XPathExpression exp = xpath.compile(query);
            // Specifying NODESET as a result allows to cast the otherwise Object into a NodeList
            NodeList result = (NodeList) exp.evaluate(dom, XPathConstants.NODESET);
            
            if (result != null) return result;
            else throw new NullPointerException();
        } catch (XPathExpressionException | NullPointerException ex) {
            throw new NullPointerException("There was an error when making the query - " + ex.getMessage());
        }
    }
}