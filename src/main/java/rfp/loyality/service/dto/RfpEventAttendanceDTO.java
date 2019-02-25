package rfp.loyality.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class RfpEventAttendanceDTO implements Serializable{

	private Long id;

	private LocalDate attendanceDate;

	private RfpEventDTO rfbEventDTO;

	private UserDTO userDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public RfpEventDTO getRfbEventDTO() {
		return rfbEventDTO;
	}

	public void setRfbEventDTO(RfpEventDTO rfbEventDTO) {
		this.rfbEventDTO = rfbEventDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	        RfpEventAttendanceDTO rfbEventAttendanceDTO = (RfpEventAttendanceDTO) o;
	        if(rfbEventAttendanceDTO.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), rfbEventAttendanceDTO.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

	    @Override
	    public String toString() {
	        return "RfbEventAttendanceDTO{" +
	            "id=" + getId() +
	            ", attendanceDate='" + getAttendanceDate() + "'" +
	            "}";
	}
	
}
