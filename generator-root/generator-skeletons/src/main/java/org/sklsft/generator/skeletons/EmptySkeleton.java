package org.sklsft.generator.skeletons;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.skeletons.layers.Layer;

public abstract class EmptySkeleton implements Skeleton {

	protected List<Layer> layers = new ArrayList<>();

	@Override
	public List<Layer> getLayers() {
		return layers;
	}
}
