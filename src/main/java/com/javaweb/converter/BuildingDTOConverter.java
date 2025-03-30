package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
		BuildingDTO building = modelMapper.map(buildingEntity, BuildingDTO.class);
		building.setAddress(buildingEntity.getStreet() + "," + buildingEntity.getWard() + "," + buildingEntity.getDistrict().getName() );
		List<RentAreaEntity> rentAreas = buildingEntity.getItem() ;
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
