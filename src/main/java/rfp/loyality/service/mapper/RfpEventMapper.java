package rfp.loyality.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import rfp.loyality.domain.RfpEvent;
import rfp.loyality.service.dto.RfpEventDTO;

@Mapper(componentModel = "spring", uses = {RfpLocationMapper.class})
public interface RfpEventMapper extends EntityMapper <RfpEventDTO, RfpEvent>{

	@Mapping(source = "rfpLocation", target = "rfpLocationDTO")
	RfpEventDTO toDto(RfpEvent rfpEvent);
	
	@Mapping(source = "rfpLocationDTO", target = "rfpLocation")
	@Mapping(target = "rfpEventAttendances", ignore = true)
	RfpEvent toEntity(RfpEventDTO rfpEventDTO);
	
	default RfpEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        RfpEvent rfbEvent = new RfpEvent();
        rfbEvent.setId(id);
        return rfbEvent;
}
	
	
}
