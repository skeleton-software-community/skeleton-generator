package com.skeleton.generator.bl.services.interfaces;

import com.skeleton.generator.exception.PopulateTableFailureException;

public interface TablePopulator {

	void populateTable() throws PopulateTableFailureException;
}
