package ddbb;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class BattleHtmlTransformator {
    // Paths for XML and HTML files
    // and the XSLT file
    private static final String XML_DIR = "./M4-Marcas/battles/xml/";
    private static final String HTML_DIR = "./M4-Marcas/battles/html/";
    private static final String XSL_FILE = "./M4-Marcas/battles/battle_transform.xsl";

    public static boolean transform(int battleId) {
        String inputXmlPath = XML_DIR + "battle" + battleId + ".xml";
        String outputHtmlPath = HTML_DIR + "battle" + battleId + ".html";

        try {
            // Verify input file exists
            File xmlFile = new File(inputXmlPath);
            if (!xmlFile.exists()) {
                System.err.println("Input file not found: " + inputXmlPath);
                return false;
            }

            // Set up the XSLT transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xslSource = new StreamSource(new File(XSL_FILE));
            Transformer transformer = factory.newTransformer(xslSource);

            // Set up input and output
            StreamSource xmlSource = new StreamSource(xmlFile);
            StreamResult htmlResult = new StreamResult(new File(outputHtmlPath));

            // Perform the transformation
            transformer.transform(xmlSource, htmlResult);

            System.out.println("Successfully transformed and saved battle " + battleId + " to: " + outputHtmlPath);
            return true;
        } catch (TransformerException e) {
            System.err.println("XSLT transformation failed for battle " + battleId + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing battle " + battleId + ": " + e.getMessage());
        }
        return false;
    }
}