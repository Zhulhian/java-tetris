package tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HighscoreList {

    private static final HighscoreList INSTANCE = new HighscoreList();
    private List<Score> highscoreList;

    private HighscoreList() {
        highscoreList = new ArrayList<>();
    }

    public static HighscoreList getInstance() {
	return INSTANCE;
    }

    public void addHighscore(String name, int score) {
        highscoreList.add(new Score(score, name));
    }

    public Iterable<Score> getHighscore() {
        return highscoreList;
    }

    public void sort() {
        Collections.sort(highscoreList);
    }
}
