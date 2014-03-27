package org.skeleton.generator.repository.dao.datasource.impl;

import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.skeleton.generator.model.backup.SourceAndScript;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlFileSourceAndScriptReader {

	public SourceAndScript readScript(String scriptFilePath) throws IOException {

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
