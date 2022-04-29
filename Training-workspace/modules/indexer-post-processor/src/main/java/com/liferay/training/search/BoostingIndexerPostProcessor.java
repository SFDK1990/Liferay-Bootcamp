package com.liferay.training.search;

import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author dasencio
 */
@Component(
		immediate = true,
		property = {
			"indexer.class.name=com.liferay.journal.model.JournalArticle",
		},
		service = IndexerPostProcessor.class
	)
public class BoostingIndexerPostProcessor extends BaseIndexerPostProcessor {

	@Override
	public void postProcessSearchQuery(
		BooleanQuery searchQuery, BooleanFilter booleanFilter,
		SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		System.out.println("Keywords: " + keywords);

		// If keywords entered by the user contain word "spec", 
		// boost contents having a tag "specification".

		if (Validator.isNotNull(keywords)) {

			String[] searchTerms = keywords.toLowerCase().split(" ");

			for (String s : searchTerms) {
				if (s.equals("spec")) {
					TermQuery termQuery = new TermQueryImpl(
						Field.ASSET_TAG_NAMES, "specification");
					termQuery.setBoost(100);
					searchQuery.add(termQuery, BooleanClauseOccur.SHOULD);

				}
			}
		}
	}

}