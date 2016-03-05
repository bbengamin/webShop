package com.epam.preprod.bohdanov.utils.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.preprod.bohdanov.exception.DataException;

public class DOMParser {
    private static final String URL_NODE = "url";
    private static final String ROLE_NODE = "role";
    private static final String RULE_NODE = "rule";
    public static final String FEATURE_TURN_VALIDATION_ON = "http://xml.org/sax/features/validation";
    public static final String FEATURE_TURN_SCHEMA_VALIDATION_ON = "http://apache.org/xml/features/validation/schema";

    private String xmlFileName;
    private Map<String, List<String>> rules;

    public DOMParser(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Map<String, List<String>> getRules() {
        return rules;
    }

    public void parse(boolean validate) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        if (validate) {
            dbf.setFeature(FEATURE_TURN_VALIDATION_ON, true);
            dbf.setFeature(FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }
        DocumentBuilder db = dbf.newDocumentBuilder();

        db.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                throw new DataException(e);
            }
        });

        Document document = db.parse(xmlFileName);
        Element root = document.getDocumentElement();
        rules = new HashMap<>();

        NodeList ruleNodes = root.getElementsByTagName(RULE_NODE);

        for (int i = 0; i < ruleNodes.getLength(); i++) {
            Element qElement = (Element) ruleNodes.item(i);

            Node qtNode = qElement.getElementsByTagName(URL_NODE).item(0);
            String url = qtNode.getTextContent();

            NodeList aNodeList = qElement.getElementsByTagName(ROLE_NODE);
            List<String> roles = new ArrayList<>();
            for (int j = 0; j < aNodeList.getLength(); j++) {
                roles.add(aNodeList.item(j).getTextContent());
            }
            rules.put(url, roles);
        }
    }

}
