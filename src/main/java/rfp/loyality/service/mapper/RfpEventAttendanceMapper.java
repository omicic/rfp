package rfp.loyality.service.mapper;

import org.mapstruct.Mapping;

import rfp.loyality.domain.RfpEventAttendance;
import rfp.loyality.service.dto.RfpEventAttendanceDTO;

public interface RfpEventAttendanceMapper extends EntityMapper <RfpEventAttendanceDTO, RfpEventAttendance>{

	@Mapping(source = "rfpEvent", target = "rfpEventDTO")
	@Mapping(source = "user", target = "userDTO")
	RfpEventAttendanceDTO toDto(RfpEventAttendance rfpEventAttendance);
	
    @Mapping(source = "rfpEventDTO", target = "rfpEvent")
    @Mapping(source = "userDTO", target = "user")
    RfpEventAttendance toEntity(RfpEventAttendanceDTO rfpEventAttendanceDTO);
    
    default RfpEventAttendance fromId(Long id) {
        if (id == null) {
            return null;
        }
        RfpEventAttendance rfpEventAttendance = new RfpEventAttendance();
        rfpEventAttendance.setId(id);
        return rfpEventAttendance;
}
	
}
