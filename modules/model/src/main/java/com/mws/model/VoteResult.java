package com.mws.model;

import javax.persistence.*;

/**
 * Created by ranfi on 3/7/16.
 */

@Table(name = "vote_result")
@Entity
public class VoteResult extends IdEntity<Integer> {

	private static final long serialVersionUID = 1L;
	
	private Integer subjectId;
    private String subject;
    private Integer value;

    private Vote vote;


    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", referencedColumnName = "id")
    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
