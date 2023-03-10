package domain.result;

import domain.participant.Money;
import domain.participant.Participant;
import java.util.HashMap;
import java.util.Map;

public class BettingResults {
    private final Map<Participant, Money> bettingResults;

    public BettingResults() {
        this.bettingResults = new HashMap<>();
    }

    public void initParticipantBet(Participant participant, Money money) {
        bettingResults.put(participant, money);
    }

    public Money getParticipantBet(Participant participant) {
        return bettingResults.get(participant);
    }
}
