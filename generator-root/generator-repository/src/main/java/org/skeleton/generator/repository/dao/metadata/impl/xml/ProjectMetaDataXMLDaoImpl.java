package org.skeleton.generator.repository.dao.metadata.impl.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

/**
 * Implementation that uses jaxb
 * @author Mounir Regragui
 *
 */
@Component(value="projectMetaDataXmlDao")
public class ProjectMetaDataXMLDaoImpl implements ProjectMetaDataDao {
	
	private static final String SCHEMA_LOCATION = "skeleton-metadata-1.0.xsd";


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
			
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	        Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource(SCHEMA_LOCATION));
	        jaxbUnmarshaller.setSchema(schema);
			
			ProjectMetaData projectMetaData = (ProjectMetaData) jaxbUnmarshaller.unmarshal(file);
			
			projectMetaData.setWorkspaceFolder(workspacePath);
			projectMetaData.setSourceFolder(sourcePath);
			
			return projectMetaData;
			
		} catch (JAXBException | SAXException e) {
			throw new InvalidProjectMetaDataException("Unable to parse skeleton.xml file", e);
		}
	}
	
	
	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		
		String sourcePath = projectMetaData.getSourceFolder();
		Path parametersPath = Paths.get(sourcePath + File.separator + XML_CONFIG_FILE_NAME);
		File file = parametersPath.toFile();
		
		try {
			
			if (Files.exists(Paths.get(sourcePath + File.separator + SCHEMA_LOCATION))) {
				Files.delete(Paths.get(sourcePath + File.separator + SCHEMA_LOCATION));
			}
			copyXsd(sourcePath);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ProjectMetaData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
			Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource(SCHEMA_LOCATION));
	        jaxbMarshaller.setSchema(schema);
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,"http://www.skeleton-generator.org/model/metadata skeleton-metadata-1.0.xsd");
			 
			jaxbMarshaller.marshal(projectMetaData, file);
			
		} catch (JAXBException | SAXException e) {
			throw new InvalidProjectMetaDataException("Failed to write skeleton.xml file", e);
		} catch (IOException e) {
			throw new InvalidProjectMetaDataException("Failed to write xsd file", e);
		}
	}


	private void copyXsd(String sourcePath) throws IOException {
		
		try (
				InputStream inputStream = getClass().getResourceAsStream("/" + SCHEMA_LOCATION);
				OutputStream outputStream = new FileOutputStream(Files.createFile(Paths.get(sourcePath + File.separator + SCHEMA_LOCATION)).toFile());
			) {
				int read = 0;
				byte[] bytes = new byte[1024];
		 
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
			}
		}	
	}
}
