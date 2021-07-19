package org.sklsft.generator.components.metadata.factory.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.sklsft.generator.components.metadata.factory.interfaces.BasicViewBeanFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.ViewPropertiesFactory;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.BasicViewBean;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.springframework.stereotype.Component;

@Component("javaBasicViewBeanFactory")
public class JavaBasicViewBeanFactory implements BasicViewBeanFactory {
	
	@Resource(name="javaViewPropertiesFactory")
	private ViewPropertiesFactory viewPropertiesFactory;

	@Override
	public BasicViewBean getBasicViewBean(Bean bean) {
				
		BasicViewBean basicViewBean = new BasicViewBean();
		
		basicViewBean.className = bean.className + "BasicView";
		basicViewBean.objectName = bean.objectName + "BasicView";
		
		basicViewBean.baseMapperClassName = basicViewBean.className + "BaseMapper";
		basicViewBean.mapperClassName = basicViewBean.className + "Mapper";
		basicViewBean.mapperObjectName = basicViewBean.objectName + "Mapper";
		
		basicViewBean.filter.className = bean.className + "Filter";
		basicViewBean.sortingClassName = bean.className + "Sorting";
		
		basicViewBean.properties = viewPropertiesFactory.getBasicViewProperties(bean);
		
		for (ViewProperty property:basicViewBean.properties) {
			if (property.filterable) {
				basicViewBean.filter.properties.addAll(buildFilterProperties(property));
			}
		}
		
		return basicViewBean;
	}

	@Override
	public BasicViewBean getBasicViewBean(OneToMany oneToMany) {
		
		BasicViewBean basicViewBean = new BasicViewBean();
		
		Bean bean = oneToMany.referenceBean;
		
		basicViewBean.className = bean.className + "BasicView";
		basicViewBean.objectName = bean.objectName + "BasicView";
		
		basicViewBean.baseMapperClassName = basicViewBean.className + "BaseMapper";
		basicViewBean.mapperClassName = basicViewBean.className + "Mapper";
		basicViewBean.mapperObjectName = basicViewBean.objectName + "Mapper";
		
		basicViewBean.filter.className = bean.className + "Filter";
		
		basicViewBean.properties = viewPropertiesFactory.getBasicViewProperties(oneToMany);
		
		for (ViewProperty property:basicViewBean.properties) {
			if (property.filterable) {
				basicViewBean.filter.properties.addAll(buildFilterProperties(property));
			}
		}
		
		return basicViewBean;
	}

	private List<FilterProperty> buildFilterProperties(ViewProperty property) {
		List<FilterProperty> result = new ArrayList<>();
		if (property.dataType.isLimitable()) {
			FilterProperty minProperty = new FilterProperty();
			minProperty.name = property.name + "MinValue";
			minProperty.dataType = property.dataType;
			minProperty.tsType = property.tsType;
			minProperty.rendering = property.rendering + " Min";
			result.add(minProperty);
			
			FilterProperty maxProperty = new FilterProperty();
			maxProperty.name = property.name + "MaxValue";
			maxProperty.dataType = property.dataType;
			maxProperty.tsType = property.tsType;
			maxProperty.rendering = property.rendering + " Max";
			result.add(maxProperty);
		} else {
			FilterProperty filterProperty = new FilterProperty();
			filterProperty.name = property.name;
			filterProperty.dataType = property.dataType;
			filterProperty.tsType = property.tsType;
			filterProperty.rendering = property.rendering;
			result.add(filterProperty);
		}
		return result;
	}

	
}
