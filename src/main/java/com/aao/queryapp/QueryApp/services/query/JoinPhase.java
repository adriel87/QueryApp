package com.aao.queryapp.QueryApp.services.query;

import com.aao.queryapp.QueryApp.Entities.Models;
import org.springframework.util.ObjectUtils;

import java.util.Set;

public class JoinPhase implements Phase {

    private String JOIN = "JOIN";
    private String ON = "ON";
    private String equalTo = "=";
    private ShareSqlCharacter character = new ShareSqlCharacter();
    private StringBuilder join = new StringBuilder();

    private Set<Models> childrens;

    private String result = "";

    public JoinPhase(Set<Models> childrens) {
        this.childrens = childrens;
    }

    private String addJoin(Models child) {
        Models parent = child.getModels();
        String childModelName = child.getName();
        String childModelAlias = child.getAlias();
        String childModelColumnToJoin = child.getModelJoinColum();
        String parentModelAlias = parent.getAlias();
        String parentColumnToJoin = parent.getTargetJoinColumn();

        StringBuilder newJoin = new StringBuilder();

        return newJoin
                .append(JOIN)
                .append(character.whiteSpace)
                .append(childModelName)
                .append(character.whiteSpace)
                .append(childModelAlias)
                .append(character.whiteSpace)
                .append(ON)
                .append(character.whiteSpace)
                .append(childModelAlias)
                .append(character.point)
                .append(childModelColumnToJoin)
                .append(character.whiteSpace)
                .append(equalTo)
                .append(character.whiteSpace)
                .append(parentModelAlias)
                .append(character.point)
                .append(parentColumnToJoin)
                .append(character.carryReturn)
                .toString();
    }

    @Override
    public String getPhaseResult() {
        return result;
    }

    @Override
    public void makePhase() {
        if (childrens == null) return;
        if (hasChildrenParent()) return;
        childrens
                .iterator()
                .forEachRemaining(child -> join.append(addJoin(child)));

        result = join.toString();
    }

    public boolean hasChildrenParent() {
        return childrens.stream().parallel().anyMatch(child->child.getModels() == null);
    }

    public boolean hasChildren() {
        return childrens != null;
    }
}
