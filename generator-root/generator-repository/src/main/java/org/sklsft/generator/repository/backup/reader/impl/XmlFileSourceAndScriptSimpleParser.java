package org.sklsft.generator.repository.backup.reader.impl;

import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.sklsft.generator.model.backup.SourceAndScript;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class is used to convert a xml file to a {@link SourceAndScript}<br/>
 * should use Jaxb2 for next releases
 * @author Nicolas Thibault
 *
 */
@Component
public class XmlFileSourceAndScriptSimpleParser {

	public SourceAndScript parse(String scriptFilePath) throws IOException {

		String source = null;
		String script = null;
		Document document = null;
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			document = builder.parse(Paths.get(scriptFilePath).toFile());
			
			Element element = document.getDocumentElement();
			source = element.getElementsByTagName("source").item(0).getTextContent();
			script = element.getElementsByTagName("script").item(0).getTextContent();
			
		} catch (Exception e) {
			throw new IOException("failed to read xml file : " + scriptFilePath,e);
		}
		
		SourceAndScript sourceAndScript = new SourceAndScript();
		sourceAndScript.setScript(script);
		sourceAndScript.setSource(source);
		
		return sourceAndScript;
	}

}