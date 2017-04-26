package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IPredictiveZoneDAO;
import gov.shdrc.util.ShdrcQueryList;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class PredictiveZoneDAOJdbc implements IPredictiveZoneDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	public JSONArray getIMRHeatMapTopLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapTopLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			imrHeatMapTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETIMRHEATMAP_TOPLEFTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setString(6, district);
			preparedStatement.setString(7, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			imrHeatMapTopLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapTopLeftData;
	}

	public JSONArray getIMRHeatMapTopRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapTopRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			imrHeatMapTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETIMRHEATMAP_TOPRIGHTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setArray(6, distArray);
			preparedStatement.setString(7, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 		
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			imrHeatMapTopRightData.put(obj);
		}
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapTopRightData;
	}

	public JSONArray getIMRHeatMapBottomLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapBottomLeftData=null;
		try{
			imrHeatMapBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETIMRHEATMAP_BOTTOMLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setArray(6, distArray);
			preparedStatement.setString(7, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			JSONObject obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			imrHeatMapBottomLeftData.put(obj);
		}		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapBottomLeftData;
	}

	public JSONArray getIMRHeatMapBottomRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapBottomRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			imrHeatMapBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETIMRHEATMAP_BOTTOMRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setString(6, district);
			preparedStatement.setString(7, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			imrHeatMapBottomRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapBottomRightData;
	}

	public List<String> getDistrictList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> district=null;
		try{
			district = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETDISTRICTLIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			district.add(resultSet.getString(1));
		}		
	} catch (SQLException e) {
		e.printStackTrace();
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
		return district;
	}

	public JSONArray getIMR2HeatMapTopLeftData(String indicator,
			String district, String userName,int cluster) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapTopLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			imrHeatMapTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETIMR2_HEATMAPTOPLEFTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, district);
			preparedStatement.setInt(3, cluster);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", resultSet.getInt(2));
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(3));
			imrHeatMapTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapTopLeftData;
	}
	
	public JSONArray getIMR2HeatMapTopRightData(String indicator,
			String district, String userName,int cluster) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imr2LineTopRightData=null;
		JSONObject obj=null;
		try{
			imr2LineTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETIMR2_LINETOPRIGHTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setArray(2, distArray);
			preparedStatement.setInt(3, cluster);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("year", resultSet.getInt(2));
			obj.put("value", resultSet.getInt(3));
			imr2LineTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imr2LineTopRightData;
	}

	public JSONArray getIMR2HeatMapBottomLeftData(String indicator,
			String district, String userName,int cluster) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapBottomRightData=null;
		JSONObject obj=null;
		String nextDist=null;
		int y=0;
		int x=1;
		try{
			imrHeatMapBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETIMR2_HEATMAPBOTTOMDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, district);
			preparedStatement.setInt(3, cluster);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			String prevDist=resultSet.getString(2);		
			if(prevDist.equalsIgnoreCase(nextDist)){
				x=x+1;
				obj.put("district", "");
				obj.put("xAxis", x);
				obj.put("yAxis", y);
			}
			else{				
				y=y+x;
				x=1;
				nextDist=prevDist;
				obj.put("district", prevDist);
				obj.put("xAxis", x);
				obj.put("yAxis", y);				
			}			
			obj.put("indicator", resultSet.getString(1));
			obj.put("value", resultSet.getInt(3));
			imrHeatMapBottomRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapBottomRightData;
	}
//DOB
	
	public List<String> getDiseaseList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> disease=null;
		try{
			disease = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETDISEASELIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			disease.add(resultSet.getString(1));
		}		
	} catch (SQLException e) {
		e.printStackTrace();
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
		return disease;
	}

	public JSONArray getDOBTopLeftData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray dobHeatMapTopLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			dobHeatMapTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETDOB_HEATMAPTOPLEFTDATA);
			Array diseaseArray=preparedStatement.getConnection().createArrayOf("text", disease.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setString(6, district);
			preparedStatement.setArray(7, diseaseArray);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();
		while(resultSet.next())
		{
			obj=new JSONObject();
			obj.put("rowid",resultSet.getString(2));
			obj.put("columnid",resultSet.getString(1));
			obj.put("value",resultSet.getInt(3));
			dobHeatMapTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return dobHeatMapTopLeftData;
	}

	public JSONArray getDOBTopRightData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray dobHeatMapTopRightData=null;
		JSONObject obj=null;
		try{
			dobHeatMapTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETDOB_HEATMAPTOPRIGHTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setArray(6, distArray);
			preparedStatement.setString(7, disease);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{
			obj=new JSONObject();
			obj.put("district", resultSet.getString(1));
			obj.put("disease", resultSet.getString(2));
			obj.put("value", resultSet.getInt(3));
			dobHeatMapTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return dobHeatMapTopRightData;
	}

	public JSONArray getDOBBottomLeftData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray dobLineBottomLeftData=null;
		JSONObject obj=null;
		try{
			dobLineBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETDOB_LINEBOTTOMLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setArray(6, distArray);
			preparedStatement.setString(7, disease);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("year", resultSet.getInt(2));
			obj.put("value", resultSet.getInt(3));
			dobLineBottomLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return dobLineBottomLeftData;
	}

	public JSONArray getDOBBottomRightData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray dobHeatMapBottomRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			dobHeatMapBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETDOB_HEATMAPBOTTTOMRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, fromMonth);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, toMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setString(6, district);
			preparedStatement.setString(7, disease);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(3));
			dobHeatMapBottomRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return dobHeatMapBottomRightData;
	}
