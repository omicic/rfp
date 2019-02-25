package rfp.loyality.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class RfpEventDTO implements Serializable{
	
	private Long id;
	private LocalDate eventDate;
	private String eventCode;
	private RfpLocationDTO rfpLocationDTO;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public RfpLocationDTO getRfpLocationDTO() {
		return rfpLocationDTO;
	}
	public void setRfpLocationDTO(RfpLocationDTO rfpLocationDTO) {
		this.rfpLocationDTO = rfpLocationDTO;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	        RfpEventDTO rfpEventDTO = (RfpEventDTO) o;
	        if(rfpEventDTO.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), rfpEventDTO.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

	    @Override
	    public String toString() {
	        return "RfpEventDTO{" +
	            "id=" + getId() +
	            ", eventDate='" + getEventDate() + "'" +
	            ", eventCode='" + getEventCode() + "'" +
	            "}";
	}

}
