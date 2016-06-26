package xyz.aungpyaephyo.joketeller.data.vos;

/**
 * Created by aung on 6/24/16.
 */
public class JokeVO {
    private String jokeTitle;
    private String jokeContent;
    private int jokeImage;

    public JokeVO(String jokeTitle, String jokeContent, int jokeImage) {
        this.jokeTitle = jokeTitle;
        this.jokeContent = jokeContent;
        this.jokeImage = jokeImage;
    }

    public String getJokeTitle() {
        return jokeTitle;
    }

    public String getJokeContent() {
        return jokeContent;
    }

    public int getJokeImage() {
        return jokeImage;
    }
}
