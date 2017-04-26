package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IIndicatorMasterCalculationDAO;
import gov.shdrc.servicemaintenance.util.ShdrcServiceMaintenanceQueryList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.ShdrcConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IndicatorMasterCalculationDAOJdbc implements IIndicatorMasterCalculationDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JSONArray getIndicatorsList(Integer directorateId){
		JSONArray jsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			switch(directorateId){
				case ShdrcConstants.DirectorateId.DMS		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST_DMS);
					break;
				case ShdrcConstants.DirectorateId.DPH		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST_DPH);
					break;
				case ShdrcConstants.DirectorateId.TNMSC		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST_TNMSC);
					break;
				case ShdrcConstants.DirectorateId.NRHM		:	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST_NRHM);
					break;
				case ShdrcConstants.DirectorateId.DME		:   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST_DME);
					break;
				case ShdrcConstants.DirectorateId.DFW		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST_DFW);
					break;
				case ShdrcConstants.DirectorateId.COC		:  	preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST_COC);
					break;
				default                                     :   preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST);
				    break;
			}  
			/*preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATORS_LIST);*/
			preparedStatement.setInt(1, directorateId);
			
			resultSet = preparedStatement.executeQuery();
			jsonArray = getGridRecordsFromRS(resultSet);
			
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
	    return jsonArray;
	}
	
	public static JSONArray getGridRecordsFromRS(ResultSet resultSet){
		JSONArray jsonArray=null;
		try {
			JSONObject jsonObject =null;
			jsonArray= new JSONArray();					
			
			while (resultSet.next()) {
	        	jsonObject = new JSONObject();
	        	
	        	/*int indicatorId=resultSet.getInt(1);*/
	        	String indicatorName=resultSet.getString(1);
	        	/*jsonObject.put("indicatorId",indicatorId);*/
	        	//jsonObject.put("indicatorValue",indicatorName);
	        	jsonArray.put(indicatorName);
	        }
			if(jsonArray.length()==1){
				jsonArray=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}
		return jsonArray;
	}
	
	public boolean insertFormulaRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords,String userName)throws SHDRCException{ 
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.INSERT_FORMULA);
			String indicator=null;
			String type=null;
			String arithmetic=null;
			Integer constants=null;
			
			for(int i=0;i<gridRecords.length()-1;i++){
				if(!gridRecords.isNull(i)){
					JSONObject gridRow = gridRecords.getJSONObject(i);
					indicator=gridRow.has("indicator")?gridRow.getString("indicator"):null;
					type=gridRow.has("type")?gridRow.getString("type"):null;
					arithmetic=gridRow.has("arithmetic")?gridRow.getString("arithmetic"):null;
					constants=gridRow.has("constants")?gridRow.getInt("constants"):null;
						 preparedStatementInsert.setInt(1,directorateId);
						 preparedStatementInsert.setInt(2,indicatorId);
						 preparedStatementInsert.setString(3,indicator);
						 preparedStatementInsert.setString(4,type);
						 preparedStatementInsert.setString(5,arithmetic);
						 if(constants==null)
							preparedStatementInsert.setNull(6,java.sql.Types.INTEGER);
						else
							preparedStatementInsert.setInt(6,constants);
						 java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
						 preparedStatementInsert.setTimestamp(7,addOnDate);
						 preparedStatementInsert.setString(8,userName);
						 preparedStatementInsert.addBatch();
				}	
			}	
			preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();

	    } catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}finally{
				try {
	                if (preparedStatementInsert != null) {
	                	preparedStatementInsert.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }
	
			}
		return successFlag;
	}
	
	public JSONArray getFormulaDetails(Integer directorateId,Integer indicatorId){
		JSONArray formulaJsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_FORMULA_DETAILS);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setInt(2, indicatorId);
			
			resultSet = preparedStatement.executeQuery();
			formulaJsonArray = getFormulaGridRecordsFromRS(resultSet);
			
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
	    return formulaJsonArray;
	}
	
	public static JSONArray getFormulaGridRecordsFromRS(ResultSet resultSet){
		JSONArray formulaJsonArray=null;
		try {
			JSONObject jsonObject =null;
			formulaJsonArray= new JSONArray();			
			String indicator="";			
			String type="";		
			String arithmetic="";	
			int constants;
			
			while (resultSet.next()) {
	        	jsonObject = new JSONObject();
	        	
	        	indicator=resultSet.getString(1);
	        	type=resultSet.getString(2);
	        	arithmetic=resultSet.getString(3);
	        	constants=resultSet.getInt(4);
	        	jsonObject.put("indicator",indicator);
	        	jsonObject.put("type",type);
	        	jsonObject.put("arithmetic",arithmetic);
	        	jsonObject.put("constants",constants);
	        	formulaJsonArray.put(jsonObject);
	        }
			if(formulaJsonArray.length()==0){
				formulaJsonArray=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}
		return formulaJsonArray;
	}
	
	public boolean updateFormulaRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords)throws SHDRCException{ 
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.UPDATE_FORMULA);
			String indicator=null;
			String type=null;
			String arithmetic=null;
			Integer constants=null;
			
			for(int i=0;i<gridRecords.length()-1;i++){
				if(!gridRecords.isNull(i)){
					JSONObject gridRow = gridRecords.getJSONObject(i);
					indicator=gridRow.has("indicator")?gridRow.getString("indicator"):null;
					type=gridRow.has("type")?gridRow.getString("type"):null;
					arithmetic=gridRow.has("arithmetic")?gridRow.getString("arithmetic"):null;
					constants=gridRow.has("constants")?gridRow.getInt("constants"):null;
					
						 preparedStatementUpdate.setString(1,indicator);
						 preparedStatementUpdate.setString(2,type);
						 preparedStatementUpdate.setString(3,arithmetic);
						 if(constants==null)
							 preparedStatementUpdate.setNull(4,java.sql.Types.INTEGER);
							else
								preparedStatementUpdate.setInt(4,constants);
						 java.sql.Timestamp updateOnDate = new java.sql.Timestamp(new Date().getTime());
						 preparedStatementUpdate.setTimestamp(5,updateOnDate);
						 preparedStatementUpdate.setInt(6,directorateId);
						 preparedStatementUpdate.setInt(7,indicatorId);
						 preparedStatementUpdate.addBatch();
				}	
			}	
			preparedStatementUpdate.execute();
			successFlag=true;
			connection.commit();

	    } catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}finally{
				try {
	                if (preparedStatementUpdate != null) {
	                	preparedStatementUpdate.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }
	
			}
		return successFlag;
	}

}
