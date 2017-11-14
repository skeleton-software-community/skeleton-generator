package org.sklsft.generator.skeletons;

import java.util.List;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.layers.Layer;

/**
 * This interface represents a Application skeleton as a List of {@link Layer}
 * @author Nicolas Thibault
 *
 */
public interface Skeleton {
	
	String getName();

	List<Layer> getLayers(Project project);
}
