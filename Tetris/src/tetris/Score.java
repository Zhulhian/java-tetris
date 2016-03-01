package tetris;

public class Score implements Comparable<Score> {
    int score;
    String name;

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public int compareTo(Score other) {
        // If this.score is less than other.score then return -1. Else if
        // this.score is greater than other.score then return 1.
        // If this.score is equal to other.score then return 0;
        // This is used later when sorting scores with Collections.sort(scores)
        return score < other.score ? -1 : score > other.score ? 1 : 0;
    }
}

