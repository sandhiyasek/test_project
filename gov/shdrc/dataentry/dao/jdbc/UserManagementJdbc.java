package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.util.SecurityUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import gov.shdrc.dataentry.dao.IUserManagementDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.DFWEmployeeForm;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.UserManagement;
import gov.shdrc.util.Util;
@Service
public class UserManagementJdbc implements IUserManagementDAO{
	private static Logger log=Logger.getLogger(UserManagementJdbc.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<CommonStringList> getDirectoratesList() {
		// TODO Auto-generated method stub
		
		List<CommonStringList> resultList=new ArrayList<CommonStringList>();
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DIRECTORATE_MASTER_DIM_LIST);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	resultList.add(commonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- directoratesList "+e.getMessage());
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
		
		
		return resultList;
	}

	
	
	@Override
	public String getDerectrateName(int deroctrateListId) {
		// TODO Auto-generated method stub
		
		String orgName=null;
		PreparedStatement preparedStatementGet =null;
		ResultSet resultSet =null;
		Connection connection = null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			
			
			preparedStatementGet=connection.prepareStatement(ShdrcQueryList.GET_LDAPORGNAME_DETAILS);
			preparedStatementGet.setInt(1,deroctrateListId);
			resultSet = preparedStatementGet.executeQuery();
			resultSet.next();
			orgName= resultSet.getString(1);
			
			
	    }
		 catch (SQLException e) {
				log.error(this.getClass().getName() + "- directoratesList "+e.getMessage());
			}
			
			finally{
				try {
		            if (resultSet != null) {
		                resultSet.close();
		            }
		            if (preparedStatementGet != null) {
		            	preparedStatementGet.close();
		            }
		            if (connection != null) {
		            	connection.close();
		            }

		        } catch (SQLException ex) {
		        }

			}
		
		
		
		return orgName;
	}
	

