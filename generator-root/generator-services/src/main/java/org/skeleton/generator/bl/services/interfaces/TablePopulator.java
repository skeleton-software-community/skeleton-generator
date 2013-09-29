package org.skeleton.generator.bl.services.interfaces;

import org.skeleton.generator.exception.PopulateTableFailureException;

public interface TablePopulator {

	void populateTable() throws PopulateTableFailureException;
}
