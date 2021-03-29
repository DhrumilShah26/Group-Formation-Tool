package CSCI5308.GroupFormationTool.SurveyTest;

import CSCI5308.GroupFormationTool.Survey.TextSimilarityCalculator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TextSimilarityCalculatorTest {
    @Test
    void calculateTextSimilarity() {
        TextSimilarityCalculator obj = new TextSimilarityCalculator();

        ArrayList<String> list = new ArrayList<>();
        list.add("The roses are black");
        list.add("The roses are red");
        list.add("C");
        list.add("D");

        System.out.println(obj.calculateTextSimilarity(list));
    }
}
