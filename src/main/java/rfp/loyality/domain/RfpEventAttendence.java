package rfp.loyality.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RfpEventAttendence.
 */
@Entity
@Table(name = "rfp_event_attendence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RfpEventAttendence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendence_date")
    private String attendenceDate;

    @ManyToOne
    @JsonIgnoreProperties("rfpEventAttendances")
    private RfpUser rfpUser;

    @ManyToOne
    @JsonIgnoreProperties("rfpEventAttendances")
    private RfpEvent rfpEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendenceDate() {
        return attendenceDate;
    }

    public RfpEventAttendence attendenceDate(String attendenceDate) {
        this.attendenceDate = attendenceDate;
        return this;
    }

    public void setAttendenceDate(String attendenceDate) {
        this.attendenceDate = attendenceDate;
    }

    public RfpUser getRfpUser() {
        return rfpUser;
    }

    public RfpEventAttendence rfpUser(RfpUser rfpUser) {
        this.rfpUser = rfpUser;
        return this;
    }

    public void setRfpUser(RfpUser rfpUser) {
        this.rfpUser = rfpUser;
    }

    public RfpEvent getRfpEvent() {
        return rfpEvent;
    }

    public RfpEventAttendence rfpEvent(RfpEvent rfpEvent) {
        this.rfpEvent = rfpEvent;
        return this;
    }

    public void setRfpEvent(RfpEvent rfpEvent) {
        this.rfpEvent = rfpEvent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RfpEventAttendence rfpEventAttendence = (RfpEventAttendence) o;
        if (rfpEventAttendence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfpEventAttendence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfpEventAttendence{" +
            "id=" + getId() +
            ", attendenceDate='" + getAttendenceDate() + "'" +
            "}";
    }
}
