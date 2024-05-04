package com.aao.queryapp.QueryApp.services.query;

public class Query implements QueryI {

    private SelectPhase selectPhase;
    private JoinPhase joinPhase;
    private final ShareSqlCharacter character = new ShareSqlCharacter();
    private StringBuilder query = new StringBuilder();

    public Query(SelectPhase selectPhase) {
        this.selectPhase = selectPhase;
    }

    public Query(SelectPhase selectPhase, JoinPhase joinPhase) {
        this.selectPhase = selectPhase;
        this.joinPhase = joinPhase;
    }

    @Override
    public String createQuery() {

        if (joinPhase != null) {
            selectPhase.makePhase();
            joinPhase.makePhase();

            return query
                    .append(selectPhase.getPhaseResult())
                    .append(joinPhase.getPhaseResult())
                    .append(character.closeQuery)
                    .toString();

        } else {
            selectPhase.makePhase();
            return query
                    .append(selectPhase.getPhaseResult())
                    .append(character.closeQuery)
                    .toString();
        }
    }

}