//HOB
	public JSONArray getHOBHeatMapTopLeftData(String indicator,
			String month, int year, String district, String instType,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray HOBLineTopLeftData=null;
		JSONObject obj=null;
		try{
			HOBLineTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETHOB_LINETOPLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, month);
			preparedStatement.setInt(3, year);
			preparedStatement.setArray(4, distArray);
			preparedStatement.setArray(5, instArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			HOBLineTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return HOBLineTopLeftData;
	}

	public JSONArray getHOBHeatMapTopRightData(String indicator,
			String month, int year, String district, String instType,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray HOBBarRightLeftData=null;
		JSONObject obj=null;
		try{
			HOBBarRightLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETHOB_BARTOPRIGHTDATA);
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, month);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, district);
			preparedStatement.setArray(5, instArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			HOBBarRightLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return HOBBarRightLeftData;
	}

	public JSONArray getHOBHeatMapBottomData(String indicator, String month,
			int year, String district, String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapTopLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			imrHeatMapTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETHOB_BOTTOMDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, month);
			preparedStatement.setInt(3, year);
			preparedStatement.setArray(4, distArray);
			preparedStatement.setArray(5, instArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			imrHeatMapTopLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapTopLeftData;
	}
//BBSI
	public JSONArray getBBSIHeatMapTopLeftData(String indicator, String date,
			String instType, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BBSIBarTopLeftData=null;
		JSONObject obj=null;
		try{
			BBSIBarTopLeftData = new JSONArray();			
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETBBSI_BARTOPLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, date);
			preparedStatement.setArray(4, distArray);
			preparedStatement.setString(3, instType);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			BBSIBarTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return BBSIBarTopLeftData;
	}

	public JSONArray getBBSIHeatMapTopRightData(String indicator, String date,
			String instType, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BBSIBarTopRightData=null;
		JSONObject obj=null;
		try{
			BBSIBarTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETBBSI_BARTOPRIGHTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, date);
			preparedStatement.setArray(4, distArray);
			preparedStatement.setArray(3, instArray);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			BBSIBarTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return BBSIBarTopRightData;
	}

	public JSONArray getBBSIHeatMapBottomLeftData(String indicator,
			String date, String instType, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BBSIBottomLeftData=null;
		JSONObject obj=null;
		try{
			BBSIBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETBBSI_BOTTOMLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, date);
			preparedStatement.setArray(4, distArray);
			preparedStatement.setString(3, instType);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			BBSIBottomLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return BBSIBottomLeftData;
	}
//BBS
	public JSONArray getBBSTopLeftData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BBSTopLeftData=null;
		JSONObject obj=null;
		try{
			BBSTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETBBS_TOPLEFTDATA);
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, date);			
			preparedStatement.setArray(3, instArray);
			preparedStatement.setString(4, indCategory);
			preparedStatement.setString(5, district);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("instType", resultSet.getString(2));
			obj.put("seriesName", resultSet.getString(2));
			obj.put("value", resultSet.getInt(3));
			BBSTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return BBSTopLeftData;
	}

	public JSONArray getBBSTopRightData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BBSTopRightData=null;
		JSONObject obj=null;
		try{
			BBSTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETBBS_TOPRIGHTDATA);
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, date);			
			preparedStatement.setArray(3, instArray);
			preparedStatement.setString(4, indCategory);
			preparedStatement.setString(5, district);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			BBSTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return BBSTopRightData;
	}

	public JSONArray getBBSBottomLeftData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BBSBottomLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			BBSBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETBBS_BOTTOMLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, date);			
			preparedStatement.setArray(3, instArray);
			preparedStatement.setString(4, indCategory);
			preparedStatement.setArray(5, distArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			BBSBottomLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return BBSBottomLeftData;
	}

	public JSONArray getBBSBottomRightData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BBSBottomRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			BBSBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETBBS_BOTTOMRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, date);			
			preparedStatement.setString(3, instType);
			preparedStatement.setString(4, indCategory);
			preparedStatement.setString(5, district);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			BBSBottomRightData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return BBSBottomRightData;
	}

	public List<String> getIndCategoryList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> indCategory=null;
		try{
			indCategory = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETINDCATEGORYLIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			indCategory.add(resultSet.getString(1));
		}		
	} catch (SQLException e) {
		e.printStackTrace();
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
		return indCategory;
	}

