import javafx.util.*;
import java.util.*;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;


public class NaiveBayesClassifier implements Classifier {

    private HashMap<String, Classification> classes;
    private HashMap<Pair<String, String>, Double> frequencyTable;
    private int rowCounter;
    private ArrayList<String> vocabulary;

    public NaiveBayesClassifier(ArrayList<Pair<String, String>> train) {
        classes = new HashMap<>();
        frequencyTable = new HashMap<>();
        vocabulary = new ArrayList<>();
        rowCounter = 0;

        initialize(train);
        fillFrequencyTable();
        System.out.println("Entrenamiento terminado");
    }

    public NaiveBayesClassifier(String inputRoute){

        classes = new HashMap<>();
        frequencyTable = new HashMap<>();
        vocabulary = new ArrayList<>();
        rowCounter = 0;

        ArrayList<Pair<String, String>> train = new ArrayList<>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(inputRoute));
            String[] line;
            while ((line = reader.readNext()) != null) {
                train.add(new Pair<>(line[0], line[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize(train);
        fillFrequencyTable();
        System.out.println("Entrenamiento terminado");
    }


    private void fillFrequencyTable() {

        for (Classification currentClass : classes.values()) {
            // temporary setting Prior
            currentClass.setPrior(((double)currentClass.getFrequency() / (double)rowCounter));

            for (String word: vocabulary) {
                double probability = 0.0;
                Integer wordFrecuency = currentClass.wordFrequency.get(word);
                if (wordFrecuency == null)
                    wordFrecuency = 0;

                probability = (double)(wordFrecuency + 1) / (double)(currentClass.getWordCounter() + vocabulary.size());

                frequencyTable.put(new Pair<>(currentClass.getId(), word), probability);

            }
        }
    }

    private void initialize(ArrayList<Pair<String, String>> train) {
        for(Pair <String, String> row : train) {
            String doc = row.getKey();
            String classId = row.getValue();
            Classification currentClass;
            rowCounter++;

            classes.putIfAbsent(classId, new Classification(classId));

            currentClass = classes.get(classId);
            currentClass.increaseFrequency();

            String[] words = doc.split(" ");

            for (String word: words) {
                if (!vocabulary.contains(word))
                    vocabulary.add(word);

                currentClass.wordFrequency.putIfAbsent(word, 0);
                currentClass.wordFrequency.put(word, currentClass.wordFrequency.get(word)+1);
                currentClass.increaseWordCounter();
            }
        }
    }

    @Override
    public String classify(String doc) {


        String[] words = doc.split(" ");
        HashMap<String, Double> classProbability = new HashMap<>();
        for (Classification currentClass : classes.values()) {
            for (String word: words) {
                Double prob = frequencyTable.get(new Pair<>(currentClass.getId(),word));
                classProbability.putIfAbsent(currentClass.getId(), currentClass.getPrior());
                classProbability.put(currentClass.getId(), classProbability.get(currentClass.getId()) * prob);
            }
        }


        Pair<String, Double> max = new Pair<>("", 0.0);
        for (Map.Entry<String, Double> entry : classProbability.entrySet()) {
            String classId = entry.getKey();
            Double probability = entry.getValue();

            if(probability > max.getValue())
                max = new Pair<>(classId, probability);

        }

        return max.getKey();
    }

}
