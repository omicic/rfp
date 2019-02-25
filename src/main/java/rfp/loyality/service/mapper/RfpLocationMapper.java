package rfp.loyality.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import rfp.loyality.domain.RfpLocation;
import rfp.loyality.service.dto.RfpLocationDTO;

@Mapper(componentModel = "spring", uses = {})
public interface RfpLocationMapper extends EntityMapper<RfpLocationDTO, RfpLocation> {
	
	@Mapping(target = "rfpEvents", ignore = true)
	RfpLocation toEntity(RfpLocationDTO rfpLocationDTO);
	
    default RfpLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        RfpLocation rfbLocation = new RfpLocation();
        rfbLocation.setId(id);
        return rfbLocation;
}

}
