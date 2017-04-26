package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IDirectorateMasterDAO;
import gov.shdrc.servicemaintenance.util.DirectorateMaster;
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
public class DirectorateMasterDAOJdbc implements IDirectorateMasterDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean insertDirectorate(String directorateName,String directorateLevel,String userName)
			throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			boolean isDirectorateExists =isDirectorateExists(directorateName);
			 if(isDirectorateExists){
				throw new SHDRCException("The Directorate record already exists");
			 }
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.INSERT_DIRECTORATE);
			preparedStatementInsert.setString(1,directorateName);
			preparedStatementInsert.setString(2,directorateLevel);
			java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementInsert.setTimestamp(3,addOnDate);
			preparedStatementInsert.setString(4,userName);
			preparedStatementInsert.setInt(5,1);
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
	
	public List<DirectorateMaster> getDirectorateDetailsList(){
		DirectorateMaster directorateDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<DirectorateMaster> directorateDetailsList =null;
	try {
		directorateDetailsList = new ArrayList<DirectorateMaster>();
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.DIRECTORATE_LIST);
		resultSet = preparedStatement.executeQuery();
		String strUpdatedOn=null;
		while (resultSet.next()) {
			directorateDetails =new DirectorateMaster();
        	directorateDetails.setDirectorateName(resultSet.getString(1));
        	directorateDetails.setDirectorateLevel(resultSet.getString(2));        	
        	directorateDetails.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(3)));
        	strUpdatedOn=resultSet.getString(4);
        	directorateDetails.setUpdatedOn(strUpdatedOn!=null?Util.convertSqlDateToStrDate(strUpdatedOn):null);
        	//directorateDetails.setActive(resultSet.getInt(5));
        	directorateDetails.setDirectorateId(resultSet.getInt(5));
        	directorateDetailsList.add(directorateDetails);
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
	return directorateDetailsList;
	}
	
	public boolean updateDirectorate(int directorateId,String directorateName,String directorateLevel)
			throws SHDRCException{
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.UPDATE_DIRECTORATE);
			preparedStatementUpdate.setString(1,directorateName);
			preparedStatementUpdate.setString(2,directorateLevel);
			java.sql.Timestamp updateOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementUpdate.setTimestamp(3,updateOnDate);
			//preparedStatementUpdate.setInt(4,active);
			preparedStatementUpdate.setInt(4,directorateId);
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
	
	public boolean isDirectorateExists(String directorateName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		boolean isDirectorateExists=false;
		  ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.DIRECTORATE_EXISTS);
			preparedStatement.setString(1,directorateName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	long directorateCount=resultSet.getLong(1);
	        	isDirectorateExists =(directorateCount>0);
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
		return isDirectorateExists;		
	}
}
