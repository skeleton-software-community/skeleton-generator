package org.sklsft.generator.bc.file.factory.impl;

import org.sklsft.generator.bc.file.factory.interfaces.FileWriteCommandTreeFactory;
import org.sklsft.generator.model.om.Project;

/**
 * a static builder to get the {@link FileWriteCommandTreeFactory} o use depending on the project's skeleton
 * @author Nicolas Thibault
 *
 */
public class FileWriteCommandTreeFactoryBuilder {
	
	public static FileWriteCommandTreeFactory getFileWriteCommandTreeFactory(Project project) {
		switch (project.skeletonType) {
			case SPRING_HIBERNATE_RICHFACES_3:
				return new SpringHibernateRichFaces3CommandTreeFactory();
			
			case SPRING_HIBERNATE_RICHFACES_4:
				return new SpringHibernateRichFaces3CommandTreeFactory();
				
			default:
				throw new IllegalArgumentException("Unhandled Skeleton type : " + project.skeletonType.name());
		}
	}
}
