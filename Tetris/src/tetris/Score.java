package tetris;

/**
 * A score. It implements Comparable so that it can
 * be compared to other scores with compareTo().
 */
class Score implements Comparable<Score> {
    // I don't really need the equals method of comparable so I'm not
    // going to implement it. Hope that is ok.
    public final int score;
    public final String name;

    Score(int score, String name) {
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

