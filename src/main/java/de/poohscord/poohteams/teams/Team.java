package de.poohscord.poohteams.teams;

import java.util.List;

public interface Team {

    int getId();

    String getName();

    String getTag();

    int getLevel();

    int getHoney();

    int getOwnerId();

    List<Integer> getMemberIds();

    String getGradientFromColor();

    String getGradientToColor();

}
