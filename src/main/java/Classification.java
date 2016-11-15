import java.util.HashMap;

/**
 * Created by luisgerardoangelsalas on 10/24/16.
 */
public class Classification {
    private String id;
    private Integer frequency;
    public HashMap<String, Integer> wordFrequency;
    private Double prior;
    private int wordCounter;

    public Classification(String classId) {
        id = classId;
        frequency = 0;
        wordCounter = 0;
        wordFrequency = new HashMap<>();
        prior = 0.0;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void increaseFrequency() {
        this.frequency++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrior() {
        return prior;
    }

    public void setPrior(Double prior) {
        this.prior = prior;
    }

    public int getWordCounter() {
        return wordCounter;
    }

    public void setWordCounter(int wordCounter) {
        this.wordCounter = wordCounter;
    }

    public void increaseWordCounter() {
        this.wordCounter += 1;
    }
}
