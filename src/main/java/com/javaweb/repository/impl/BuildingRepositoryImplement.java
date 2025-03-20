package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImplement implements BuildingRepository {
	
	//Join các bảng cần thiết
	public static void joinTable(Map<String, Object> params,List<String> typeCode, StringBuilder sql) {
		String staffid = (String) params.get("staffid");
		if(StringUtil.notNull(staffid)) {
			sql.append("JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		if(typeCode != null && typeCode.size() != 0 ) {
			sql.append("JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append("JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
		//buoi 13, dùng exist trong truy vấn
//		String rentAreaFrom = (String)params.get("areaFrom");
//		String rentAreaTo = (String)params.get("areaTo");
//		if(StringUtil.notNull(rentAreaTo) || StringUtil.notNull(rentAreaFrom)) {
//			sql.append("JOIN rentarea ON rentarea.buildingid = b.id ");
//		}
	}
	
	//truy vấn  thông thường : những value là chuỗi
	public static void queryNormal(Map<String, Object> params, StringBuilder where) {
		for(Map.Entry<String, Object> item : params.entrySet()) {
			if(!item.getKey().equals("staffid") && !item.getKey().equals("typeCode") 
					&& !item.getKey().startsWith("area")
					&& !item.getKey().startsWith("rentPrice")) {
				String value = item.getValue().toString();
				if(StringUtil.notNull(value)) {
					if(NumberUtil.isNumber(value)) {
						where.append(" AND b." + item.getKey() + " = " + value);
					}
					else {
						where.append(" AND b." + item.getKey() + " like '%" + value + "%' ");

					}
				}
			}
		}
	}
	
	//truy vấn đặc biệt : value là số
	public static void querySpecial(Map<String, Object> params,List<String> typeCode, StringBuilder where) {
		//xử lý mã nhân viên
		String staffid = (String) params.get("staffid");
		if(StringUtil.notNull(staffid)) {
			where.append(" AND assignmentbuilding.staffid = " + staffid);
		}
		//xử lý diện tích
		String rentAreaFrom = (String)params.get("areaFrom");
		String rentAreaTo = (String)params.get("areaTo");
		if(StringUtil.notNull(rentAreaTo) || StringUtil.notNull(rentAreaFrom)) {
			where.append(" AND EXISTS (SELECT * FROM rentarea WHERE b.id = rentarea.buildingid ");
			if(StringUtil.notNull(rentAreaFrom)) {
				where.append(" AND rentarea.value >= " + rentAreaFrom);
			}
			if(StringUtil.notNull(rentAreaTo)) {
				where.append(" AND rentarea.value <= " + rentAreaTo);
			}
			
			where.append(") ");
		}
		
		//xử lý mã giá thuê
		String rentPriceFrom = (String)params.get("rentPriceFrom");
		String rentPriceTo = (String)params.get("rentPriceTo");
		if(StringUtil.notNull(rentPriceTo) || StringUtil.notNull(rentPriceFrom)) {
			if(StringUtil.notNull(rentPriceFrom)) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if(StringUtil.notNull(rentPriceTo)) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		
//		java7
//		if (typeCode != null && !typeCode.isEmpty()) {
//		    List<String> code = new ArrayList<>();
//		    for (String item : typeCode) {
//		        code.add("'" + item + "'");  // Dùng nháy đơn '
//		    }
//		    where.append(" AND renttype.code IN (" + String.join(",", code) + ") ");
//		}
		
		//java8 buoi 13
		if (typeCode != null && !typeCode.isEmpty()) {
			where.append(" AND (");
			String sql = typeCode.stream().map(item -> "renttype.code like" + "'%" + item + "%'").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(") ");
		}
	}
	
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params,List<String> typeCode) {
		StringBuilder sql = new StringBuilder("SELECT distinct b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, \r\n"
				+ "       b.floorarea, b.rentprice, b.managername, b.managerphonenumber, \r\n"
				+ "       b.servicefee, b.brokeragefee FROM building b ");
		joinTable(params, typeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(params, where);
		querySpecial(params, typeCode, where);
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<>();
		
		try (Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());){
			
			while(rs.next()) {
			    BuildingEntity buildingEntity = new BuildingEntity();
			    buildingEntity.setId(rs.getLong("b.id"));
			    buildingEntity.setName(rs.getString("b.name"));
			    buildingEntity.setWard(rs.getString("b.ward"));
			    buildingEntity.setDistrictid(rs.getLong("b.districtid"));
			    buildingEntity.setStreet(rs.getString("b.street"));
			    buildingEntity.setNumberOfBasement(rs.getInt("b.numberofbasement"));
			    buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
			    buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
			    buildingEntity.setServiceFee(rs.getString("b.servicefee"));
			    buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
			    buildingEntity.setManagerName(rs.getString("b.managername"));
			    buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
			    result.add(buildingEntity);
			}

		    
		} catch (SQLException e) {
		    e.printStackTrace();
		    System.out.println("Connected database failed...");
		}
		return result;
	}

}
