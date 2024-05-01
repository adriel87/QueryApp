package com.aao.queryapp.QueryApp.services.query;
import java.util.*;

import com.aao.queryapp.QueryApp.Entities.Models;

public class Query implements QueryI {


    private StringBuilder query = new StringBuilder();
    private Models parentModel;
    private ArrayList<Models> childrenModels;
    private String SELECT_FROM = "SELECT * FROM";
    private String carryReturn = "\n";
    private String closeQuery = ";"; 
    private String JOIN = "JOIN";
    private String ON = "ON";
    private String equalTo = "=";
    private String point = ".";
    private String whiteSpace = " ";

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
                .append(closeQuery)
                .toString();
    }

    @Override
    public String createQuery(Models parentModel) {
        return query
                .append(selectPhase(parentModel))
                .append(closeQuery)
                .toString();
    }

    @Override
    public String createQuery(Models parentModel, Collection<Models> childrenModels) {
        query
            .append(selectPhase(parentModel))
            .append(carryReturn);

        childrenModels.forEach(joinModel -> {
            query.append(joinPhase(parentModel, joinModel));
        });

        query
            .append(closeQuery);

        return query.toString();
    }

    @Override
    public String selectPhase(Models parentModel) {
       
        StringBuilder select = new StringBuilder();
        String alias = parentModel.getAlias();
        String baseModel = parentModel.getName();

        select
            .append(SELECT_FROM)
            .append(whiteSpace)
            .append(baseModel);

        if (alias != null) {
            select
            .append(whiteSpace)
            .append(alias);
        }

        select.append(carryReturn);

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
            .append(whiteSpace)
            .append(childModelName)
            .append(whiteSpace)
            .append(childModelAlias)
            .append(whiteSpace)
            .append(ON)
            .append(whiteSpace)
            .append(childModelAlias)
            .append(point)
            .append(childModelColumnToJoin)
            .append(whiteSpace)
            .append(equalTo)
            .append(whiteSpace)
            .append(parentModelAlias)
            .append(point)
            .append(parentColumnToJoin);

        return join.toString();
    }

    private boolean hasChildrenModels (){
        return !childrenModels.isEmpty();
    }
    
}
