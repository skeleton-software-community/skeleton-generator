package org.sklsft.generator.skeletons.layers;

public abstract class AbstractLayer implements Layer {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	public AbstractLayer(String name) {
		this.name = name;
	}
}
