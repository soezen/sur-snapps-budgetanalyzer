package sur.snapps.budgetanalyzer.domain.event;

import sur.snapps.budgetanalyzer.domain.BaseEntity;
import sur.snapps.budgetanalyzer.domain.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * User: SUR
 * Date: 14/06/14
 * Time: 8:35
 */
@Entity
@Table(name = "EVENTS")
public class Event extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(nullable = false)
    private Date tms;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = true, name = "SUBJECT_ID")
    private Integer subjectId;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Date getTms() {
        return tms;
    }

    public void setTms(Date tms) {
        this.tms = tms;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String getDisplayValue() {
        return "";
    }
}