//FIP
	public JSONArray getFIPTopLeftData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray FIPTopLeftData=null;
		JSONObject obj=null;
		try{
			FIPTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETFIP_TOPLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, year);			
			preparedStatement.setArray(3, distArray);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			FIPTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return FIPTopLeftData;
	}

	public JSONArray getFIPTopRightData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray FIPTopRightData=null;
		JSONObject obj=null;
		try{
			FIPTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETFIP_TOPRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, year);			
			preparedStatement.setString(3, district);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			FIPTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return FIPTopRightData;
	}

	public JSONArray getFIPBottomLeftData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray FIPBottomLeftData=null;
		JSONObject obj=null;
		try{
			FIPBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETFIP_BOTTOMLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, year);			
			preparedStatement.setArray(3, distArray);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			FIPBottomLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return FIPBottomLeftData;
	}

	public JSONArray getFIPBottomRightData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray FIPBottomRightData=null;
		JSONObject obj=null;
		try{
			FIPBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETFIP_BOTTOMRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, year);			
			preparedStatement.setString(3, district);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			FIPBottomRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return FIPBottomRightData;
	}

	public List<String> getFIPYearList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> yearList=null;
		try{
			yearList = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETYEARLIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			yearList.add(resultSet.getString(1));
		}		
	} catch (SQLException e) {
		e.printStackTrace();
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
		return yearList;
	}
	
//CDD
	public List<String> getFileTypeList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> fileTypeList=null;
		try{
			fileTypeList = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETFILETYPELIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			fileTypeList.add(resultSet.getString(1));
		}		
	} catch (SQLException e) {
		e.printStackTrace();
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
		return fileTypeList;
	}

	public List<String> getReasonList(String fileType) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> reasonList=null;
		try{
			reasonList = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETREASONLIST);
			preparedStatement.setString(1, fileType);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			reasonList.add(resultSet.getString(1));
		}		
	} catch (SQLException e) {
		e.printStackTrace();
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
		return reasonList;
	}

	public JSONArray getCDDTopLeftData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CDDTopLeftData=null;
		JSONObject obj=null;
		try{
			CDDTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCDD_TOPLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);			
			preparedStatement.setString(4, fileType);
			preparedStatement.setArray(5, distArray);
			preparedStatement.setString(6, reason);
			preparedStatement.setArray(7, instArray);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CDDTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CDDTopLeftData;
	}

	public JSONArray getCDDTopRightData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CDDTopRightData=null;
		JSONObject obj=null;
		try{
			CDDTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCDD_TOPRIGHTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);			
			preparedStatement.setString(4, fileType);
			preparedStatement.setArray(5, distArray);
			preparedStatement.setString(6, reason);
			preparedStatement.setArray(7, instArray);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CDDTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CDDTopRightData;
	}

	public JSONArray getCDDBottomLeftData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CDDBottomLeftData=null;
		JSONObject obj=null;
		try{
			CDDBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCDD_BOTTOMLEFTDATA);
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);			
			preparedStatement.setString(4, fileType);
			preparedStatement.setString(5, district);
			preparedStatement.setString(6, reason);
			preparedStatement.setArray(7, instArray);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CDDBottomLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CDDBottomLeftData;
	}

	public JSONArray getCDDBottomRightData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CDDBottomRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			CDDBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCDD_BOTTOMRIGHTDATA);
			Array instArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);			
			preparedStatement.setString(4, fileType);
			preparedStatement.setArray(5, distArray);
			preparedStatement.setString(6, reason);
			preparedStatement.setArray(7, instArray);
			preparedStatement.setString(8, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CDDBottomRightData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CDDBottomRightData;
	}
