/**
 * Created by Emmanuel on 11/15/2016.
 */

import javafx.util.Pair;

import java.util.ArrayList;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        NaiveBayesClassifier classifier = new NaiveBayesClassifier("dataset.csv");

        get("/properties", (req, res) -> {


            String inputWord = req.queryParams("word");
            String classification = classifier.classify(inputWord);

            return new ClassifiedObject(inputWord, classification);

        }, new JsonTransformer());



//        type = classifier.classify(doc);

//        System.out.println(String.format("The type of %s is: %s", doc, type));




    }
}

