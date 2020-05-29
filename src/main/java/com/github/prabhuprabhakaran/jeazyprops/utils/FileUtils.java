/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.utils;

/**
 *
 * @author Prabhu Prabhakaran
 */
import com.prabhu.jeazyprops.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileUtils {

    public static String relativePath(File from, File to) {
        return relativePath(from, to, File.separatorChar);
    }

    public static String relativePath(File from, File to, char separatorChar) {
        String fromPath = from.getAbsolutePath();
        String toPath = to.getAbsolutePath();
        boolean isDirectory = from.isDirectory();
        return relativePath(fromPath, toPath, isDirectory, separatorChar);
    }

    public static String relativePath(String fromPath, String toPath, boolean fromIsDirectory) {
        return relativePath(fromPath, toPath, fromIsDirectory, File.separatorChar);
    }

    public static String relativePath(String fromPath, String toPath, boolean fromIsDirectory, char separatorChar) {
        ArrayList<String> fromElements = splitPath(fromPath);
        ArrayList<String> toElements = splitPath(toPath);
        while (!fromElements.isEmpty() && !toElements.isEmpty()) {
            if (!(fromElements.get(0).equals(toElements.get(0)))) {
                break;
            }
            fromElements.remove(0);
            toElements.remove(0);
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < fromElements.size() - (fromIsDirectory ? 0 : 1); i++) {
            result.append("..");
            result.append(separatorChar);
        }
        for (String s : toElements) {
            result.append(s);
            result.append(separatorChar);
        }
        return result.substring(0, result.length() - 1);
    }

    private static ArrayList<String> splitPath(String path) {
        ArrayList<String> pathElements = new ArrayList<String>();
        for (StringTokenizer st = new StringTokenizer(path, File.separator); st
                .hasMoreTokens();) {
            String token = st.nextToken();
            if (token.equals(".")) {
                // do nothing
            } else if (token.equals("..")) {
                if (!pathElements.isEmpty()) {
                    pathElements.remove(pathElements.size() - 1);
                }
            } else {
                pathElements.add(token);
            }
        }
        return pathElements;
    }

    /**
     * Creates a Empty XML Properties File
     *
     * @param pFilePath File Name to create a new XML File
     */
    public static void createNewXMLFile(String pFilePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(Constants.PropertiesRootString);
            doc.appendChild(rootElement);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, Constants.PropertiesStandard);
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pFilePath));
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isValidXML(String pFilePath) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.XML_DTD_NS_URI);
            Schema schema = schemaFactory.newSchema(Runtime.getRuntime().getClass().getResource("properties.dtd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pFilePath));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
