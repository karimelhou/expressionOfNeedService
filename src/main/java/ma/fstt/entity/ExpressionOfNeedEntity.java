package ma.fstt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "expressionofneed")
public class ExpressionOfNeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long expressionOfNeedId;

    private String description;

    private Boolean urgence;
    private Integer userId;

    public ExpressionOfNeedEntity() {
    }

    public ExpressionOfNeedEntity(Long expressionOfNeedId, String description, Boolean urgence, Integer userId) {
        this.expressionOfNeedId = expressionOfNeedId;
        this.description = description;
        this.urgence = urgence;
        this.userId = userId;
    }

    public Long getExpressionOfNeedId() {
        return expressionOfNeedId;
    }

    public void setExpressionOfNeedId(Long expressionOfNeedId) {
        this.expressionOfNeedId = expressionOfNeedId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUrgence() {
        return urgence;
    }

    public void setUrgence(Boolean urgence) {
        this.urgence = urgence;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
