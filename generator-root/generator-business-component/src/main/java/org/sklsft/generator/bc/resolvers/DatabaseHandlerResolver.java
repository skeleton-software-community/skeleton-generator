package org.sklsft.generator.bc.resolvers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.database.DatabaseHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class DatabaseHandlerResolver {
	
	public static Map<String, DatabaseHandler> handlers = new HashMap<>();
	
	static {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(DatabaseHandler.class));
		
		Set<BeanDefinition> defs = provider.findCandidateComponents("");
		
		for (BeanDefinition def:defs) {
			try {
				DatabaseHandler handler = (DatabaseHandler) Class.forName(def.getBeanClassName()).newInstance();
				handlers.put(handler.getName(), handler);			
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid DatabaseHandler : " + def.getBeanClassName(), e);
			}
		}
	}

	public static DatabaseHandler getDatabaseHandler(Project project) {
		return handlers.get(project.getDatabaseEngine());
	}
}
