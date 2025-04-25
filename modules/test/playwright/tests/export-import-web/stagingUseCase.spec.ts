/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../fixtures/webContentDisplayPageTest';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {stagingConfigartionPageTest} from '../export-import-web/fixtures/stagingConfigartionPageTest';
import {stagingPageTest} from '../export-import-web/fixtures/stagingPageTest';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	productMenuPageTest,
	uiElementsPageTest,
	stagingPageTest,
	stagingConfigartionPageTest,
	webContentDisplayPageTest
);

test('Non Modified Referred Content Cannot Publish To Live When Enable Include If Modified Option', async ({
	apiHelpers,
	stagingConfigartionPage,
	stagingPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'site-' + getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	const webContentContent = getRandomString();

	let webContent = await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: webContentContent,
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		groupId: site.id,
		titleMap: {en_US: getRandomString()},
	});

	apiHelpers.data.push({
		id: `${site.id}_${webContent.articleId}`,
		type: 'webContent',
	});

	await stagingPage.goto(site.name);
	await stagingPage.enableLocalStaging();

	const stagingSite =
		await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
			`${site.friendlyUrlPath}-staging`
		);

	const document = await apiHelpers.headlessDelivery.postDocument(
		stagingSite.id,
		createReadStream(path.join(__dirname, '/dependencies/Document.jpg')),
		{
			fileName: 'Document.jpg',
			title: 'Document.jpg',
		}
	);

	webContent = await apiHelpers.jsonWebServicesJournal.editWebContent(
		{
			content: `<img alt="" data-fileentryid="${document.id}" src="/documents/d${stagingSite.friendlyUrlPath}/Document-jpg">&nbsp;<br>${webContentContent}`,
		},
		stagingSite.id,
		webContent
	);

	await stagingConfigartionPage.goto(site.name);
	await stagingConfigartionPage.disableTemporaryLARdeletion();

	webContent = await apiHelpers.jsonWebServicesJournal.editWebContent(
		{title: getRandomString()},
		stagingSite.id,
		webContent
	);

	await stagingPage.goto(site.name + '-staging');
	await stagingPage.publish(['Web Content 1 Items Web']);
});
