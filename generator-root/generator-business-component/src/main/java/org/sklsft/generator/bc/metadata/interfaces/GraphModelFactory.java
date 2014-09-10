package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.om.GraphModel;
import org.sklsft.generator.model.om.Model;

public interface GraphModelFactory {
	GraphModel buildGraphModel(Model model);
}
