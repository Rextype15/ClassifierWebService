/**
 * Created by Emmanuel on 11/15/2016.
 */

import javafx.util.Pair;

import java.util.ArrayList;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        ArrayList<Pair<String, String>> train = new ArrayList<>();
        train.add(new Pair<>("Chinese Beijing Chinese", "c"));
        train.add(new Pair<>("Chinese Chinese Shanghai", "c"));
        train.add(new Pair<>("Chinese Macao", "c"));
        train.add(new Pair<>("Tokyo Japan Chinese", "j"));
        train.add(new Pair<>("", ""));

        NaiveBayesClassifier classifier = new NaiveBayesClassifier(train);

        get("/properties", (req, res) -> {


            String inputWord = req.queryParams("word");
            String classification = classifier.classify(inputWord);

            return new ClassifiedObject(inputWord, classification);

        }, new JsonTransformer());



//        type = classifier.classify(doc);

//        System.out.println(String.format("The type of %s is: %s", doc, type));




    }
}

