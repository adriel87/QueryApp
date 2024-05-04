package com.aao.queryapp.QueryApp.services.querys;

import com.aao.queryapp.QueryApp.Entities.Models;
import com.aao.queryapp.QueryApp.services.query.JoinPhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
@DisplayName("Join Phase")
class JoinPhaseTests {

    @Test
    @DisplayName("When children are null or empty return empty string")
    void GiventheChildrensAreEmptyOrNullWhenWeMakeAJoinQueryThenRecieveEmptyString() {

//        Arrange
        JoinPhase joinPhase = new JoinPhase(null);

//        Act
        joinPhase.makePhase();
        String result = joinPhase.getPhaseResult();

//        Assert
        assertEquals("", result);

    }

    @Test
    @DisplayName("When children dont have parent return empty string")
    void GivenAValidChildrenButWithoutParentWhenWeMakeAJoinQueryThenRecieveEmptyString(){
//        Arrange
        String expected = "";
        Models childWithoutParent = new Models();
        childWithoutParent.setModels(null);
        HashSet<Models> childrens = new HashSet<>();
        childrens.add(childWithoutParent);

        JoinPhase joinPhase = new JoinPhase(childrens);

//        Act
        joinPhase.makePhase();
        String result = joinPhase.getPhaseResult();

//        Assert
        assertEquals(expected, result);
    }
    @Test
    @DisplayName("use case when has a basic join query 1 parent and 1 child")
    void GivenAValidChildrenWithParentWhenWeMakeAJoinQueryThenRecieveAJoinConjunction(){
//        Arrange
        String expected = "JOIN child_name child_alias ON child_alias.parent_id = parent_alias.id\n";
        Models child = new Models();
        Models parent = new Models();
        child.setAlias("child_alias");
        child.setName("child_name");
        child.setModelJoinColum("parent_id");
        parent.setAlias("parent_alias");
        parent.setTargetJoinColumn("id");
        child.setModels(parent);
        HashSet<Models> childrens = new HashSet<>();
        childrens.add(child);

        JoinPhase joinPhase = new JoinPhase(childrens);

//        Act
        joinPhase.makePhase();
        String result = joinPhase.getPhaseResult();

//        Assert
        assertEquals(expected, result);
    }

}
