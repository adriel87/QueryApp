package com.aao.queryapp.QueryApp.services.query;

import com.aao.queryapp.QueryApp.Entities.ColumnName;
import com.aao.queryapp.QueryApp.Entities.Filter;

public class Clause {

    private Filter filter;

    private ColumnName columnName;

    private String valueToCompare;
    private String valueB;

    void tal(){
//        ejemplo para construir una clausula
//        String where = "WHERE"; <-- este where estaria en la  WherePhase
//        devolvemos la clausula
//        1. hacer referencia a al valor de la columna
//        las siguientes lineas serian la representacion de este parte del script de sql
//        base_alias.column
        columnName.getModels().getAlias();
        String point= ".";
        columnName.getName();

//      usamos el operador del filtro
        filter.getOperator();
//
    }


}
