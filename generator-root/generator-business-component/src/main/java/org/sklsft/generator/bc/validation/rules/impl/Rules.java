package org.sklsft.generator.bc.validation.rules.impl;

import org.sklsft.generator.bc.validation.rules.ProjectMetaDataRuleChecker;

public enum Rules {

	NO_DUPLICATE_TABLE(NoDuplicateTableChecker.class);
	
	private Class<? extends ProjectMetaDataRuleChecker> checkerClass; 
	
	private Rules(Class<? extends ProjectMetaDataRuleChecker> checkerClass) {
		this.checkerClass = checkerClass;
	}

	public Class<? extends ProjectMetaDataRuleChecker> getCheckerClass() {
		return checkerClass;
	}
}
