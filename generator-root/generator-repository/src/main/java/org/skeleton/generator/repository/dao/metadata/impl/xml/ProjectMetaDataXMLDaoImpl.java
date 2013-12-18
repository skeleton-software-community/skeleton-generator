package org.skeleton.generator.repository.dao.metadata.impl.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.skeleton.generator.exception.ConfigurationReadException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Implementation that parses the project configuration from a single xml file
 * @author Mounir Regragui
 *
 */
public class ProjectMetaDataXMLDaoImpl implements ProjectMetaDataDao{
	
	private static final String CONFIG_FILE_NAME = "skeleton.xml";
	
	private DocumentBuilderFactory dbFactory;
	private ProjectMetaDataParser parser;
	
	public ProjectMetaDataXMLDaoImpl(ProjectMetaDataParser parser) {
		this.parser = parser;
		this.dbFactory = DocumentBuilderFactory.newInstance();
	}

	@Override
	public ProjectMetaData loadProjectMetaData(String folderPath) throws ConfigurationReadException{
		File configFile = getConfigurationFile(folderPath);
		
		try {
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document doc = builder.parse(configFile);
			
			Element root = doc.getDocumentElement();
			root.normalize();
			
			return parser.parse(root);
			
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			throw new ConfigurationReadException(e);
		}
		
	}
	
	private File getConfigurationFile(String folderPath) throws ConfigurationReadException{
		Path parametersPath = Paths.get(folderPath + File.separator + CONFIG_FILE_NAME);
		if (!Files.exists(parametersPath)) {
			throw new ConfigurationReadException("Unable to find project in folder : " + folderPath);
		}
		return parametersPath.toFile();
	}

	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws XPathExpressionException, ConfigurationReadException {
		ProjectMetaDataParser theParser = new ProjectMetaDataParser();
		ProjectMetaDataXMLDaoImpl runner = new ProjectMetaDataXMLDaoImpl(theParser);
		
		ProjectMetaData result = runner.loadProjectMetaData("C:\\Workspaces\\afklm\\distribution\\distribution-root\\dallas-root\\data-model");
		System.out.println(result.toString());
	}

}
