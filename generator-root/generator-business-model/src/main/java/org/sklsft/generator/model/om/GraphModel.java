package org.sklsft.generator.model.om;

import java.util.Map;

public class GraphModel {
	private Map<Table, GraphNode>	nodes;
	
	public GraphNode getNode(Table table) {
		return nodes.get(table);
	}
	public Map<Table, GraphNode> getNodes() {
		return nodes;
	}

	public void setNodes(Map<Table, GraphNode> nodes) {
		this.nodes = nodes;
	}


}
