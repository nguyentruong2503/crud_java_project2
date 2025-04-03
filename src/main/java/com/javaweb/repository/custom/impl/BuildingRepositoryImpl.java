package com.javaweb.repository.custom.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom  {

	@PersistenceContext
	private EntityManager entityManager;
	
	//Join các bảng cần thiết
		public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
			Long staffid = buildingSearchBuilder.getStaffId();
			if(staffid != null) {
				sql.append("JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
			}
			List<String> typeCode = buildingSearchBuilder.getTypeCode(); 
			if(typeCode != null && typeCode.size() != 0 ) {
				sql.append("JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
				sql.append("JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
			}
			//buoi 13, dùng exist trong truy vấn
//			String rentAreaFrom = (String)params.get("areaFrom");
//			String rentAreaTo = (String)params.get("areaTo");
//			if(StringUtil.notNull(rentAreaTo) || StringUtil.notNull(rentAreaFrom)) {
//				sql.append("JOIN rentarea ON rentarea.buildingid = b.id ");
//			}
		}
		
		//truy vấn  thông thường : những value là chuỗi
		public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
			try {
				Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
				for(Field item : fields) {
					item.setAccessible(true);
					String fieldName = item.getName();
					if(!fieldName.equals("staffid") && !fieldName.equals("typeCode") 
							&& !fieldName.startsWith("area")
							&& !fieldName.startsWith("rentPrice")) {
						Object value = item.get(buildingSearchBuilder);
						if(value != null) {
							if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer") ) {
								where.append(" AND b." + fieldName + " = " + value);
							}
							else if (item.getType().getName().equals("java.lang.String")) {
								where.append(" AND b." + fieldName + " like '%" + value + "%' ");
		
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		//truy vấn đặc biệt : value là số
		public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
			//xử lý mã nhân viên
			Long staffid = buildingSearchBuilder.getStaffId();
			if(staffid != null) {
				where.append(" AND assignmentbuilding.staffid = " + staffid);
			}
			//xử lý diện tích
			Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
			Long rentAreaTo = buildingSearchBuilder.getAreaTo();
			if(rentAreaTo != null || rentAreaFrom != null) {
				where.append(" AND EXISTS (SELECT * FROM rentarea WHERE b.id = rentarea.buildingid ");
				if(rentAreaFrom != null) {
					where.append(" AND rentarea.value >= " + rentAreaFrom);
				}
				if(rentAreaTo != null) {
					where.append(" AND rentarea.value <= " + rentAreaTo);
				}
				
				where.append(") ");
			}
			
			//xử lý mã giá thuê
			Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
			Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
			if(rentPriceTo != null || rentPriceFrom != null) {
				if(rentPriceFrom != null) {
					where.append(" AND b.rentprice >= " + rentPriceFrom);
				}
				if(rentPriceTo != null) {
					where.append(" AND b.rentprice <= " + rentPriceTo);
				}
			}
			
			List<String> typeCode = buildingSearchBuilder.getTypeCode();
			if (typeCode != null && !typeCode.isEmpty()) {
				where.append(" AND (");
				String sql = typeCode.stream().map(item -> "renttype.code like" + "'%" + item + "%'").collect(Collectors.joining(" OR "));
				where.append(sql);
				where.append(") ");
			}
		}
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("SELECT distinct b.* FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		sql.append(where);
		
		//Sql Native
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();

	}

}
