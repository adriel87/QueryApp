package com.aao.queryapp.QueryApp.services.query;

import com.aao.queryapp.QueryApp.Entities.ColumnName;
import com.aao.queryapp.QueryApp.Entities.Models;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Getter
@Setter
public class SelectPhase implements Phase {

    private Models parentModel;
    private Set<Models> childrenModels;

    private Set<ColumnName> columns;

    private String result;
    private StringBuilder select = new StringBuilder();
    private ShareSqlCharacter character = new ShareSqlCharacter();
    private String SELECT = "SELECT";
    private String ALL_COLUMNS = "*";
    private String FROM = "FROM";

    public SelectPhase(Models parentModel) {
        this.parentModel = parentModel;
    }

    public SelectPhase(Models parentModel, Set<Models> childrenModels, Set<ColumnName> columns) {
        this.parentModel = parentModel;
        this.childrenModels = childrenModels;
        this.columns = columns;
    }

    private String selectBeforeColumns() {
        StringBuilder selectBeforeColumns = new StringBuilder();
        return selectBeforeColumns
                .append(SELECT)
                .append(character.whiteSpace)
                .toString();
    }

    public String selectAddColumns() {
        StringBuilder columnsPhaseString = new StringBuilder();
        if (hasColumns()) {
            columns
                    .iterator()
                    .forEachRemaining(columnName -> {
                        String alias = columnName.getModels().getAlias();
                        String name = columnName.getName();
                        columnsPhaseString
                                .append(alias)
                                .append(character.point)
                                .append(name)
                                .append(character.colon);

                    });

//            remove last colon
            columnsPhaseString.deleteCharAt(columnsPhaseString.lastIndexOf(character.colon));
        } else {
            columnsPhaseString.append(ALL_COLUMNS);
        }

        columnsPhaseString.append(character.whiteSpace);


        return columnsPhaseString.toString();
    }

    private String selectAfterColumns() {
        String alias = parentModel.getAlias();
        String baseModel = parentModel.getName();
        StringBuilder selectAfterColumns = new StringBuilder();
        return selectAfterColumns
                .append(character.whiteSpace)
                .append(FROM)
                .append(character.whiteSpace)
                .append(baseModel)
                .append(character.whiteSpace)
                .append(alias)
                .toString();
    }

    private String selectEnd() {
        return hasChildren() ? character.carryReturn : character.whiteSpace;
    }

    private boolean hasChildren() {
        return !ObjectUtils.isEmpty(childrenModels);
    }


    private boolean hasColumns() {
        return !ObjectUtils.isEmpty(columns);
    }

    @Override
    public String getPhaseResult() {
        return result;
    }

    @Override
    public void makePhase() {
        result = select
                .append(selectBeforeColumns())
                .append(selectAddColumns())
                .append(selectAfterColumns())
                .append(selectEnd())
                .toString();
    }
}
