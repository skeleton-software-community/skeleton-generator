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

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.impl.xml.parser.ProjectMetaDataParser;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.skeleton.generator.util.metadata.PersistenceMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Implementation that parses the project configuration from a single xml file
 * @author Mounir Regragui
 *
 */
@Component(value="projectMetaDataXmlDao")
public class ProjectMetaDataXMLDaoImpl implements ProjectMetaDataDao {
	
	private DocumentBuilderFactory dbFactory;
	
	@Autowired
	private ProjectMetaDataParser parser;
	
	public ProjectMetaDataXMLDaoImpl() {
		this.dbFactory = DocumentBuilderFactory.newInstance();
	}

	@Override
	public ProjectMetaData loadProjectMetaData(String workspacePath) {
		
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		File configFile = getConfigurationFile(sourcePath);
		
		try {
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document doc = builder.parse(configFile);
			
			Element root = doc.getDocumentElement();
			root.normalize();
			
			ProjectMetaData projectMetaData = parser.parse(root);
			
			projectMetaData.setWorkspaceFolder(workspacePath);
			projectMetaData.setSourceFolder(sourcePath);
			projectMetaData.setPersistenceMode(PersistenceMode.XML);
			
			return projectMetaData;
			
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			throw new InvalidProjectMetaDataException("",e);
		}
		
	}
	
	private File getConfigurationFile(String sourcePath) {
		
		Path parametersPath = Paths.get(sourcePath + File.separator + XML_CONFIG_FILE_NAME);
		if (!Files.exists(parametersPath)) {
			throw new ProjectNotFoundException("Unable to find project in folder : " + sourcePath);
		}
		return parametersPath.toFile();
	}

	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		// TODO Auto-generated method stub
		
	}
}
