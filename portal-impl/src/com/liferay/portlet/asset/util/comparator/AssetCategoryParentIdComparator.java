/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.util.comparator;

import com.liferay.asset.kernel.model.AssetCategory;

import java.util.Comparator;

/**
 * @author Miklos Zakanyi
 */
public class AssetCategoryParentIdComparator
	implements Comparator<AssetCategory> {

	@Override
	public int compare(
		AssetCategory assetCategory1, AssetCategory assetCategory2) {

		if (_findParent(assetCategory1, assetCategory2)) {
			return 1;
		}
		else if (_findParent(assetCategory2, assetCategory1)) {
			return -1;
		}

		return 0;
	}

	private boolean _findParent(
		AssetCategory assetCategory1, AssetCategory assetCategory2) {

		AssetCategory parent = assetCategory1.getParentCategory();

		while (parent != null) {
			if (assetCategory2.getCategoryId() == parent.getCategoryId()) {
				return true;
			}

			parent = parent.getParentCategory();
		}

		return false;
	}

}