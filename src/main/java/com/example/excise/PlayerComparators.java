package com.example.excise;

import java.util.Comparator;

public final  class PlayerComparators {
    private PlayerComparators(){

    }
    public static final Comparator<Player> BY_SCORE_THEN_ID =
            Comparator.comparingInt(Player::getScore)
                    .reversed()
                    .thenComparingInt(Player::getID);
}
