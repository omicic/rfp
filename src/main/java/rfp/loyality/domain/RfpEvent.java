package rfp.loyality.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RfpEvent.
 */
@Entity
@Table(name = "rfp_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RfpEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_code")
    private String eventCode;

    @OneToMany(mappedBy = "rfpEvent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RfpEventAttendence> rfpEventAttendances = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("rfpEvents")
    private RfpLocation rfpLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public RfpEvent eventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventCode() {
        return eventCode;
    }

    public RfpEvent eventCode(String eventCode) {
        this.eventCode = eventCode;
        return this;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Set<RfpEventAttendence> getRfpEventAttendances() {
        return rfpEventAttendances;
    }

    public RfpEvent rfpEventAttendances(Set<RfpEventAttendence> rfpEventAttendences) {
        this.rfpEventAttendances = rfpEventAttendences;
        return this;
    }

    public RfpEvent addRfpEventAttendance(RfpEventAttendence rfpEventAttendence) {
        this.rfpEventAttendances.add(rfpEventAttendence);
        rfpEventAttendence.setRfpEvent(this);
        return this;
    }

    public RfpEvent removeRfpEventAttendance(RfpEventAttendence rfpEventAttendence) {
        this.rfpEventAttendances.remove(rfpEventAttendence);
        rfpEventAttendence.setRfpEvent(null);
        return this;
    }

    public void setRfpEventAttendances(Set<RfpEventAttendence> rfpEventAttendences) {
        this.rfpEventAttendances = rfpEventAttendences;
    }

    public RfpLocation getRfpLocation() {
        return rfpLocation;
    }

    public RfpEvent rfpLocation(RfpLocation rfpLocation) {
        this.rfpLocation = rfpLocation;
        return this;
    }

    public void setRfpLocation(RfpLocation rfpLocation) {
        this.rfpLocation = rfpLocation;
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
        RfpEvent rfpEvent = (RfpEvent) o;
        if (rfpEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfpEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfpEvent{" +
            "id=" + getId() +
            ", eventDate='" + getEventDate() + "'" +
            ", eventCode='" + getEventCode() + "'" +
            "}";
    }
}
