package com.aao.queryapp.QueryApp.services.query;
import java.util.*;

import com.aao.queryapp.QueryApp.Entities.Models;

public class Query implements QueryI {


    private final ShareSqlCharacter shareSqlCharacter = new ShareSqlCharacter();
    private StringBuilder query = new StringBuilder();
    private Models parentModel;
    private ArrayList<Models> childrenModels;
    private String SELECT_FROM = "SELECT * FROM";
    private String JOIN = "JOIN";
    private String ON = "ON";
    private String equalTo = "=";

    public Query(Models parentModel){
        this.parentModel = parentModel;
    }
    public Query(Models parentModel, ArrayList<Models> childrenModels){
        this.parentModel = parentModel;
        this.childrenModels = childrenModels;
    }

    @Override
    public String createQuery() {

        if (hasChildrenModels()) {
            return createQuery(parentModel, childrenModels);
        } else {
            createQuery(parentModel);
        }

        return query
                .append(selectPhase(parentModel))
                .append(shareSqlCharacter.closeQuery)
                .toString();
    }

    @Override
    public String createQuery(Models parentModel) {
        return query
                .append(selectPhase(parentModel))
                .append(shareSqlCharacter.closeQuery)
                .toString();
    }

    @Override
    public String createQuery(Models parentModel, Collection<Models> childrenModels) {
        query
            .append(selectPhase(parentModel))
            .append(shareSqlCharacter.carryReturn);

        childrenModels.forEach(joinModel -> {
            query.append(joinPhase(parentModel, joinModel));
        });

        query
            .append(shareSqlCharacter.closeQuery);

        return query.toString();
    }

    @Override
    public String selectPhase(Models parentModel) {
       
        StringBuilder select = new StringBuilder();
        String alias = parentModel.getAlias();
        String baseModel = parentModel.getName();

        select
            .append(SELECT_FROM)
            .append(shareSqlCharacter.whiteSpace)
            .append(baseModel);

        if (alias != null) {
            select
            .append(shareSqlCharacter.whiteSpace)
            .append(alias);
        }

        select.append(shareSqlCharacter.carryReturn);

        return select.toString();
    }

    @Override
    public String joinPhase(Models parentModel, Models childModel) {
        String childModelName = childModel.getName();
        String childModelAlias = childModel.getAlias();
        String childModelColumnToJoin = childModel.getModelJoinColum();
        String parentModelAlias = parentModel.getAlias();
        String parentColumnToJoin = parentModel.getTargetJoinColumn();

        StringBuilder join = new StringBuilder();

        join
            .append(JOIN)
            .append(shareSqlCharacter.whiteSpace)
            .append(childModelName)
            .append(shareSqlCharacter.whiteSpace)
            .append(childModelAlias)
            .append(shareSqlCharacter.whiteSpace)
            .append(ON)
            .append(shareSqlCharacter.whiteSpace)
            .append(childModelAlias)
            .append(shareSqlCharacter.point)
            .append(childModelColumnToJoin)
            .append(shareSqlCharacter.whiteSpace)
            .append(equalTo)
            .append(shareSqlCharacter.whiteSpace)
            .append(parentModelAlias)
            .append(shareSqlCharacter.point)
            .append(parentColumnToJoin);

        return join.toString();
    }

    private boolean hasChildrenModels (){
        return !childrenModels.isEmpty();
    }
    
}
