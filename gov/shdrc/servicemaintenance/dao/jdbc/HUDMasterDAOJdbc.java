package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IHUDMasterDAO;
import gov.shdrc.servicemaintenance.util.HUDMaster;
import gov.shdrc.servicemaintenance.util.ShdrcServiceMaintenanceQueryList;
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
public class HUDMasterDAOJdbc implements IHUDMasterDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<HUDMaster> getHudDetailsList(Integer directorateId){
		HUDMaster hudDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<HUDMaster> hudDetailsList =null;
	try {
		hudDetailsList = new ArrayList<HUDMaster>();
		connection=jdbcTemplate.getDataSource().getConnection();
		switch(directorateId){
			case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_DMS);
				break;
			case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_DPH);
				break;
			case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_TNMSC);
				break;
			case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_NRHM);
				break;
			case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_DME);
				break;
			case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_DFW);
				break;
			case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_COC);
				break;
			case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST_CMCHIS);
				break;
			default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_LIST);
			    break;
		}  
		preparedStatement.setInt(1,directorateId);
		resultSet = preparedStatement.executeQuery();
		String strUpdatedOn=null;
		while (resultSet.next()) {
			hudDetails =new HUDMaster();
			hudDetails.setHudName(resultSet.getString(1));
			hudDetails.setHudType(resultSet.getString(2));  
			hudDetails.setHudGroup(resultSet.getString(3));  
			hudDetails.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(4)));
        	strUpdatedOn=resultSet.getString(5);
        	hudDetails.setUpdatedOn(strUpdatedOn!=null?Util.convertSqlDateToStrDate(strUpdatedOn):null);
        	hudDetails.setHudId(resultSet.getInt(6));
        	hudDetailsList.add(hudDetails);
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
	return hudDetailsList;
	}
	
	public boolean insertHUD(Integer directorateId,String hudName,String hudType,String hudGroup,String userName)throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			boolean isHUDExists =isHUDExists(hudName);
			 if(isHUDExists){
				throw new SHDRCException("This HUD already exists");
			 }
			 connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			switch(directorateId){
			case ShdrcConstants.DirectorateId.DMS		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_DMS);
				break;
			case ShdrcConstants.DirectorateId.DPH		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_DPH);
				break;
			case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_TNMSC);
				break;
			case ShdrcConstants.DirectorateId.NRHM		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_NRHM);
				break;
			case ShdrcConstants.DirectorateId.DME		:   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_DME);
				break;
			case ShdrcConstants.DirectorateId.DFW		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_DFW);
				break;
			case ShdrcConstants.DirectorateId.COC		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_COC);
				break;
			case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD_CMCHIS);
				break;
			default                                     :   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_HUD);
			    break;
			}  
			preparedStatementInsert.setString(1,hudName);
			preparedStatementInsert.setString(2,hudType);
			preparedStatementInsert.setString(3,Util.isNullOrBlank(hudGroup)?null:hudGroup);
			java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementInsert.setTimestamp(4,addOnDate);
			preparedStatementInsert.setString(5,userName);
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
	
	public boolean isHUDExists(String hudName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		boolean isHUDExists=false;
		  ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.HUD_EXISTS);
			preparedStatement.setString(1,hudName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	long hudCount=resultSet.getLong(1);
	        	isHUDExists =(hudCount>0);
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
		return isHUDExists;		
	}
	
	public HUDMaster getHudDetails(Integer hudId,Integer directorateId){
		HUDMaster hudDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {
			
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
			case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_DMS);
				break;
			case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_DPH);
				break;
			case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_TNMSC);
				break;
			case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_NRHM);
				break;
			case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_DME);
				break;
			case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_DFW);
				break;
			case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_COC);
				break;
			case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS_CMCHIS);
				break;
			default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_HUD_DETAILS);
			    break;
			}

			preparedStatement.setInt(1,directorateId);
			preparedStatement.setInt(2,hudId);		
			preparedStatement.setInt(3,directorateId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	hudDetails =new HUDMaster();
	        	hudDetails.setHudName(resultSet.getString(1));
	        	hudDetails.setHudType(resultSet.getString(2));
	        	hudDetails.setHudGroup(resultSet.getString(3));
	        	hudDetails.setDirectorateName(resultSet.getString(4));
	        	hudDetails.setDirectorateId(directorateId);
	        	
	        	// hudDetails.setActive(resultSet.getInt(4));
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
		return hudDetails;

	}
	
	public boolean updateHUD(Integer directorateId,int hudId,String hudName,String hudType,String hudGroup)throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			switch(directorateId){
			case ShdrcConstants.DirectorateId.DMS		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_DMS);
				break;
			case ShdrcConstants.DirectorateId.DPH		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_DPH);
				break;
			case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_TNMSC);
				break;
			case ShdrcConstants.DirectorateId.NRHM		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_NRHM);
				break;
			case ShdrcConstants.DirectorateId.DME		:   preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_DME);
				break;
			case ShdrcConstants.DirectorateId.DFW		:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_DFW);
				break;
			case ShdrcConstants.DirectorateId.COC		:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_COC);
				break;
			case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD_CMCHIS);
				break;
			default                                     :   preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_HUD);
			    break;
			}
			preparedStatementUpdate.setString(1,hudName);
			preparedStatementUpdate.setString(2,hudType);
			preparedStatementUpdate.setString(3,hudGroup);
			java.sql.Timestamp updateOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementUpdate.setTimestamp(4,updateOnDate);
			// preparedStatementUpdate.setInt(6,active);
			preparedStatementUpdate.setInt(5,hudId);
			preparedStatementUpdate.setInt(6,directorateId);
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
}
