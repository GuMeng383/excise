package com.example.excise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VotingService {
    public List<Player> findMaxVotePlayers(List<Player> players,Map<Player,Integer> votes){
            List<Player> result = new ArrayList<>();
            int totalVote = 0;
            for(Player player :players){
                if(player.isAlive()){
                    totalVote += votes.getOrDefault(player, 0);
                }
            }
            if(totalVote==0){
                return result;
            }
            int  maxVoteCount = -1;
            for(Player player:players){
                if(!player.isAlive()){
                    continue;
                }
                int voteCount = votes.getOrDefault(player,0);
                if(voteCount > maxVoteCount){
                    maxVoteCount = voteCount;
                }
            }
            for(Player player : players){
                if(!player.isAlive()){
                    continue;
                }
                if(votes.getOrDefault(player, 0) == maxVoteCount){
                    result.add(player);
                }
            }
            return result;
    }
}
