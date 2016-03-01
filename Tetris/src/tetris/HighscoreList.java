package tetris;

public class HighscoreList {

    private static final HighscoreList INSTANCE = new HighscoreList();
    private List<Highscore>

    private HighscoreList() {

    }

    public static HighscoreList getInstance() {
	return INSTANCE;
    }
}
