package org.sklsft.generator.persistence.metadata.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.sklsft.generator.model.metadata.datasources.DataSourceMetaData;
import org.springframework.stereotype.Component;

@Component
public class DataSourceContextInitializer {
	
	private static VelocityEngine engine;
	private static final String UTF8 = "UTF8";
	private VelocityContext context;
	private Template template;
	protected StringWriter writer = new StringWriter();
	
	
	static {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class",ClasspathResourceLoader.class.getName());
		engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
		engine.init();
	}
	
	public DataSourceContextInitializer() {
		this.context = new VelocityContext();
		this.template = engine.getTemplate("/datasource-context.vm", UTF8);
	}
	
	
	public void init(DataSourceMetaData datasource, String folderName) throws IOException {
		
		Path filePath = Paths.get(folderName + File.separator + "datasource-context.xml");
		
		context.put("datasource", datasource);
		template.merge(context, writer);
		
		try (BufferedWriter fileWriter = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8);){
			fileWriter.write(writer.toString());
			fileWriter.flush();
		}
	}

}