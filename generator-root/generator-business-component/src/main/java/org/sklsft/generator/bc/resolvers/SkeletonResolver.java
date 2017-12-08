package org.sklsft.generator.bc.resolvers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.Skeleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * a static builder to get the {@link Skeleton} o use depending on the project's skeleton
 * @author Nicolas Thibault
 *
 */
public class SkeletonResolver {
	
	public static Map<String, Skeleton> skeletons = new HashMap<>();
	
	static {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(Skeleton.class));
		
		Set<BeanDefinition> defs = provider.findCandidateComponents("org.sklsft.generator.skeletons");
		
		for (BeanDefinition def:defs) {
			try {
				Skeleton handler = (Skeleton) Class.forName(def.getBeanClassName()).newInstance();
				skeletons.put(handler.getName(), handler);			
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid DatabaseHandler : " + def.getBeanClassName(), e);
			}
		}
	}

	public static Skeleton getSkeleton(Project project) {
		return skeletons.get(project.getSkeleton());
	}
}
