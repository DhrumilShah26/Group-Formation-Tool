package CSCI5308.GroupFormationTool.SurveyTest;

import CSCI5308.GroupFormationTool.Survey.StandardDeviationCalculator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class StandardDeviationCalculatorTest {

    @Test
    void calculateStandardDeviation() {

        StandardDeviationCalculator standardDeviationCalculator = new StandardDeviationCalculator();

        ArrayList<Double> list = new ArrayList<>();
        list.add((double) 1);
        list.add((double) 2);
        list.add((double) 3);
        list.add((double) 4);

        System.out.println(standardDeviationCalculator.calculateStandardDeviation(list));
    }

}
