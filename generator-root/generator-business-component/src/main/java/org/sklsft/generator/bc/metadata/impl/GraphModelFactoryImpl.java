package org.sklsft.generator.bc.metadata.impl;

import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.bc.metadata.interfaces.GraphModelFactory;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.GraphModel;
import org.sklsft.generator.model.om.GraphNode;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Table;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Michael Fernandez
 *
 */
@Component
public class GraphModelFactoryImpl implements GraphModelFactory {
	public GraphModel buildGraphModel(Model model) {
		GraphModel graphModel = new GraphModel();
		
		Map<Table,GraphNode> nodes = new HashMap<Table, GraphNode>();
		for (Package pack : model.packages) {
			for (Table table : pack.tables) {
				add(nodes, table);
			}
		}	
		graphModel.setNodes(nodes);
		return graphModel;
	}
	
	private GraphNode add(Map<Table,GraphNode> nodes, Table table) {
		GraphNode graphNode;
		if (nodes.containsKey(table)) {
			graphNode = nodes.get(table);
		} else {
			 graphNode = new GraphNode(table);
			 
			 nodes.put(table, graphNode);
			
			 for (Column column : table.columns) {
				 if (column.referenceTable != null) {
					 GraphNode refTable = add(nodes, column.referenceTable);
					 refTable.addDependantTable(graphNode);
				 }
			 }			
		}
		return graphNode;
	}
	
	
}
