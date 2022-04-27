package com.liferay.training.gradebook.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = "indexer.class.name=com.liferay.training.gradebook.model.Assignment", service = ModelSummaryContributor.class)
public class AssignmentModelSummaryContributor implements ModelSummaryContributor {

	@Override
	public Summary getSummary(Document document, Locale locale, String snippet) {

		String languageId = LocaleUtil.toLanguageId(locale);

		return _createSummary(document, LocalizationUtil.getLocalizedName(Field.DESCRIPTION, languageId),
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId));
	}

	private Summary _createSummary(Document document, String descriptionField, String titleField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		Summary summary = new Summary(document.get(prefix + titleField, titleField),
				document.get(prefix + descriptionField, descriptionField));

		summary.setMaxContentLength(200);

		return summary;
	}

}