package org.sklsft.generator.persistence.backup.reader.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.sklsft.generator.model.backup.SourceAndScript;
import org.sklsft.generator.model.exception.BackupFileNotFoundException;
import org.sklsft.generator.model.exception.InvalidXmlBackupFileException;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * This class is used to convert a xml file to a {@link SourceAndScript}<br/>
 * @author Nicolas Thibault
 *
 */
public class XmlFileSourceAndScriptParser {
	
	private static final String SCHEMA_LOCATION = "backup-1.0.xsd";

	public SourceAndScript parse(String scriptFilePath) throws IOException {

		Path path = Paths.get(scriptFilePath);
		
		if (!Files.exists(path)) {
			throw new BackupFileNotFoundException("Unable to find backup file : " + scriptFilePath);
		}
		File file = path.toFile();
		
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(SourceAndScript.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	        Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource(SCHEMA_LOCATION));
	        jaxbUnmarshaller.setSchema(schema);
			
	        SourceAndScript sourceAndScript = (SourceAndScript) jaxbUnmarshaller.unmarshal(file);
			
			return sourceAndScript;
			
		} catch (JAXBException | SAXException e) {
			throw new InvalidXmlBackupFileException("Unable to parse backup file : " + scriptFilePath, e);
		}
	}

}
