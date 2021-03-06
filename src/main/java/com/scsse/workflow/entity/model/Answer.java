package com.scsse.workflow.entity.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "answer")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 相关问题id
     */
    @ManyToOne
	@JoinColumn(name="question_id")
    private Question question;

    /**
     * 回答内容
     */
    @Column
    private String content;

    @Column
    private Boolean correct;

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", correct=" + correct +
                '}';
    }
}