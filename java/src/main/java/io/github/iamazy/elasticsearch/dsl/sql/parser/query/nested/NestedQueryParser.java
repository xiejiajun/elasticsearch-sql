package io.github.iamazy.elasticsearch.dsl.sql.parser.query.nested;

import io.github.iamazy.elasticsearch.dsl.antlr4.ElasticsearchParser;
import io.github.iamazy.elasticsearch.dsl.sql.model.AtomicQuery;
import io.github.iamazy.elasticsearch.dsl.sql.parser.BoolExpressionParser;
import io.github.iamazy.elasticsearch.dsl.sql.parser.ExpressionQueryParser;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author iamazy
 * @date 2019/8/2
 * @descrition
 **/
public class NestedQueryParser implements ExpressionQueryParser<ElasticsearchParser.NestedContext> {

    private static BoolExpressionParser boolExpressionParser;

    @Override
    public AtomicQuery parse(ElasticsearchParser.NestedContext expression) {
        String nestedPath=expression.nestedClause().nestedPath.getText();
        if(boolExpressionParser==null){
            boolExpressionParser=new BoolExpressionParser();
        }
        BoolQueryBuilder boolQueryBuilder = boolExpressionParser.parseBoolQueryExpr(expression.nestedClause().query);
        NestedQueryBuilder queryBuilder= QueryBuilders.nestedQuery(nestedPath,boolQueryBuilder, ScoreMode.Avg);
        return new AtomicQuery(queryBuilder);
    }

    @Override
    public boolean isMatchExpressionInvocation(Class clazz) {
        return ElasticsearchParser.NestedContext.class==clazz;
    }
}
