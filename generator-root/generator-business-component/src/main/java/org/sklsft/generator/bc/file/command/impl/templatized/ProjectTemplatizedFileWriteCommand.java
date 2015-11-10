package org.sklsft.generator.bc.file.command.impl.templatized;

import java.io.IOException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.sklsft.generator.bc.file.command.impl.SingleFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;


/**
 * An implementation of the abstract file write command based on a Velocity {@link Template}
 * @author Nicolas Thibault
 *	
 */
public class ProjectTemplatizedFileWriteCommand extends SingleFileWriteCommand {
	
	private static VelocityEngine engine;
	private static final String separator = "/";
	private static final String UTF8 = "UTF8";
	private static final String DOTVM = ".vm";
	private VelocityContext context;
	private Template template;
	private Project project;
	
	static {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class",ClasspathResourceLoader.class.getName());
		engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
		engine.init();
	}
	
	public ProjectTemplatizedFileWriteCommand(String folderName, String fileName, FileType fileType, Project project) {
		super(folderName, fileName, fileType);
		this.context = new VelocityContext();
		this.template = engine.getTemplate(separator + this.getClass().getName().replace(".", separator) + DOTVM, UTF8);
		this.project = project;
	}

	
	@Override
	public int getRowCount() {
		return 0;
	}


	@Override
	protected void writeContent() throws IOException {
		
		context.put("project", project);
		template.merge(context, writer);
	}

}