	@Override
	public boolean saveUserManagementDetails(String userName, String firstName,
			String lastName, String email, long mobile, String password,
			int deroctrateListId, String tierVal,String designation) {
		Connection connection = null;
		PreparedStatement preparedStatementSave =null;
		PreparedStatement preparedStatementGet =null;
		PreparedStatement preparedStatementGetOgiD =null;
		ResultSet resultSet =null;
	
		boolean successFlag=false;
		int res=0;
		
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			
			
			preparedStatementGetOgiD=connection.prepareStatement(ShdrcQueryList.GET_USER_DETAILS_ByOGId);
			preparedStatementGetOgiD.setInt(1,deroctrateListId);
			resultSet = preparedStatementGetOgiD.executeQuery();
			resultSet.next();
			int dbOGId=resultSet.getInt(1);
			
			
			preparedStatementGet=connection.prepareStatement(ShdrcQueryList.GET_USER_DETAILS);
			preparedStatementGet.setInt(1,dbOGId);
			preparedStatementGet.setString(2,tierVal);
			resultSet = preparedStatementGet.executeQuery();
			
			resultSet.next();
			int dbTierId=resultSet.getInt(1);
			String actualPassword=SecurityUtil.digestMd5(password);
			preparedStatementSave=connection.prepareStatement(ShdrcQueryList.SAVE_USER_DETAILS);			
			preparedStatementSave.setString(1,userName);
			preparedStatementSave.setString(2,firstName);
			preparedStatementSave.setString(3,lastName);
			preparedStatementSave.setString(4,actualPassword);
			preparedStatementSave.setString(5,email);
			preparedStatementSave.setLong(6,mobile);
			preparedStatementSave.setInt(7,dbTierId);
			
			
			java.sql.Timestamp addedOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementSave.setTimestamp(8,addedOnDate);
			preparedStatementSave.setInt(9,deroctrateListId);
			preparedStatementSave.setString(10,designation);
			res=preparedStatementSave.executeUpdate();
			successFlag=true;
			connection.commit();
	    }catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  }finally{
				try {
	                if (preparedStatementSave != null) {
	                	preparedStatementSave.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }	
			}
		if(res==0)
		{
			successFlag=false;	
		}
		else{
			successFlag=true;
		}
		return successFlag;
	}



	@Override
	public List<UserManagement> getUserManagemenListByDeroctrateId(
			int deroctrateListId) {
		// TODO Auto-generated method stub
		UserManagement userManagementDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<UserManagement> userManagementDetailsList =new ArrayList<UserManagement>();
	    
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			userManagementDetailsList = new ArrayList<UserManagement>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.USER_MANAGEMENT_LIST);
			preparedStatement.setInt(1,deroctrateListId);
			resultSet = preparedStatement.executeQuery();
			
	        while (resultSet.next()) {
	        	userManagementDetails =new UserManagement();
	        	userManagementDetails.setUserName(resultSet.getString(1));
	        	userManagementDetails.setUfname(resultSet.getString(2));
	        	userManagementDetails.setUlname(resultSet.getString(3));
	        	userManagementDetails.setPhnNum(resultSet.getString(4));
	        	userManagementDetails.setEmail(resultSet.getString(5));
	        	userManagementDetails.setDesignation(resultSet.getString(6));
	        	userManagementDetails.setUserId(resultSet.getString(7));
	        
	        	userManagementDetailsList.add(userManagementDetails);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- userManagementDetailsList "+e.getMessage());
			System.out.println("- userManagementDetailsList "+e);
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
		System.out.println("userManagementDetailsList"+userManagementDetailsList);
		return userManagementDetailsList;
		}



	@Override
	public List<UserManagement> getUserMasterDetails(int userId) {
		
		UserManagement userManagementDetailss=null;
		Connection connection = null;
		  PreparedStatement preparedStatement =null;
		    ResultSet resultSet =null;
	    List<UserManagement> editUserManagementDetailsListValues =null;
	    
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			editUserManagementDetailsListValues = new ArrayList<UserManagement>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.USER_MANAGEMENT_LIST_BY_USERID);
			//preparedStatement = connection.prepareStatement("select \"User_Name\",\"Ufname\",\"Ulname\",\"Upass\",\"Email\",\"Mobile\",\"Designation\",\"Directorate_Id\",\"Tier\",\"User_Id\" from shdrc_dwh.\"User_Master\" where \"User_Id\"='394'");
			preparedStatement.setInt(1,userId);
			resultSet = preparedStatement.executeQuery();
		
			 while (resultSet.next()) {
				 userManagementDetailss =new UserManagement();
		        	
				 userManagementDetailss.setUserName(resultSet.getString(1));
				 userManagementDetailss.setUfname(resultSet.getString(2));
				 userManagementDetailss.setUlname(resultSet.getString(3));
				 userManagementDetailss.setPassword(resultSet.getString(4));
				 userManagementDetailss.setEmail(resultSet.getString(5));
		         userManagementDetailss.setPhnNum(resultSet.getString(6));
		        userManagementDetailss.setDesignation(resultSet.getString(7));
		        userManagementDetailss.setDirectrateId(resultSet.getString(8));
		        userManagementDetailss.setTier(resultSet.getString(9));
		        userManagementDetailss.setTierCD(resultSet.getString(10));
		        userManagementDetailss.setUserIds(resultSet.getInt(11));
		        userManagementDetailss.setDirectrateName(resultSet.getString(12));
		        
		        
		        
		        
		        
		        	editUserManagementDetailsListValues.add(userManagementDetailss);
		        }log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e) {
				log.error(this.getClass().getName() + "- userManagementDetailsList "+e.getMessage());
				System.out.println("- userManagementDetailsList "+e);
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
			return editUserManagementDetailsListValues;
			}

	

	@Override
	public boolean updateUserManagementValues(String firstName,
			String lastName, String email, long mobile, String designation,
			int userId) {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		PreparedStatement preparedStatementSave =null;
		
		ResultSet resultSet =null;
	
		boolean successFlag=false;
		int res=0;
		
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
		//String actualPassword=SecurityUtil.digestMd5(password);
		preparedStatementSave=connection.prepareStatement(ShdrcQueryList.Update_USER_VALUES);			
			//preparedStatementSave.setString(1,userName);
			preparedStatementSave.setString(1,firstName);
			preparedStatementSave.setString(2,lastName);
			//preparedStatementSave.setString(4,actualPassword);
			preparedStatementSave.setString(3,email);
			preparedStatementSave.setLong(4,mobile);
			preparedStatementSave.setString(5,designation);
			java.sql.Timestamp updatedOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementSave.setTimestamp(6,updatedOnDate);
			preparedStatementSave.setInt(7,userId);
			
			res=preparedStatementSave.executeUpdate();
			successFlag=true;
			connection.commit();
	    }catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  }finally{
				try {
	                if (preparedStatementSave != null) {
	                	preparedStatementSave.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }	
			}
		if(res==0)
		{
			successFlag=false;	
		}
		else{
			successFlag=true;
		}
		return successFlag;
	}



	@Override
	public boolean changePwdUserManagementValues(String userName,
			String newPassword, int userId, int derId) {
		Connection connection = null;
		PreparedStatement preparedStatementSave =null;
		
		ResultSet resultSet =null;
	
		boolean successFlag=false;
		int res=0;
		
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
		//String actualPassword=SecurityUtil.digestMd5(password);
		preparedStatementSave=connection.prepareStatement(ShdrcQueryList.Update_CHANGE_PWD_VALUES);			
			//preparedStatementSave.setString(1,userName);
			preparedStatementSave.setString(1,newPassword);
			java.sql.Timestamp updatedOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementSave.setTimestamp(2,updatedOnDate);
			preparedStatementSave.setInt(3,userId);
			//preparedStatementSave.setString(4,actualPassword);
			
			
			
			res=preparedStatementSave.executeUpdate();
			successFlag=true;
			connection.commit();
	    }catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  }finally{
				try {
	                if (preparedStatementSave != null) {
	                	preparedStatementSave.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }	
			}
		if(res==0)
		{
			successFlag=false;	
		}
		else{
			successFlag=true;
		}
		return successFlag;
	}
	
	
}

	
	
	



