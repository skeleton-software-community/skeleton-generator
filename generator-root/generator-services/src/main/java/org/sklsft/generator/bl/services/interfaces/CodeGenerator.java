package org.sklsft.generator.bl.services.interfaces;

import org.sklsft.generator.model.domain.Project;

/**
 * When writing a skeleton, we need to :
 * <li>define the files that will be copied from resources at initialization
 * <li>define the configuration files that will be created at initialization (can depend on global project metadata)
 * <li>define the files that will be potentially written at each code generation (fully depends on project metadata)
 * 
 * @author Nicolas Thibault
 *
 */
public interface CodeGenerator {
	
	void initResources(Project project);
	
	void initConfiguration(Project project);
	
	void generateCode(Project project);
}
