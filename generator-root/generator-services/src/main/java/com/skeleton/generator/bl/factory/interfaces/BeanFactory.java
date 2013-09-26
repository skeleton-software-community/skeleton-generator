package com.skeleton.generator.bl.factory.interfaces;

import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.model.om.Bean;
import com.skeleton.generator.model.om.Table;

public interface BeanFactory {

	Bean buildBean(TableMetaData tableMetaData, Table table);

}
