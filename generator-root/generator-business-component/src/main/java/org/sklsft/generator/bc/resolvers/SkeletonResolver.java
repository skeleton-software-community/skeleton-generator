package org.sklsft.generator.bc.resolvers;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.Skeleton;

/**
 * a static builder to get the {@link Skeleton} o use depending on the project's skeleton
 * @author Nicolas Thibault
 *
 */
public class SkeletonResolver {
	
	public static Skeleton getSkeleton(Project project) {
		try {
		
			switch (project.skeletonType) {
				
				case SPRING_HIBERNATE_RICHFACES_4:
				
					return (Skeleton) Class.forName("org.sklsft.generator.skeletons.jsf.SpringHibernateRichFaces4Skeleton").newInstance();
				
					
				default:
					throw new IllegalArgumentException("Unhandled Skeleton type : " + project.skeletonType.name());
			}
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new IllegalArgumentException("Unhandled Skeleton type : " + project.skeletonType.name(), e);
		}
	}
}
