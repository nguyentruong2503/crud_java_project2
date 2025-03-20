package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> getValueByBuildingId(Long id) {
		// TODO Auto-generated method stub
		String sql  = "Select * From rentarea Where rentarea.buildingid =  " + id ;
		List<RentAreaEntity> rentAreas = new ArrayList<>();
		try (Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()) {
			    RentAreaEntity entity = new RentAreaEntity();
			    entity.setValue(rs.getString("value"));
			    rentAreas.add(entity);
			}

		    
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return rentAreas;
	}

	

}
