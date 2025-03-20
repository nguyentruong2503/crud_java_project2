package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	
	@Autowired 
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
		BuildingDTO building = modelMapper.map(buildingEntity, BuildingDTO.class);

		DistrictEntity districtEntity =  districtRepository.findNameById(buildingEntity.getDistrictid());
		building.setAddress(buildingEntity.getStreet() + "," + buildingEntity.getWard() + "," + districtEntity.getName() );
		List<RentAreaEntity> rentAreas = rentAreaRepository.getValueByBuildingId(buildingEntity.getId());
		String areaResult = rentAreas.stream().map(i -> i.getValue().toString()).collect(Collectors.joining(","));
		building.setRentArea(areaResult);
		
		//Khong dung ModelMapper
//		building.setName(buildingEntity.getName());
//		building.setBrokerageFee(buildingEntity.getBrokerageFee());
//	    building.setEmptyArea(buildingEntity.getEmptyArea());
//	    building.setFloorArea(buildingEntity.getFloorArea());
//	    building.setManagerName(buildingEntity.getManagerName());
//	    building.setManagerPhoneNumber(buildingEntity.getManagerPhoneNumber());
		
		
		return building;
	}
	

}
