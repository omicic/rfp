package rfp.loyality.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RfbUser entity.
 */
public class RfpUserDTO implements Serializable {

    private Long id;

    private String userName;

    private RfpLocationDTO rfbLocationDTO;
    
    private Long homeLocationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public RfpLocationDTO getRfbLocationDTO() {
		return rfbLocationDTO;
	}

	public void setRfbLocationDTO(RfpLocationDTO rfbLocationDTO) {
		this.rfbLocationDTO = rfbLocationDTO;
	}

	public Long getHomeLocationId() {
		return homeLocationId;
	}

	public void setHomeLocationId(Long homeLocationId) {
		this.homeLocationId = homeLocationId;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RfpUserDTO rfbUserDTO = (RfpUserDTO) o;
        if(rfbUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfbUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfbUserDTO{" +
            "id=" + getId() +
            ", username='" + getUserName() + "'" +
            "}";
}  

}
