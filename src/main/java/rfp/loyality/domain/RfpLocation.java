package rfp.loyality.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RfpLocation.
 */
@Entity
@Table(name = "rfp_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RfpLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "run_day_of_week")
    private Integer runDayOfWeek;

    @OneToMany(mappedBy = "rfpLocation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RfpEvent> rfpEvents = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public RfpLocation locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getRunDayOfWeek() {
        return runDayOfWeek;
    }

    public RfpLocation runDayOfWeek(Integer runDayOfWeek) {
        this.runDayOfWeek = runDayOfWeek;
        return this;
    }

    public void setRunDayOfWeek(Integer runDayOfWeek) {
        this.runDayOfWeek = runDayOfWeek;
    }

    public Set<RfpEvent> getRfpEvents() {
        return rfpEvents;
    }

    public RfpLocation rfpEvents(Set<RfpEvent> rfpEvents) {
        this.rfpEvents = rfpEvents;
        return this;
    }

    public RfpLocation addRfpEvent(RfpEvent rfpEvent) {
        this.rfpEvents.add(rfpEvent);
        rfpEvent.setRfpLocation(this);
        return this;
    }

    public RfpLocation removeRfpEvent(RfpEvent rfpEvent) {
        this.rfpEvents.remove(rfpEvent);
        rfpEvent.setRfpLocation(null);
        return this;
    }

    public void setRfpEvents(Set<RfpEvent> rfpEvents) {
        this.rfpEvents = rfpEvents;
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
        RfpLocation rfpLocation = (RfpLocation) o;
        if (rfpLocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfpLocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfpLocation{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", runDayOfWeek=" + getRunDayOfWeek() +
            "}";
    }
}
