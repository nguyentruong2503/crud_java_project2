package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "building")
public class BuildingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ward")
    private String ward;

    @Column(name = "street")
    private String street;

    @Column(name = "managername")
    private String managername;

    @Column(name = "managerphonenumber")
    private String managerphonenumber;

    @Column(name = "floorarea")
    private Long floorarea;

    @Column(name = "rentprice")
    private Long rentprice;

    @Column(name = "servicefee")
    private String servicefee;

    @Column(name = "brokeragefee")
    private Long brokeragefee;

    @Column(name = "numberofbasement")  // Chỉnh lại theo quy tắc thường
    private Integer numberofbasement;

    @ManyToOne
    @JoinColumn(name = "districtid")
    private DistrictEntity district;

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    private List<RentAreaEntity> item = new ArrayList<>();

    // GETTER & SETTER (viết thường)

    public List<RentAreaEntity> getItem() {
        return item;
    }

    public void setItem(List<RentAreaEntity> item) {
        this.item = item;
    }

    public DistrictEntity getDistrict() {
        return district;
    }

    public void setDistrict(DistrictEntity district) {
        this.district = district;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public String getManagerphonenumber() {
        return managerphonenumber;
    }

    public void setManagerphonenumber(String managerphonenumber) {
        this.managerphonenumber = managerphonenumber;
    }

    public Long getFloorarea() {
        return floorarea;
    }

    public void setFloorarea(Long floorarea) {
        this.floorarea = floorarea;
    }

    public Long getRentprice() {
        return rentprice;
    }

    public void setRentprice(Long rentprice) {
        this.rentprice = rentprice;
    }

    public String getServicefee() {
        return servicefee;
    }

    public void setServicefee(String servicefee) {
        this.servicefee = servicefee;
    }

    public Long getBrokeragefee() {
        return brokeragefee;
    }

    public void setBrokeragefee(Long brokeragefee) {
        this.brokeragefee = brokeragefee;
    }

    public Integer getNumberofbasement() {
        return numberofbasement;
    }

    public void setNumberofbasement(Integer numberofbasement) {
        this.numberofbasement = numberofbasement;
    }
}
