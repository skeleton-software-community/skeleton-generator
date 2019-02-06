package org.sklsft.generator.bc.validation.rules.impl;

import org.sklsft.generator.bc.validation.rules.ProjectMetaDataRuleChecker;

public enum Rules {

	NO_DUPLICATE_TABLE(NoDuplicateTableChecker.class),
	INVALID_REFERENCE_TABLE(InvalidReferenceTableChecker.class),
	INVALID_CARDINALITY_CHECKER(InvalidCardinalityChecker.class),
	ZERO_CARDINALITY_REFERENCE_TABLE_CHECKER(ZeroCardinalityReferenceTableChecker.class),
	INVALID_ID_TYPE_CHECKER(InvalidIdTypeChecker.class);
	
	private Class<? extends ProjectMetaDataRuleChecker> checkerClass; 
	
	private Rules(Class<? extends ProjectMetaDataRuleChecker> checkerClass) {
		this.checkerClass = checkerClass;
	}

	public Class<? extends ProjectMetaDataRuleChecker> getCheckerClass() {
		return checkerClass;
	}
}
