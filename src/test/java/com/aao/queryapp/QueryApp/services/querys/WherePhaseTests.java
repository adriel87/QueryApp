package com.aao.queryapp.QueryApp.services.querys;

import com.aao.queryapp.QueryApp.Entities.Models;
import com.aao.queryapp.QueryApp.services.query.JoinPhase;
import com.aao.queryapp.QueryApp.services.query.WherePhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Join Phase")
class WherePhaseTests {

    @Test
    @DisplayName("Given a predicate empty or null When we make makePhase Then recieve an empty string")
    void GivenAPredicateEmptyOrNullWhenWeMakeMakePhaseThenRecieveAnEmptyString() {

//        Arrange
//        WherePhase wherePhase = new WherePhase(null);
//
////        Act
//        wherePhase.makePhase();
//        String result = wherePhase.getPhaseResult();
//
////        Assert
//        assertEquals("", result);

    }

}
