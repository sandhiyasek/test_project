package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IIndicatorMasterDAO;
import gov.shdrc.servicemaintenance.util.IndicatorMaster;
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
public class IndicatorMasterDAOJdbc implements IIndicatorMasterDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<CommonStringList> getCategoryList(Integer directorateId){
		List<CommonStringList> categoryList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			categoryList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST);
				    break;
			}  
		/*	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_CATEGORY_LIST);*/
			preparedStatement.setInt(1, directorateId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setName(resultSet.getString(1));
	        	categoryList.add(CommonStringList);
	        }
	        if(categoryList.size()==0)
	        	categoryList=null;
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
	    return categoryList;
	}
	
	public int insertIndicator(Integer directorateId,String classification,String category,
			String otherCategory,String indicatorName,String subCategory,String subSubCategory,String modeSource,String otherfileSystem,
			String factMap,String frequency,String calculation,String hierarchy,String userName){
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		Integer indicatorId=null;
		try {
			/*boolean isIndicatorExists =isIndicatorExists(directorateId,classification,category,indicatorName,frequency);
			 if(isIndicatorExists){
				throw new SHDRCException("This Indicator already exists");
			 }*/
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR_CMCHIS);
					break;
				default                                     :   preparedStatementInsert = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR);
				    break;
			}  
			/*preparedStatementInsert = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.INSERT_INDICATOR);*/
			preparedStatementInsert.setInt(1,directorateId);
			preparedStatementInsert.setString(2,classification);
			if("OTHERS".equalsIgnoreCase(category)){
				preparedStatementInsert.setString(3,otherCategory);
			}else{
				preparedStatementInsert.setString(3,category);
			}
			preparedStatementInsert.setString(4,indicatorName);
			preparedStatementInsert.setString(5,subCategory);
			preparedStatementInsert.setString(6,subSubCategory);
			preparedStatementInsert.setString(7,modeSource);
			preparedStatementInsert.setString(8,Util.isNullOrBlank(otherfileSystem)?null:otherfileSystem);
			preparedStatementInsert.setString(9,factMap);
			preparedStatementInsert.setString(10,frequency);
			preparedStatementInsert.setString(11,calculation);
			/*if("N".equalsIgnoreCase(factMap) && "N".equalsIgnoreCase(calculation)){
				preparedStatementInsert.setString(11,"Y");
			}
			else{
				preparedStatementInsert.setString(11,"N");
			}*/
			preparedStatementInsert.setString(12,Util.getIndHierarchyShortName(hierarchy));			
			java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementInsert.setTimestamp(13,addOnDate);
			preparedStatementInsert.setString(14,userName);
			ResultSet rs =preparedStatementInsert.executeQuery();
			if(rs.next())
				indicatorId=rs.getInt(1);	
	        connection.commit();
         }catch (SQLException e) {
        	 
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
		return indicatorId;
	}

	public boolean isIndicatorExists(Integer directorateId,String classification,String category,String indicatorName,String frequency){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		boolean isIndicatorExists=false;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_EXISTS);
			preparedStatement.setInt(1,directorateId);
			preparedStatement.setString(2,classification);
			preparedStatement.setString(3,category);
			preparedStatement.setString(4,indicatorName);
			preparedStatement.setString(5,frequency);			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
			long indicatorCount=resultSet.getLong(1);
			isIndicatorExists =(indicatorCount>0);
			}
		}catch (SQLException e) {
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
		return isIndicatorExists;		
	}
	
	public List<IndicatorMaster> getindicatorDetailsList(Integer directorateId){
		IndicatorMaster indicatorDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<IndicatorMaster> indicatorDetailsList =null;
	    try {
	    	indicatorDetailsList = new ArrayList<IndicatorMaster>();
	    	connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST);
				    break;
				}  
			
			/*preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_MAINTENANCE_LIST);*/
			preparedStatement.setInt(1,directorateId);
			resultSet = preparedStatement.executeQuery();
			String directorateName=null;
			String strUpdatedOn=null;	
			String indicatorHierarchy=null;
			String indDataEntry=null;
			while (resultSet.next()) {
				indicatorDetails =new IndicatorMaster();
				indicatorDetails.setIndicatorId(resultSet.getInt(1));
				directorateId=resultSet.getInt(2);
				indicatorDetails.setDirectorateId(directorateId); 
				directorateName=(Util.getDirectorateShortName(directorateId));
				indicatorDetails.setDirectorateName(directorateName); 
				indicatorDetails.setIndicatorName(resultSet.getString(3));  
				indicatorDetails.setIndicatorClassification(resultSet.getString(4));
				indicatorDetails.setIndicatorCategory(resultSet.getString(5));
				indicatorDetails.setIndicatorSubCategory(resultSet.getString(6));
				indicatorDetails.setIndicatorSubSubCategory(resultSet.getString(7));
				indicatorDetails.setIndicatorLevel(resultSet.getString(8));
				indicatorDetails.setIndicatorFactMap(Util.getIndicatorFactCalc(resultSet.getString(9)));
				indicatorDetails.setIndicatorFreq(Util.getFrequencyFullName(resultSet.getString(10)));
				indicatorDetails.setIndicatorCalc(Util.getIndicatorFactCalc(resultSet.getString(11)));
				indicatorDetails.setModeOfSource(resultSet.getString(12));
				indicatorHierarchy=resultSet.getString(13);
				if(indicatorHierarchy!=null){
					indicatorDetails.setIndicatorHierarchy(Util.getIndicatorHierarchy(indicatorHierarchy));
				}
				indicatorDetails.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(14)));
	        	strUpdatedOn=resultSet.getString(15);
	        	indicatorDetails.setUpdatedOn(strUpdatedOn!=null?Util.convertSqlDateToStrDate(strUpdatedOn):null);
	        	
	        	indicatorDetailsList.add(indicatorDetails);
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
	    return indicatorDetailsList;
	}

	public IndicatorMaster getIndicatorDetails(Integer directorateId,Integer indicatorId){
		IndicatorMaster indicatorDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {
			
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS_CMCHIS);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS);
				    break;
			}  
			/*preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.SELECT_INDICATOR_DETAILS);*/
			preparedStatement.setLong(1,directorateId);
			preparedStatement.setLong(2,indicatorId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	indicatorDetails =new IndicatorMaster();
	        	indicatorDetails.setIndicatorName(resultSet.getString(1));
	        	indicatorDetails.setIndicatorClassification(resultSet.getString(2));
	        	indicatorDetails.setIndicatorCategory(resultSet.getString(3));
	        	indicatorDetails.setIndicatorSubCategory(resultSet.getString(4));
	        	indicatorDetails.setIndicatorSubSubCategory(resultSet.getString(5));
	        	indicatorDetails.setModeOfSource(resultSet.getString(6));
	        	indicatorDetails.setIndicatorFactMap(resultSet.getString(7));
	        	indicatorDetails.setIndicatorFreq(resultSet.getString(8));
	        	indicatorDetails.setIndicatorCalc(resultSet.getString(9));
	        	indicatorDetails.setIndicatorHierarchy(Util.getIndicatorHierarchy(resultSet.getString(10)));
	        	indicatorDetails.setDirectorateName(Util.getFullRole(Util.getDirectorateShortName(resultSet.getInt(11))));
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
		return indicatorDetails;

	}
	
	public boolean updateIndicator(Integer directorateId,Integer indicatorId,String classification,String category,
			String otherCategory,String indicatorName,String subCategory,String subSubCategory,String modeSource,String otherfileSystem,
			String factMap,String frequency,String calculation,String hierarchy){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_COC);
					break;
				case ShdrcConstants.DirectorateId.CMCHIS	:  	preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR_CMCHIS);
					break;
				default                                     :   preparedStatementUpdate = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR);
				    break;
			}  
			/*preparedStatementUpdate = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.UPDATE_INDICATOR);*/
			preparedStatementUpdate.setString(1,indicatorName);
			preparedStatementUpdate.setString(2,classification);
			if("OTHERS".equalsIgnoreCase(category)){
				preparedStatementUpdate.setString(3,otherCategory);
			}else{
				preparedStatementUpdate.setString(3,category);
			}
			preparedStatementUpdate.setString(4,subCategory);
			preparedStatementUpdate.setString(5,subSubCategory);
			preparedStatementUpdate.setString(6,modeSource);
			preparedStatementUpdate.setString(7,Util.isNullOrBlank(otherfileSystem)?null:otherfileSystem);
			preparedStatementUpdate.setString(8,factMap);
			preparedStatementUpdate.setString(9,frequency);
			preparedStatementUpdate.setString(10,calculation);
			/*if("N".equalsIgnoreCase(factMap) && "N".equalsIgnoreCase(calculation)){
				preparedStatementUpdate.setString(10,"Y");
			}
			else{
				preparedStatementUpdate.setString(10,"N");
			}*/
			preparedStatementUpdate.setString(11,Util.getIndHierarchyShortName(hierarchy));
			java.sql.Timestamp updateOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementUpdate.setTimestamp(12,updateOnDate);
			preparedStatementUpdate.setInt(13,directorateId);
			preparedStatementUpdate.setInt(14,indicatorId);
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
	
	public List<CommonStringList> getIndHierarchyList(Integer directorateId){
		List<CommonStringList> indHierarchyList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			indHierarchyList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_HIERARCHY_LIST);
			preparedStatement.setInt(1, directorateId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setName(resultSet.getString(1));
	        	indHierarchyList.add(CommonStringList);
	        }
	        if(indHierarchyList.size()==0)
	        	indHierarchyList=null;
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
	    return indHierarchyList;
	}
}
