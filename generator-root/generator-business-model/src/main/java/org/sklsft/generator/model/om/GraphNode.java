package org.sklsft.generator.model.om;

import java.util.ArrayList;
import java.util.List;

public class GraphNode {
	private Table 				table;
	private List<GraphNode>		dependantTables;

	
	public GraphNode(Table table) {
		this.table = table;
		this.dependantTables = new ArrayList<GraphNode>();
	}
	
	public void addDependantTable(GraphNode node) {
		dependantTables.add(node);
	}

	public Table getTable() {
		return table;
	}

	public List<GraphNode> getDependantTables() {
		return dependantTables;
	}
	
	
}
