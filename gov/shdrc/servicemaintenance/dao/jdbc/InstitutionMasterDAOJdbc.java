package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IInstitutionMasterDAO;
import gov.shdrc.servicemaintenance.util.InstitutionMaster;
import gov.shdrc.servicemaintenance.util.ShdrcServiceMaintenanceQueryList;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class InstitutionMasterDAOJdbc implements IInstitutionMasterDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<InstitutionMaster> getInstitutionDetailsList(Integer directorateId){
		InstitutionMaster institutionDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<InstitutionMaster> institutionDetailsList =null;
	    try {
			institutionDetailsList = new ArrayList<InstitutionMaster>();
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSTITUTION_LIST);
				    break;
			}  
			preparedStatement.setInt(1,directorateId);
			resultSet = preparedStatement.executeQuery();
			String strUpdatedOn=null;
			while (resultSet.next()) {
				institutionDetails =new InstitutionMaster();
				institutionDetails.setInstitutionName(resultSet.getString(1));
				institutionDetails.setInstitutionLevel(resultSet.getString(2));  
				institutionDetails.setInstitutionSpecialityId(resultSet.getString(3));  
				institutionDetails.setInstitutionType(resultSet.getString(4));  
				institutionDetails.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(5)));
	        	strUpdatedOn=resultSet.getString(6);
	        	institutionDetails.setUpdatedOn(strUpdatedOn!=null?Util.convertSqlDateToStrDate(strUpdatedOn):null);
	        	institutionDetails.setInstitutionId(resultSet.getInt(7));
	        	institutionDetails.setLocationId(resultSet.getInt(8));
	        	institutionDetailsList.add(institutionDetails);
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
	    return institutionDetailsList;
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

	public List<CommonStringList> getLocationList(Integer directorateId,Integer districtId,Integer hudId){
		List<CommonStringList> locationList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			locationList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.BLOCKNAME_LIST);
				    break;
			}  
		preparedStatement.setInt(1,districtId);
		preparedStatement.setInt(2,hudId);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setId(resultSet.getInt(1));
	        	CommonStringList.setName(resultSet.getString(2));
	        	locationList.add(CommonStringList);
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
	        
			return locationList;
	}

}
