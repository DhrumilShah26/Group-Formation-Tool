package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;


public class StandardDeviationCalculator {

    public double calculateStandardDeviation(ArrayList<Double> numbers)
    {

        double sum = 0.0, standardDeviation = 0.0;
        int length = numbers.size();

        for(double num : numbers) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numbers) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);


    }



}
