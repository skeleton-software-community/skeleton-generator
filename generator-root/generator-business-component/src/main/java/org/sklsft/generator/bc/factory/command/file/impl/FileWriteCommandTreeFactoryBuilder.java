package org.sklsft.generator.bc.factory.command.file.impl;

import org.sklsft.generator.bc.factory.command.file.interfaces.FileWriteCommandTreeFactory;
import org.sklsft.generator.model.om.Project;

/**
 * a static builder to get the {@link FileWriteCommandTreeFactory} o use depending on the project's skeleton
 * @author Nicolas Thibault
 *
 */
public class FileWriteCommandTreeFactoryBuilder {
	
	public static FileWriteCommandTreeFactory getFileWriteCommandTreeFactory(Project project) {
		switch (project.skeletonType) {
			case SPRING_HIBERNATE_RICHFACES:
				return new SpringHibernateRichFacesCommandTreeFactory();
				
			case BASIC_SPRING_HIBERNATE_RICHFACES:
				return new BasicSpringHibernateRichFacesCommandTreeFactory();
				
			default:
				throw new IllegalArgumentException("Unhandled Skeleton type : " + project.skeletonType.name());
		}
	}
}