//CSP
	public JSONArray getCSPTopLeftData(String indicator, int year,
			String month, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CSPTopLeftData=null;
		JSONObject obj=null;
		try{
			CSPTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCSP_TOPLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setArray(4, distArray);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CSPTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CSPTopLeftData;
	}

	public JSONArray getCSPTopRightData(String indicator, int year,
			String month, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CSPTopRightData=null;
		JSONObject obj=null;
		try{
			CSPTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCSP_TOPRIGHTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setArray(4, distArray);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CSPTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CSPTopRightData;
	}

	public JSONArray getCSPBottomLeftData(String indicator, int year,
			String month, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CSPBottomLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			CSPBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCSP_BOTTOMLEFTDATA);
			Array distArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setArray(4, distArray);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CSPBottomLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CSPBottomLeftData;
	}

	public JSONArray getCSPBottomRightData(String indicator, int year,
			String month, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CSPBottomRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			CSPBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCSP_BOTTOMRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setString(4, district);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CSPBottomRightData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CSPBottomRightData;
	}
//CPP
	public JSONArray getCPPTopLeftData(String indicator, String instType,
			String district, String speciality, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CPPTopLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			CPPTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCPP_TOPLEFTDATA);
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setArray(2, instTypeArray);	
			preparedStatement.setString(3, district);		
			preparedStatement.setString(4, speciality);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CPPTopLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CPPTopLeftData;
	}

	public JSONArray getCPPTopRightData(String indicator, String instType,
			String district, String speciality, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CSPTopRightData=null;
		JSONObject obj=null;
		try{
			CSPTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCPP_TOPRIGHTDATA);
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setArray(2, instTypeArray);	
			preparedStatement.setString(3, district);		
			preparedStatement.setString(4, speciality);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CSPTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CSPTopRightData;
	}

	public JSONArray getCPPBottomLeftData(String indicator, String instType,
			String district, String speciality, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CPPBottomLeftData=null;
		JSONObject obj=null;
		try{
			CPPBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCPP_BOTTOMLEFTDATA);
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setArray(2, instTypeArray);
			preparedStatement.setString(3, district);		
			preparedStatement.setString(4, speciality);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CPPBottomLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CPPBottomLeftData;
	}

	public JSONArray getCPPBottomRightData(String indicator, String instType,
			String district, String speciality, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CPPBottomRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			CPPBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCPP_BOTTOMRIGHTDATA);
			Array districtArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setArray(2, instTypeArray);	
			preparedStatement.setArray(3, districtArray);		
			preparedStatement.setString(4, speciality);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CPPBottomRightData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CPPBottomRightData;
	}

	public List<String> getSpecialityList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> speciality=null;
		try{
			speciality = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETSPECIALITYLIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			speciality.add(resultSet.getString(1));
		}		
	} catch (SQLException e) {
		e.printStackTrace();
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
		return speciality;
	}
