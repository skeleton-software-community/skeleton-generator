package org.skeleton.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Defines the differents kind of visibilities that are supported for the current version :
 * <li>VISIBLE : visible both in a list and in a detail view of the bean
 * <li>NOT_LIST : only visible in a detail view
 * <li>NOT_VISIBLE : hidden field
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum Visibility {
	VISIBLE (true, true),
    NOT_LIST (false, true),
    NOT_VISIBLE (false, false);
	
	private boolean listVisible;
	private boolean detailVisible;
	
	private Visibility(boolean listVisible, boolean detailVisible){
		this.listVisible = listVisible;
		this.detailVisible = detailVisible;
	}
	
	public boolean isListVisible() {
		return listVisible;
	}

	public boolean isDetailVisible() {
		return detailVisible;
	}
}
