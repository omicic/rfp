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
 * A RfpUser.
 */
@Entity
@Table(name = "rfp_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RfpUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @OneToOne    @JoinColumn(unique = true)
    private RfpLocation location;

    @OneToMany(mappedBy = "rfpUser")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RfpEventAttendance> rfpEventAttendances = new HashSet<>();
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public RfpUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RfpLocation getLocation() {
        return location;
    }

    public RfpUser location(RfpLocation rfpLocation) {
        this.location = rfpLocation;
        return this;
    }

    public void setLocation(RfpLocation rfpLocation) {
        this.location = rfpLocation;
    }

    public Set<RfpEventAttendance> getRfpEventAttendances() {
        return rfpEventAttendances;
    }

    public RfpUser rfpEventAttendances(Set<RfpEventAttendance> rfpEventAttendences) {
        this.rfpEventAttendances = rfpEventAttendences;
        return this;
    }

    public RfpUser addRfpEventAttendance(RfpEventAttendance rfpEventAttendence) {
        this.rfpEventAttendances.add(rfpEventAttendence);
        rfpEventAttendence.setRfpUser(this);
        return this;
    }

    public RfpUser removeRfpEventAttendance(RfpEventAttendance rfpEventAttendence) {
        this.rfpEventAttendances.remove(rfpEventAttendence);
        rfpEventAttendence.setRfpUser(null);
        return this;
    }

    public void setRfpEventAttendances(Set<RfpEventAttendance> rfpEventAttendences) {
        this.rfpEventAttendances = rfpEventAttendences;
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
        RfpUser rfpUser = (RfpUser) o;
        if (rfpUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfpUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfpUser{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