//CNP
	public JSONArray getCNPTopLeftData(String indicator, int year,
			String month, String district, String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CSPTopRightData=null;
		JSONObject obj=null;
		try{
			CSPTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCNP_TOPLEFTDATA);
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setString(4, district);
			preparedStatement.setArray(5, instTypeArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CSPTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CSPTopRightData;
	}

	public JSONArray getCNPTopRightData(String indicator, int year,
			String month, String district, String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CSPTopRightData=null;
		JSONObject obj=null;
		try{
			CSPTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCNP_TOPRIGHTDATA);
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setString(4, district);
			preparedStatement.setArray(5, instTypeArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CSPTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CSPTopRightData;
	}

	public JSONArray getCNPBottomLeftData(String indicator, int year,
			String month, String district, String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CNPBottomLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			CNPBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCNP_BOTTOMLEFTDATA);
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setString(4, district);
			preparedStatement.setArray(5, instTypeArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CNPBottomLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CNPBottomLeftData;
	}

	public JSONArray getCNPBottomRightData(String indicator, int year,
			String month, String district, String instType, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray CNPBottomRightData=null;
		JSONObject obj=null;
		int i=1;
		try{
			CNPBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETCNP_BOTTOMRIGHTDATA);
			Array instTypeArray=preparedStatement.getConnection().createArrayOf("text", instType.split(","));
			Array districtArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, month);		
			preparedStatement.setArray(4, districtArray);
			preparedStatement.setArray(5, instTypeArray);
			preparedStatement.setString(6, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			CNPBottomRightData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return CNPBottomRightData;
	}
	
	public JSONArray getMMRITopLeftData(String indicator,int year,String month,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MMRITopLeftData=null;
		JSONObject obj=null;
		try{
			MMRITopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMMRI_TOPLEFTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);
			preparedStatement.setString(3, month);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			MMRITopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MMRITopLeftData;
	}

	public JSONArray getMMRITopRightData(String indicator,int year, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MMRITopRightData=null;
		JSONObject obj=null;
		try{
			MMRITopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMMRI_TOPRIGHTDATA);
			preparedStatement.setString(1, indicator);	
			preparedStatement.setInt(2, year);	
			preparedStatement.setString(3, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("rowid",resultSet.getString(2));
			obj.put("columnid",resultSet.getString(1));
			obj.put("value",resultSet.getInt(3));						
			MMRITopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MMRITopRightData;
	}

	public JSONArray getMMRIBottomLeftData(String indicator, String district,
			String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MMRIBottomLeftData=null;
		JSONObject obj=null;
		try{
			MMRIBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMMRI_BOTTOMLEFTDATA);
			Array districtArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);	
			preparedStatement.setArray(2, districtArray);
			preparedStatement.setString(3, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("year", resultSet.getString(2));
			obj.put("value", resultSet.getInt(3));
			MMRIBottomLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MMRIBottomLeftData;
	}
	//MMR Operational
	public List<String> getReasonableIndList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> reasonableInd=null;
		try{
			reasonableInd = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETREASONABLEINDICATORLIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			reasonableInd.add(resultSet.getString(1));
		}		
		} catch (SQLException e) {
			e.printStackTrace();
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
		return reasonableInd;
	}

	public JSONArray getMMROTopLeftData(String indicator, int year,
			String month, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapTopLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			imrHeatMapTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMMRO_TOPLEFTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);
			preparedStatement.setString(3, month);
			preparedStatement.setString(4, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(3));
			imrHeatMapTopLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapTopLeftData;
	}

	public JSONArray getMMROTopRightData(String indicator, int year,
			String month, String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray imrHeatMapTopLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			imrHeatMapTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMMRO_TOPRIGHTDATA);
			Array districtArray=preparedStatement.getConnection().createArrayOf("text", district.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setInt(2, year);
			preparedStatement.setString(3, month);
			preparedStatement.setArray(4, districtArray);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			imrHeatMapTopLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return imrHeatMapTopLeftData;
	}

	public JSONArray getMMROBottomLeftData(String indicator,
			String reasonableInd, int year, String month, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MMROTopRightData=null;
		JSONObject obj=null;
		try{
			MMROTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMMRO_BOTTOMLEFTDATA);
			Array reasonableIndArray=preparedStatement.getConnection().createArrayOf("text", reasonableInd.split(","));
			preparedStatement.setString(1, indicator);
			preparedStatement.setArray(2, reasonableIndArray);
			preparedStatement.setInt(3, year);	
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("rowid",resultSet.getString(2));
			obj.put("columnid",resultSet.getString(1));
			obj.put("value",resultSet.getInt(3));						
			MMROTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MMROTopRightData;
	}

	public JSONArray getMMROBottomRightData(String indicator, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MMROBottomRightData=null;
		JSONObject obj=null;
		try{
			MMROBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMMRO_BOTTOMRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			MMROBottomRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MMROBottomRightData;
	}

	public JSONArray getMeaslesTopLeftData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MeaslesTopLeftData=null;
		JSONObject obj=null;
		try{
			MeaslesTopLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMEASLES_TOPLEFTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, year);
			preparedStatement.setString(3, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			MeaslesTopLeftData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MeaslesTopLeftData;
	}

	public JSONArray getMeaslesTopRightData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MeaslesTopRightData=null;
		JSONObject obj=null;
		try{
			MeaslesTopRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMEASLES_TOPRIGHTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, year);
			preparedStatement.setString(3, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			MeaslesTopRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MeaslesTopRightData;
	}

	public JSONArray getMeaslesBottomLeftData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray MeaslesBottomLeftData=null;
		JSONObject obj=null;
		int i=1;
		try{
			MeaslesBottomLeftData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMEASLES_BOTTOMLEFTDATA);
			preparedStatement.setString(1, indicator);
			preparedStatement.setString(2, year);
			preparedStatement.setString(3, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("xAxis", 1);
			obj.put("yAxis", i++);
			obj.put("district", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			MeaslesBottomLeftData.put(obj);
		}
		
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return MeaslesBottomLeftData;
	}

	public JSONArray getMeaslesBottomRightData(String indicator, String year,
			String district, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray measlesBottomRightData=null;
		JSONObject obj=null;
		try{
			measlesBottomRightData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.GETMEASLES_BOTTOMRIGHTDATA);
			preparedStatement.setString(1, indicator);		
			preparedStatement.setString(2, district);
			preparedStatement.setString(3, userName);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			obj.put("label", resultSet.getString(1));
			obj.put("value", resultSet.getInt(2));
			measlesBottomRightData.put(obj);
		}	
	} catch (SQLException | JSONException e) {
		e.printStackTrace();
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
		return measlesBottomRightData;
	}
}
