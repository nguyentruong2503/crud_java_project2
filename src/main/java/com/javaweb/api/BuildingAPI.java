package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

@RestController
@PropertySource("classpath:application.properties")
@Transactional
public class BuildingAPI {
	
	@Autowired
	private BuildingService buildingService;
	
	@Value("${nguyen.truong}")
	private String data;
	
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(value="/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode ) {
		List<BuildingDTO> result = buildingService.findAll(params,typeCode);
 
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
    
    @DeleteMapping(value="/building/{id}")
    public void deleteBuilding(@PathVariable Long id) {
    	BuildingEntity buildEntity = entityManager.find(BuildingEntity.class, id);
    	entityManager.remove(buildEntity);
    	System.out.println("delete success");
    }
    
    @PostMapping(value="/building/")
    public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
    	BuildingEntity buildEntity = new BuildingEntity();
    	buildEntity.setName(buildingRequestDTO.getName());
    	buildEntity.setStreet(buildingRequestDTO.getStreet());
    	buildEntity.setWard(buildingRequestDTO.getWard());

    	DistrictEntity districtEntity = new DistrictEntity();
    	districtEntity.setId(buildingRequestDTO.getDistrictid());
    	buildEntity.setDistrict(districtEntity);
    	entityManager.persist(buildEntity);
    	System.out.println("insert success");
    }
    
    @PutMapping(value="/building/")
    public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
    	BuildingEntity buildEntity = new BuildingEntity();
    	buildEntity.setId(4L);
    	buildEntity.setName(buildingRequestDTO.getName());
    	buildEntity.setStreet(buildingRequestDTO.getStreet());
    	buildEntity.setWard(buildingRequestDTO.getWard());

    	DistrictEntity districtEntity = new DistrictEntity();
    	districtEntity.setId(buildingRequestDTO.getDistrictid());
    	buildEntity.setDistrict(districtEntity);
    	entityManager.merge(buildEntity);
    	System.out.println("update success");
    }

}

