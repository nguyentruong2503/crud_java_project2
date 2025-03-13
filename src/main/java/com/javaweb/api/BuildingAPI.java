package com.javaweb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	
	@Autowired
	private BuildingService buildingService;
	
	@GetMapping(value="/building/")
	public List<BuildingDTO> getBuilding(@RequestParam(name = "name", required = false) String name,
											@RequestParam(name = "districtid", required = false) Long districtid ,
												@RequestParam(name = "typeCode", required = false) List<String> typeCode ) {
		List<BuildingDTO> result = buildingService.findAll(name,districtid);
 
	    return result;
	}

//	public void validate(BuildingDTO buildingDTO)  {
//	    if(buildingDTO.getName() == null || buildingDTO.getName().equals("") || buildingDTO.getNumberOfBasement() == null) {
//	        throw new FieldRequiredException("name or numberOfBasement is null");
//	    }
//	}

    
//    @PostMapping(value="/building/")
//    public BuildingDTO getBuilding2(@RequestBody BuildingDTO buildingDTO){
//    	return buildingDTO;
//    }
    
    @DeleteMapping(value="/api/building/{id}")
    public void deleteBuilding(@PathVariable Integer id) {
        System.out.print("Da xoa toa nha co id la " + id + " roi nhe!");
    }

}

