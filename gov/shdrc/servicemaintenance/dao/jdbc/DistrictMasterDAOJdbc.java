package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IDistrictMasterDAO;
import gov.shdrc.servicemaintenance.util.DistrictMaster;
import gov.shdrc.servicemaintenance.util.ShdrcServiceMaintenanceQueryList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DistrictMasterDAOJdbc implements IDistrictMasterDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<DistrictMaster> getDistrictDetailsList(){
		DistrictMaster districtDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<DistrictMaster> districtDetailsList =null;
	    try {
			districtDetailsList = new ArrayList<DistrictMaster>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.DISTRICT_LIST);
			resultSet = preparedStatement.executeQuery();
			String strUpdatedOn=null;
			while (resultSet.next()) {
				districtDetails =new DistrictMaster();
				districtDetails.setDistrictName(resultSet.getString(1));
				districtDetails.setDistrictHeadQuarters(resultSet.getString(2));  
				districtDetails.setState(resultSet.getString(3));  
				districtDetails.setYearOfPopulationCount(resultSet.getInt(4));
				districtDetails.setPopulation(resultSet.getInt(5));
				districtDetails.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(6)));
	        	strUpdatedOn=resultSet.getString(7);
	        	districtDetails.setUpdatedOn(strUpdatedOn!=null?Util.convertSqlDateToStrDate(strUpdatedOn):null);
	        	districtDetails.setDistrictId(resultSet.getInt(8));
	        	districtDetailsList.add(districtDetails);
	        }
		}catch (SQLException e) {
			e.printStackTrace();
		}
	    finally{
	    	try {
	    		if (resultSet != null) {
	    			resultSet.close();
	    		}
	    		if (preparedStatement != null) {
	    			preparedStatement.close();
	    		}
	    		if (connection != null) {
	    			connection.close();
	    		}
	    	} catch (SQLException ex) {
	    	 }
	    }
	    return districtDetailsList;
	}
	
	public boolean insertDistrict(String districtName,String districtHeadQuarters,String state,Integer yearOfPopulationCount,
			Integer population,String userName)throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			boolean isDistrictExists =isDistrictExists(districtName);
			 if(isDistrictExists){
				throw new SHDRCException("This District already exists");
			 }
			 connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.INSERT_DISTRICT);
			preparedStatementInsert.setString(1,districtName);
			preparedStatementInsert.setString(2,districtHeadQuarters);
			preparedStatementInsert.setString(3,state);
			if(yearOfPopulationCount==null)
				preparedStatementInsert.setNull(4,java.sql.Types.INTEGER);
			else
				preparedStatementInsert.setInt(4,yearOfPopulationCount);
			preparedStatementInsert.setInt(5,population);
			java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementInsert.setTimestamp(6,addOnDate);
			preparedStatementInsert.setString(7,userName);
			preparedStatementInsert.execute();
			successFlag=true;
	        connection.commit();
         }catch (SQLException e) {
        	 e.printStackTrace();
             try {
            	 connection.rollback();
             }catch (SQLException e1) { 
            	 e1.printStackTrace();
              }
          }finally{
        	  try {
        		  if(preparedStatementInsert != null) {
        			  preparedStatementInsert.close();
		          }
		          if(connection != null) {
		        	  connection.close();
                  }
              }catch (SQLException ex) {
            	  ex.printStackTrace();
               }
           }
		return successFlag;
	}
	
	public boolean isDistrictExists(String districtName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		boolean isDistrictExists=false;
		  ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.DISTRICT_EXISTS);
			preparedStatement.setString(1,districtName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	long districtCount=resultSet.getLong(1);
	        	isDistrictExists =(districtCount>0);
	        }
	    } catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
				try {
	                if (preparedStatement != null) {
	                	preparedStatement.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }
			}
		return isDistrictExists;		
	}
	
	public DistrictMaster getDistrictDetails(Integer districtId){
		DistrictMaster districtDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {
			
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_DISTRICT_DETAILS);
			preparedStatement.setLong(1,districtId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	districtDetails =new DistrictMaster();
	        	districtDetails.setDistrictName(resultSet.getString(1));
	        	districtDetails.setDistrictHeadQuarters(resultSet.getString(2));
	        	districtDetails.setState(resultSet.getString(3));
	        	districtDetails.setYearOfPopulationCount(resultSet.getInt(4));
	        	districtDetails.setPopulation(resultSet.getInt(5));
	        	// districtDetails.setActive(resultSet.getInt(6));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	            	connection.close();
	            }
	
	        } catch (SQLException ex) {
	        }
	
		}
		return districtDetails;

	}
	
	public boolean updateDistrict(int districtId,String districtName,String districtHeadQuarters,Integer yearOfPopulationCount,
			Integer population)throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.UPDATE_DISTRICT);
			preparedStatementUpdate.setString(1,districtName);
			preparedStatementUpdate.setString(2,districtHeadQuarters);
			preparedStatementUpdate.setInt(3,yearOfPopulationCount);
			preparedStatementUpdate.setInt(4,population);
			java.sql.Timestamp updateOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementUpdate.setTimestamp(5,updateOnDate);
			// preparedStatementUpdate.setInt(6,active);
			preparedStatementUpdate.setInt(6,districtId);
			preparedStatementUpdate.execute();
			successFlag=true;
	        connection.commit();
         }catch (SQLException e) {
        	 e.printStackTrace();
             try {
            	 connection.rollback();
             }catch (SQLException e1) { 
            	 e1.printStackTrace();
              }
          }finally{
        	  try {
        		  if(preparedStatementUpdate != null) {
        			  preparedStatementUpdate.close();
		          }
		          if(connection != null) {
		        	  connection.close();
                  }
              }catch (SQLException ex) {
            	  ex.printStackTrace();
               }
           }
		return successFlag;
	}
}
