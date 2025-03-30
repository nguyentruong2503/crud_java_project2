package com.javaweb.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "rentarea")
public class RentAreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;


    @ManyToOne
	@JoinColumn(name = "buildingid")
	private BuildingEntity building ;
    
    
    public BuildingEntity getBuilding() {
		return building;
	}
	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }


}
