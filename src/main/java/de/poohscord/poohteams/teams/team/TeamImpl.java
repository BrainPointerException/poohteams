package de.poohscord.poohteams.teams.team;

import de.poohscord.poohteams.teams.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamImpl implements Team {

    private final int id = 0;
    private final String name, tag, gradientFromColor, gradientToColor;
    private int level = 0, honey = 0;
    private final int ownerId;
    private final List<Integer> memberIds = new ArrayList<>();

    public TeamImpl(String name, String tag, String gradientFromColor, String gradientToColor, int ownerId) {
        this.name = name;
        this.tag = tag;
        this.gradientFromColor = gradientFromColor;
        this.gradientToColor = gradientToColor;
        this.ownerId = ownerId;
        this.memberIds.add(ownerId);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getHoney() {
        return 0;
    }

    @Override
    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public List<Integer> getMemberIds() {
        return memberIds;
    }

    @Override
    public String getGradientFromColor() {
        return gradientFromColor;
    }

    @Override
    public String getGradientToColor() {
        return gradientToColor;
    }
}
