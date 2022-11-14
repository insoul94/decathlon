package com.test.decathlon.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Decathlon implements Serializable {

    @XmlElement(name = "score_board")
    private List<ScoreBoard> scoreBoardList;

    private Decathlon() {}

    /**
     * @param scoreBoardList must not be <code>null</code>; must not be empty
     */
    public Decathlon(List<ScoreBoard> scoreBoardList) {
        if (scoreBoardList == null || scoreBoardList.size() == 0) {
            throw new IllegalArgumentException("Decathlon must have at least 1 score board.");
        }
        this.scoreBoardList = scoreBoardList;
    }

    public List<ScoreBoard> getScoreBoardList() {
        return scoreBoardList;
    }
}
