package com.pxene.pap.domain.beans;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.Arrays;

/**
 * Created by wangshuai on 2017/8/2.
 * es查询bean类
 */
public class EsQueryBean {

    private String index;   //索引
    private String type;    //索引类型

    private SearchType searchType;//执行检索的类别
    private QueryBuilder queryBuilder;  //查询条件
    private SortBuilder sortBuilder;    //查询排序
    private String[] field;    //查询字段
    private TermsBuilder aggregation;   //聚合条件

    private TimeValue timeout;

    private int from=0;
    private int size=1000; //查询大小

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public SortBuilder getSortBuilder() {
        return sortBuilder;
    }

    public void setSortBuilder(SortBuilder sortBuilder) {
        this.sortBuilder = sortBuilder;
    }

    public String[] getField() {
        return field;
    }

    public void setField(String[] field) {
        this.field = field;
    }

    public TermsBuilder getAggregation() {
        return aggregation;
    }

    public void setAggregation(TermsBuilder aggregation) {
        this.aggregation = aggregation;
    }

    public TimeValue getTimeout() {
        return timeout;
    }

    public void setTimeout(TimeValue timeout) {
        this.timeout = timeout;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "EsQueryBean{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", searchType=" + searchType +
                ", queryBuilder=" + queryBuilder +
                ", sortBuilder=" + sortBuilder +
                ", field=" + Arrays.toString(field) +
                ", aggregation=" + aggregation +
                ", timeout=" + timeout +
                ", from=" + from +
                ", size=" + size +
                '}';
    }
}
