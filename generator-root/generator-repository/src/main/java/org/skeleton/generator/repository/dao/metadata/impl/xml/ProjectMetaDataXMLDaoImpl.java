package org.skeleton.generator.repository.dao.metadata.impl.xml;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.springframework.stereotype.Component;

/**
 * Implementation that uses jaxb
 * @author Mounir Regragui
 *
 */
@Component(value="projectMetaDataXmlDao")
public class ProjectMetaDataXMLDaoImpl implements ProjectMetaDataDao {
	
	

	@Override
	public ProjectMetaData loadProjectMetaData(String workspacePath) {
		
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		Path parametersPath = Paths.get(sourcePath + File.separator + XML_CONFIG_FILE_NAME);
		if (!Files.exists(parametersPath)) {
			throw new ProjectNotFoundException("Unable to find project in folder : " + sourcePath);
		}
		File file = parametersPath.toFile();
		
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ProjectMetaData.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ProjectMetaData projectMetaData = (ProjectMetaData) jaxbUnmarshaller.unmarshal(file);
			
			return projectMetaData;
			
		} catch (JAXBException e) {
			throw new InvalidProjectMetaDataException("Unable to parse skeleton.xml file", e);
		}
	}
	
	
	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		
		String sourcePath = projectMetaData.getSourceFolder();
		Path parametersPath = Paths.get(sourcePath + File.separator + XML_CONFIG_FILE_NAME);
		File file = parametersPath.toFile();
		
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ProjectMetaData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			 
			jaxbMarshaller.marshal(projectMetaData, file);
			
		} catch (JAXBException e) {
			throw new InvalidProjectMetaDataException("Failed to write skeleton.xml file", e);
		}
	}
}
