/**
 * 
 */
package gov.shdrc.home.dao.jdbc;

import gov.shdrc.home.dao.IHomeDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.News;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.UserInfo;
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

/**
 * @author Upendra G
 *
 */
@Service
public class HomeDAOJdbc implements IHomeDAO {	

/*private static final Logger logger = Logger.getLogger(HomeDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
	
	public List<String> getNotificationList(boolean isAllDirectorate,String role){
		List<String> notificationList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String sqlQuery=null;
		try {
			String preNotification="";
			String notification=null;
			if(isAllDirectorate){
				sqlQuery="select \"Message\" from shdrc_data.\"Alert_Message\" alertmsg JOIN shdrc_dwh.\"Directorate_Master_Dim\" dir ON alertmsg.\"Directorate_Id\"=dir.\"Directorate_Id\"" +
						" where alertmsg.\"Message_Type\"='N' and alertmsg.\"Added_On\" between  current_date - interval '1' day and alertmsg.\"Added_On\" order by alertmsg.\"Added_On\" desc limit 10";
			}else{
				sqlQuery="select \"Message\" from shdrc_data.\"Alert_Message\" alertmsg JOIN shdrc_dwh.\"Directorate_Master_Dim\" dir ON alertmsg.\"Directorate_Id\"=dir.\"Directorate_Id\"" +
						" where alertmsg.\"Message_Type\"='N' and alertmsg.\"Added_On\" between  current_date - interval '1' day and alertmsg.\"Added_On\" and (dir.\"Directorate_Name\" in ("+role+") or alertmsg.\"Directorate_Id\"=-99 ) order by alertmsg.\"Added_On\" desc limit 10";
			}	
			
			notificationList=new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	notification=resultSet.getString(1);
	        	if(!preNotification.equalsIgnoreCase(notification)){
	        		notificationList.add(notification);
	        		preNotification=notification;
	        	}	
	        }
	        
	        if(notificationList.size()==0){
	        	notificationList=null;
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
	        
			return notificationList;
	}
	
	public List<String> getPreviousNotificationList(boolean isAllDirectorate,String role){
		List<String> previousNotificationList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String sqlQuery=null;
		String preNotification="";
		String notification=null;
		try {
			if(isAllDirectorate){
				sqlQuery="select \"Message\" from shdrc_data.\"Alert_Message\" alertmsg JOIN shdrc_dwh.\"Directorate_Master_Dim\" dir ON alertmsg.\"Directorate_Id\"=dir.\"Directorate_Id\"" +
						" where alertmsg.\"Message_Type\"='N' and current_date - interval '1' day > alertmsg.\"Added_On\" order by alertmsg.\"Added_On\" desc limit 10";
			}else{
				sqlQuery="select \"Message\" from shdrc_data.\"Alert_Message\" alertmsg JOIN shdrc_dwh.\"Directorate_Master_Dim\" dir ON alertmsg.\"Directorate_Id\"=dir.\"Directorate_Id\"" +
						" where alertmsg.\"Message_Type\"='N' and current_date - interval '1' day > alertmsg.\"Added_On\" and (dir.\"Directorate_Name\" in ("+role+") or alertmsg.\"Directorate_Id\"=-99 ) order by alertmsg.\"Added_On\" desc limit 10";
			}	
			
			previousNotificationList=new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	notification=resultSet.getString(1);
	        	if(!preNotification.equalsIgnoreCase(notification)){
	        		previousNotificationList.add(notification);
	        		preNotification=notification;
	        	}	
	        }
	        
	        if(previousNotificationList.size()==0){
	        	previousNotificationList=null;
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
	        
			return previousNotificationList;
	}
	
	public List<CommonStringList> getNewsList(boolean isAllDirectorate,String role){
		List<CommonStringList> previousNotificationList=null;
		CommonStringList commonStringList =null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String sqlQuery=null;
		String preMessageType="";
		String messageType=null;
		try {
			if(isAllDirectorate){
				sqlQuery="select \"Message_Id\",\"Message_Header\" from shdrc_data.\"Alert_Message\" alertmsg JOIN shdrc_dwh.\"Directorate_Master_Dim\" dir ON alertmsg.\"Directorate_Id\"=dir.\"Directorate_Id\"" +
						" where alertmsg.\"Message_Type\"='NS' order by alertmsg.\"Added_On\" desc limit 10";
			}else{
				sqlQuery="select \"Message_Id\",\"Message_Header\" from shdrc_data.\"Alert_Message\" alertmsg JOIN shdrc_dwh.\"Directorate_Master_Dim\" dir ON alertmsg.\"Directorate_Id\"=dir.\"Directorate_Id\"" +
						" where alertmsg.\"Message_Type\"='NS' and (dir.\"Directorate_Name\" in ("+role+") or alertmsg.\"Directorate_Id\"=-99 ) order by alertmsg.\"Added_On\" desc limit 10";
			}	
			
			previousNotificationList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	messageType=resultSet.getString(2);
	        	if(!preMessageType.equalsIgnoreCase(messageType)){
		        	commonStringList=new CommonStringList();
		        	commonStringList.setId(resultSet.getInt(1));
		        	commonStringList.setName(resultSet.getString(2));
		        	previousNotificationList.add(commonStringList);
		        	preMessageType=messageType;
	        	}	
	        }
	        
	        if(previousNotificationList.size()==0){
	        	previousNotificationList=null;
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
	        
			return previousNotificationList;
	}
	
	public News getNews(Integer messageId){
		News news =null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.GET_NEWS_INFO);
			preparedStatement.setInt(1, messageId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	news=new News();
	        	news.setMessageHeader(resultSet.getString(1));
	        	news.setMessage(resultSet.getString(2));
	        	news.setAddedBy(resultSet.getString(3));
	        	news.setAddedOn(Util.convertSqlDateToStrDate(resultSet.getString(4)));
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
			return news;
	}
}
