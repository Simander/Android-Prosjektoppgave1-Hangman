package s198739.example.com.hangman;

/**
 * Created by simander on 17/09/15.
 */
public class Highscore {
    private int id;
    private String name;
    private int round;
    private int points;

    public Highscore() {
    }

    public Highscore(String name, int round, int points) {
        this.name = name;
        this.round = round;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
