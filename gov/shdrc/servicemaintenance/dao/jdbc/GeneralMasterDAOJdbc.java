package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IGeneralMasterDAO;
import gov.shdrc.servicemaintenance.util.GeneralMaster;
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
public class GeneralMasterDAOJdbc implements IGeneralMasterDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<GeneralMaster> getGeneralDetailsList(Integer directorateId){
		GeneralMaster generalDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<GeneralMaster> generalDetailsList =null;
	    try {
	    	generalDetailsList = new ArrayList<GeneralMaster>();
	    	connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST);
				    break;
			}  
			//preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_MAINTENANCE_LIST);
			preparedStatement.setInt(1, directorateId);
			resultSet = preparedStatement.executeQuery();			
			String strUpdatedOn=null;
			String directorateName=null;
			while (resultSet.next()) {
				generalDetails =new GeneralMaster();
				generalDetails.setGeneralId(resultSet.getInt(1));
				generalDetails.setDirectorateId(resultSet.getInt(2));  
				directorateName=(Util.getDirectorateShortName(directorateId));
				generalDetails.setDirectorateName(directorateName); 
				generalDetails.setGeneralName(resultSet.getString(3));
				generalDetails.setGeneralType(resultSet.getString(4));
				generalDetails.setGeneralClassification(resultSet.getString(5));
				generalDetails.setGeneralCategory(resultSet.getString(6));
				generalDetails.setGeneralSubCategory(resultSet.getString(7));
				generalDetails.setGeneralSubSubCategory(resultSet.getString(8));
				generalDetails.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(9)));
	        	strUpdatedOn=resultSet.getString(10);
	        	generalDetails.setUpdatedOn(strUpdatedOn!=null?Util.convertSqlDateToStrDate(strUpdatedOn):null);
	        	
	        	generalDetailsList.add(generalDetails);
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
	    return generalDetailsList;
	}

	public List<CommonStringList> getTypeList(Integer directorateId){
		List<CommonStringList> typeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			typeList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST);
				    break;
			}  
			/*preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.GENERAL_TYPE_LIST);*/
			preparedStatement.setInt(1, directorateId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setName(resultSet.getString(1));
	        	typeList.add(CommonStringList);
	        }
	        if(typeList.size()==0)
	        	typeList=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception json){
			
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

            }catch (SQLException ex) {
	         }
	
		}
	    return typeList;
	}
	
	public boolean insertGeneral(Integer directorateId,Integer indicatorId,String generalCategory,String type,
			String otherType,String generalName,String subCategory,String subSubCategory,String classification,String userName)throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS_CMCHIS);
					break;
				default                                     :   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS);
				    break;
			}  
		/*	preparedStatementInsert = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.INSERT_GENERAL_DETAILS);*/
			preparedStatementInsert.setInt(1,directorateId);
			preparedStatementInsert.setLong(2,indicatorId);
			if("OTHERS".equalsIgnoreCase(type)){
				preparedStatementInsert.setString(3,otherType);
			}else{
				preparedStatementInsert.setString(3,type);
			}
			preparedStatementInsert.setString(4,generalName);
			preparedStatementInsert.setString(5,generalCategory);
			preparedStatementInsert.setString(6,subCategory);
			preparedStatementInsert.setString(7,subSubCategory);
			preparedStatementInsert.setString(8,classification);		
			java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementInsert.setTimestamp(9,addOnDate);
			preparedStatementInsert.setString(10,userName);
			preparedStatementInsert.execute();
	        connection.commit();
	        successFlag=true;
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
	
	public boolean updateGeneral(Integer directorateId,Integer indicatorId,String generalCategory,String type,
			String otherType,String generalName,String subCategory,String subSubCategory,String classification)throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS_CMCHIS);
					break;
				default                                     :   preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS);
				    break;
			}  
			/*preparedStatementUpdate = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.UPDATE_GENERAL_DETAILS);*/
			if("OTHERS".equalsIgnoreCase(type)){
				preparedStatementUpdate.setString(1,otherType);
			}else{
				preparedStatementUpdate.setString(1,type);
			}
			preparedStatementUpdate.setString(2,generalName);
			preparedStatementUpdate.setString(3,generalCategory);
			preparedStatementUpdate.setString(4,subCategory);
			preparedStatementUpdate.setString(5,subSubCategory);
			preparedStatementUpdate.setString(6,classification);		
			java.sql.Timestamp updateOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementUpdate.setTimestamp(7,updateOnDate);
			preparedStatementUpdate.setInt(8,directorateId);
			preparedStatementUpdate.setInt(9,indicatorId);
			preparedStatementUpdate.execute();
	        connection.commit();
	        successFlag=true;
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
	
	public GeneralMaster getGeneralDetails(Integer directorateId,Integer indicatorId){
		GeneralMaster generalDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {
			
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS);
				    break;
			}
			/*preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_GENERAL_DETAILS);*/
			preparedStatement.setLong(1,directorateId);
			preparedStatement.setLong(2,indicatorId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	generalDetails =new GeneralMaster();
	        	generalDetails.setGeneralType(resultSet.getString(1));
	        	generalDetails.setGeneralName(resultSet.getString(2));
	        	generalDetails.setGeneralCategory(resultSet.getString(3));
	        	generalDetails.setGeneralSubCategory(resultSet.getString(4));
	        	generalDetails.setGeneralSubSubCategory(resultSet.getString(5));
	        	generalDetails.setGeneralClassification(resultSet.getString(6));
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
		return generalDetails;

	}
}
