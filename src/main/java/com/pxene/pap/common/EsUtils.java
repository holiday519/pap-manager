package com.pxene.pap.common;

import com.pxene.pap.domain.beans.EsQueryBean;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangshuai on 2017/7/31.
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EsUtils {
    private static TransportClient client;

    @Autowired
    private EsUtils(Environment env){
        initClient(env);
    }

    public static void initClient (Environment env){
        String clusterName = env.getProperty("dsp.es.cluster.name");
        String clusterAddress = env.getProperty("dsp.es.cluster.address");
        String port = env.getProperty("dsp.es.port");
        // 通过setting对象指定集群配置信息, 配置的集群名
        Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName) // 设置集群名
                .put("client.transport.sniff", true) // 开启嗅探
                .build();
        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(clusterAddress, Integer.parseInt(port))));

    }

    /**
     * 判断指定的索引名是否存在
     * @param indexName 索引名
     * @return  存在：true; 不存在：false;
     */
    public boolean isExistsIndex(String indexName){
        IndicesExistsResponse response =
                client.admin().indices().exists(
                        new IndicesExistsRequest().indices(new String[]{indexName})).actionGet();
        return response.isExists();
    }

    /**
     * 判断指定的索引的类型是否存在
     * @param indexName 索引名
     * @param indexType 索引类型
     * @return  存在：true; 不存在：false;
     */
    public boolean isExistsType(String indexName,String indexType){
        TypesExistsResponse response =
                client.admin().indices()
                        .typesExists(new TypesExistsRequest(new String[]{indexName}, indexType)
                        ).actionGet();
        return response.isExists();
    }

    /**
     * 查询es
     * @param index
     * @param type
     * @param querySize
     * @param queryBuilder
     * @param queryField
     * @param aggregation
     * @param sortBuilder
     * @return
     */
    public SearchResponse query(String index,String type,int querySize,QueryBuilder queryBuilder,String[] queryField,TermsBuilder aggregation, SortBuilder sortBuilder){


        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if(type != null){
            searchRequestBuilder.setTypes(type);
        }
        searchRequestBuilder.setSize(querySize);
        if(queryBuilder != null) {
            searchRequestBuilder.setQuery(queryBuilder);
        }
        //查询的字段
        if(queryField != null) {
            searchRequestBuilder.addFields(queryField);
        }

        //聚合
        if(aggregation != null) {
            searchRequestBuilder.addAggregation(aggregation);
        }
        //排序
        if(sortBuilder != null) {
            searchRequestBuilder.addSort(sortBuilder);
        }

        //执行查询
        SearchResponse scrollResp = searchRequestBuilder.execute().actionGet();

        return scrollResp;
    }

    public SearchResponse query(EsQueryBean esQueryBean){
        if(esQueryBean == null){
            return null;
        }
        String index = esQueryBean.getIndex();
        String type = esQueryBean.getType();

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);

        if(type != null){
            searchRequestBuilder.setTypes(type);
        }

        if(esQueryBean.getSearchType() != null) {
            searchRequestBuilder.setSearchType(esQueryBean.getSearchType());
        }


        if(esQueryBean.getQueryBuilder() != null) {
            searchRequestBuilder.setQuery(esQueryBean.getQueryBuilder());
        }
        //查询的字段
        if(esQueryBean.getField() != null) {
            searchRequestBuilder.addFields(esQueryBean.getField());
        }

        //聚合
        if(esQueryBean.getAggregation() != null) {
            searchRequestBuilder.addAggregation(esQueryBean.getAggregation());
        }
        //排序
        if(esQueryBean.getSortBuilder() != null) {
            searchRequestBuilder.addSort(esQueryBean.getSortBuilder());
        }

        if(esQueryBean.getTimeout()!= null) {
            searchRequestBuilder.setTimeout(esQueryBean.getTimeout());
        }
        searchRequestBuilder.setFrom(esQueryBean.getFrom());
        searchRequestBuilder.setSize(esQueryBean.getSize());

        //执行查询
        SearchResponse scrollResp = searchRequestBuilder.execute().actionGet();

        return scrollResp;
    }
}
