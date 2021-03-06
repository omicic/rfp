package rfp.loyality.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class RfpLocationDTO implements Serializable{

    private Long id;

    private String locationName;

    private Integer runDayOfWeek;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getRunDayOfWeek() {
		return runDayOfWeek;
	}

	public void setRunDayOfWeek(Integer runDayOfWeek) {
		this.runDayOfWeek = runDayOfWeek;
	}

	
	  @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	        RfpLocationDTO rfbLocationDTO = (RfpLocationDTO) o;
	        if(rfbLocationDTO.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), rfbLocationDTO.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

	    @Override
	    public String toString() {
	        return "RfbLocationDTO{" +
	            "id=" + getId() +
	            ", locationName='" + getLocationName() + "'" +
	            ", runDayOfWeek='" + getRunDayOfWeek() + "'" +
	            "}";
	}
	
}
