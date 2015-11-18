package org.sklsft.generator.model.metadata;

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
	VISIBLE (true, true, 3),
    NOT_LIST (false, true, 2),
    NOT_VISIBLE (false, false, 1);
	
	private boolean listVisible;
	private boolean detailVisible;
	private int weight;
	
	private Visibility(boolean listVisible, boolean detailVisible, int weight){
		this.listVisible = listVisible;
		this.detailVisible = detailVisible;
		this.weight = weight;
	}
	
	public boolean isListVisible() {
		return listVisible;
	}

	public boolean isDetailVisible() {
		return detailVisible;
	}

	public static Visibility min(Visibility visibility, Visibility visibility2) {
		if (visibility.weight < visibility2.weight) {
			return visibility;
		} else {
			return visibility2;
		}
	}
}
