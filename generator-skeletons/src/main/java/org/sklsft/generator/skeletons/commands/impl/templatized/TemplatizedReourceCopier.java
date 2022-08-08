package org.sklsft.generator.skeletons.commands.impl.templatized;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.sklsft.generator.model.domain.Project;

public class TemplatizedReourceCopier {
	
	private static VelocityEngine engine;
	private VelocityContext context;
	
	static {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class",ClasspathResourceLoader.class.getName());
		engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
		engine.init();
	}
	
	public TemplatizedReourceCopier(Project project) {
		this.context = new VelocityContext();
		this.context.put("project", project);
	}

	public void resolveAndCopy(String resourceLocation, Path targetFile) throws IOException {
		Template template = engine.getTemplate(resourceLocation);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		Files.write(targetFile, writer.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
}
