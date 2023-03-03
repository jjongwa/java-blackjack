package domain.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import domain.participant.Dealer;
import domain.participant.Player;
import domain.participant.Players;

public class ResultCalculator {

    private final Map<String, List<Integer>> results;

    public ResultCalculator(Players players, Dealer dealer) {
        this.results = new LinkedHashMap<>();
        results.put(dealer.getName(), new ArrayList<>(List.of(0, 0, 0)));
        for (Player player : players.getPlayers()) {
            results.put(player.getName(), new ArrayList<>(List.of(0, 0, 0)));
        }
    }

    public void executeGame(Players players, Dealer dealer) {
        for (Player player : players.getPlayers()) {
            fight(player, dealer);
        }
    }

    public void fight(Player player, Dealer dealer) {
        int playerTotalValue = checkBust(player.getMaxSum());
        int dealerTotalValue = checkBust(dealer.getMaxSum());
        List<Integer> playerResult = results.get(player.getName());
        List<Integer> dealerResult = results.get(dealer.getName());
        if (playerTotalValue > dealerTotalValue) {
            playerWin(playerResult, dealerResult);
        }
        if (playerTotalValue < dealerTotalValue) {
            dealerWin(playerResult, dealerResult);
        }
        if (playerTotalValue == dealerTotalValue) {
            draw(playerResult, dealerResult);
        }
    }

    private void playerWin(List<Integer> playerResult, List<Integer> dealerResult) {
        playerResult.set(0, playerResult.get(0) + 1);
        dealerResult.set(2, dealerResult.get(2) + 1);
    }

    private void dealerWin(List<Integer> playerResult, List<Integer> dealerResult) {
        playerResult.set(2, playerResult.get(2) + 1);
        dealerResult.set(0, dealerResult.get(0) + 1);
    }

    private void draw(List<Integer> playerResult, List<Integer> dealerResult) {
        playerResult.set(1, playerResult.get(1) + 1);
        dealerResult.set(1, dealerResult.get(1) + 1);
    }

    private int checkBust(int totalValue) {
        if (totalValue > 21) {
            totalValue = 0;
        }
        return totalValue;
    }

    public List<String> getFinalFightResults() {
        List<String> finalFightResults = new ArrayList<>();
        for (String name : results.keySet()) {
            List<Integer> participantResult = results.get(name);
            StringBuilder sb = getResultCount(name, participantResult);
            finalFightResults.add(sb.toString());
        }
        return finalFightResults;
    }

    private StringBuilder getResultCount(String name, List<Integer> participantResult) {
        StringBuilder sb = new StringBuilder(name + ": ");
        if (participantResult.get(0) != 0) {
            sb.append(participantResult.get(0)).append("승 ");
        }
        if (participantResult.get(1) != 0) {
            sb.append(participantResult.get(1)).append("무 ");
        }
        if (participantResult.get(2) != 0) {
            sb.append(participantResult.get(2)).append("패");
        }
        return sb;
    }


    public List<Integer> getResultsByName(String name) {
        return results.get(name);
    }
}
