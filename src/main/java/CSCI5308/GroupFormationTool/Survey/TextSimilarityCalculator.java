package CSCI5308.GroupFormationTool.Survey;


import java.util.*;


public class TextSimilarityCalculator {
    private static double sumAverage =0;
    private static int itterator =0;

    public double calculateTextSimilarity(ArrayList<String> response)
    {

        for(int outerItterator=0; outerItterator<response.size()-1; outerItterator++) {
            for (int innerItterator = outerItterator+1; innerItterator < response.size(); innerItterator++) {
                printSimilarity(response.get(outerItterator),response.get(innerItterator));
                itterator = itterator +1;

            }
        }
        return (sumAverage / itterator);

    }

    public static double similarityCheck(String firstString, String secondString) {
        String longer = firstString, shorter = secondString;
        if (firstString.length() < secondString.length()) {
            longer = secondString; shorter = firstString;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0;  }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    public static int editDistance(String firstString, String secondString) {
        firstString = firstString.toLowerCase();
        secondString = secondString.toLowerCase();

        int[] costs = new int[secondString.length() + 1];
        for (int i = 0; i <= firstString.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= secondString.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (firstString.charAt(i - 1) != secondString.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[secondString.length()] = lastValue;
        }
        return costs[secondString.length()];
    }

    public static void printSimilarity(String firstString, String secondString) {
        String.format("%.3f is the similarity between \"%s\" and \"%s\"", similarityCheck(firstString, secondString), firstString, secondString, sumAverage = sumAverage + similarityCheck(firstString,secondString));


    }







}

