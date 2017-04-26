package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.ILocationMasterDAO;
import gov.shdrc.servicemaintenance.util.LocationMaster;
import gov.shdrc.servicemaintenance.util.ShdrcServiceMaintenanceQueryList;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.ShdrcConstants;
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
public class LocationMasterDAOJdbc implements ILocationMasterDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<LocationMaster> getLocationDetailsList(Integer directorateId){
		LocationMaster locationDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<LocationMaster> locationDetailsList =null;
	    try {
			locationDetailsList = new ArrayList<LocationMaster>();
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.LOCATION_LIST);
				    break;
		}  
			preparedStatement.setInt(1,directorateId);
			resultSet = preparedStatement.executeQuery();
			String strUpdatedOn=null;
			while (resultSet.next()) {
				locationDetails =new LocationMaster();
				locationDetails.setHudType(resultSet.getString(1));
				locationDetails.setBlockName(resultSet.getString(2));  
				locationDetails.setTalukName(resultSet.getString(3));  
				locationDetails.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(4)));
	        	strUpdatedOn=resultSet.getString(5);
	        	locationDetails.setUpdatedOn(strUpdatedOn!=null?Util.convertSqlDateToStrDate(strUpdatedOn):null);
	        	locationDetails.setLocationId(resultSet.getInt(6));
	        	locationDetailsList.add(locationDetails);
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
	    	}catch (SQLException ex) {
	    	}
	    }
	    return locationDetailsList;
	}
	
	public String getHUDType(Integer directorateId){
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    String hudType=null;
		try {
			
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_TYPE);
			preparedStatement.setInt(1,directorateId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	hudType=resultSet.getString(1);
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
		return hudType;
	}
	
	public List<CommonStringList> getHudNameList(Integer directorateId,String hudType){
		List<CommonStringList> hudList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			hudList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUDNAME_LIST);
				    break;
			}  
		preparedStatement.setString(1,hudType);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setId(resultSet.getInt(1));
	        	CommonStringList.setName(resultSet.getString(2));
	        	hudList.add(CommonStringList);
        	}	
        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}finally{
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
	        
			return hudList;
	}
	
	public boolean insertLocation(Integer directorateId,Integer districtId,Integer hudId,String hudType,String talukName,String blockName,String userName)throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			switch(directorateId){
			case ShdrcConstants.DirectorateId.DMS		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_DMS);
				break;
			case ShdrcConstants.DirectorateId.DPH		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_DPH);
				break;
			case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_TNMSC);
				break;
			case ShdrcConstants.DirectorateId.NRHM		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_NRHM);
				break;
			case ShdrcConstants.DirectorateId.DME		:   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_DME);
				break;
			case ShdrcConstants.DirectorateId.DFW		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_DFW);
				break;
			case ShdrcConstants.DirectorateId.COC		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_COC);
				break;
			case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION_CMCHIS);
				break;
			default                                     :   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_LOCATION);
			    break;
			}  
			preparedStatementInsert.setInt(1,districtId);
			preparedStatementInsert.setInt(2,hudId);
			preparedStatementInsert.setString(3,hudType);
			preparedStatementInsert.setString(4,talukName);
			preparedStatementInsert.setString(5,blockName);
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
}

