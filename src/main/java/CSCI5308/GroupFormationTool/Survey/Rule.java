package CSCI5308.GroupFormationTool.Survey;

public class Rule
{
    RuleType type;
    Integer questionId;
    SurveyQuestionType questionType;
    Double reward;
    Integer idx;

    public Rule()
    {

    }

    public Integer getIdx()
    {
        return idx;
    }

    public void setIdx(Integer idx)
    {
        this.idx = idx;
    }

    public SurveyQuestionType getQuestionType()
    {
        return questionType;
    }

    public void setQuestionType(SurveyQuestionType questionType)
    {
        this.questionType = questionType;
    }

    public RuleType getType()
    {
        return type;
    }

    public void setType(RuleType type)
    {
        this.type = type;
    }

    public Integer getQuestionId()
    {
        return questionId;
    }

    public void setQuestionId(Integer questionId)
    {
        this.questionId = questionId;
    }

    public Double getReward()
    {
        return reward;
    }

    public void setReward(Double reward)
    {
        this.reward = reward;
    }
}
