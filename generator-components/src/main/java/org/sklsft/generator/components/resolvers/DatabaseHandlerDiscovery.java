package org.sklsft.generator.components.resolvers;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSource;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.database.DatabaseHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class DatabaseHandlerDiscovery {
	
	public static Map<String, DatabaseHandler> handlersMap = new HashMap<>();
	public static Map<String, DatabaseHandler> byDriverHandlersMap = new HashMap<>();
	
	public static Set<DatabaseHandler> handlers = new HashSet<>();
	
	static {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(DatabaseHandler.class));
		
		String[] packagesToScan = ResourceBundle.getBundle("generator").getString("database.handlers.path").split(",");
		
		Set<BeanDefinition> defs = new HashSet<>();
		
		
		for (String packageToScan:packagesToScan) {
			defs.addAll(provider.findCandidateComponents(packageToScan.trim()));
		}
		
		for (BeanDefinition def:defs) {
			try {
				DatabaseHandler handler = (DatabaseHandler) Class.forName(def.getBeanClassName()).newInstance();
				handlersMap.put(handler.getName(), handler);
				byDriverHandlersMap.put(handler.getDriverClassName(), handler);
				handlers.add(handler);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid DatabaseHandler : " + def.getBeanClassName(), e);
			}
		}
	}
	
	
	public static String getBuildScriptFolder(String engineName) {
		return "data-model" + File.separator + "scripts" + File.separator + "SQL" + File.separator + engineName + File.separator + "build";
	}
	
	
	public static String getBuildScriptFolder(DatabaseHandler handler) {
		return getBuildScriptFolder(handler.getName());
	}
	
	public static String getBuildScriptFolder(BasicDataSource dataSource) {
		DatabaseHandler handler = byDriverHandlersMap.get(dataSource.getDriverClassName());
		return getBuildScriptFolder(handler);
	}
	
	public static DatabaseHandler getDatabaseHandler(String databaseEngine) {
		return handlersMap.get(databaseEngine);
	}

	public static DatabaseHandler getDatabaseHandler(Project project) {
		return handlersMap.get(project.getDatabaseEngine());
	}
}
