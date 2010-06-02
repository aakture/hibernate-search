package org.hibernate.search.query.dsl.v2.impl;

import org.apache.lucene.search.Filter;

import org.hibernate.search.query.dsl.v2.TermMatchingContext;
import org.hibernate.search.query.dsl.v2.WildcardContext;

/**
 * @author Emmanuel Bernard
 */
class ConnectedWildcardContext implements WildcardContext {
	private final QueryBuildingContext queryContext;
	private final QueryCustomizer queryCustomizer;
	private final TermQueryContext termContext;

	public ConnectedWildcardContext(QueryCustomizer queryCustomizer, QueryBuildingContext queryContext) {
		this.queryContext = queryContext;
		this.queryCustomizer = queryCustomizer;
		this.termContext = new TermQueryContext(TermQueryContext.Approximation.WILDCARD);
	}

	public TermMatchingContext onField(String field) {
		return new ConnectedTermMatchingContext( termContext, field, queryCustomizer, queryContext);
	}

	public WildcardContext boostedTo(float boost) {
		queryCustomizer.boostedTo( boost );
		return this;
	}

	public WildcardContext constantScore() {
		queryCustomizer.constantScore();
		return this;
	}

	public WildcardContext filter(Filter filter) {
		queryCustomizer.filter(filter);
		return this;
	}
}