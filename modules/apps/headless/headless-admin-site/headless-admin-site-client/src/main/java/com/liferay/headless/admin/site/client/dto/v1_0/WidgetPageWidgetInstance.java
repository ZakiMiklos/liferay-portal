/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.WidgetPageWidgetInstanceSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class WidgetPageWidgetInstance implements Cloneable, Serializable {

	public static WidgetPageWidgetInstance toDTO(String json) {
		return WidgetPageWidgetInstanceSerDes.toDTO(json);
	}

	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String externalReferenceCode;

	public WordSpacing getWordSpacing() {
		return wordSpacing;
	}

	public String getWordSpacingAsString() {
		if (wordSpacing == null) {
			return null;
		}

		return wordSpacing.toString();
	}

	public void setWordSpacing(WordSpacing wordSpacing) {
		this.wordSpacing = wordSpacing;
	}

	public void setWordSpacing(
		UnsafeSupplier<WordSpacing, Exception> wordSpacingUnsafeSupplier) {

		try {
			wordSpacing = wordSpacingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WordSpacing wordSpacing;

	@Override
	public WidgetPageWidgetInstance clone() throws CloneNotSupportedException {
		return (WidgetPageWidgetInstance)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WidgetPageWidgetInstance)) {
			return false;
		}

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			(WidgetPageWidgetInstance)object;

		return Objects.equals(toString(), widgetPageWidgetInstance.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WidgetPageWidgetInstanceSerDes.toJSON(this);
	}

	public static enum WordSpacing {

		_1EM("-1em"), _0_95EM("-0.95em"), NUM_0_9EM("0.9em"),
		NUM_0_95EM("0.95em"), NUM_1_2EM("1.2em"), NUM_12EM("12em");

		public static WordSpacing create(String value) {
			for (WordSpacing wordSpacing : values()) {
				if (Objects.equals(wordSpacing.getValue(), value) ||
					Objects.equals(wordSpacing.name(), value)) {

					return wordSpacing;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private WordSpacing(String value) {
			_value = value;
		}

		private final String _value;

	}

}