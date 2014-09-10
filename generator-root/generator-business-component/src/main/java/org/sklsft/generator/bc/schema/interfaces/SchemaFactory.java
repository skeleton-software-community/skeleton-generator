package org.sklsft.generator.bc.schema.interfaces;

import org.sklsft.generator.model.metadatadb.DatabaseMetaData;
import org.sklsft.generator.model.om.DatabaseSchema;
import org.sklsft.generator.model.om.Project;



public interface SchemaFactory {
	DatabaseSchema createSchema(DatabaseMetaData metaData, Project project);
}
