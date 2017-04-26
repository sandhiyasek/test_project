/**
 * 
 */
package gov.shdrc.home.service.impl;

import gov.shdrc.home.dao.IHomeDAO;
import gov.shdrc.home.service.IHomeManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.News;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Upendra G
 *
 */
@Service
public class HomeManagerImpl implements IHomeManager {
@Autowired(required=true)
IHomeDAO homeDAO;

	/* (non-Javadoc)
	 * @see gov.shdrc.home.service.IHomeManager#getNotificationList()
	 */
	@Override
	public List<String> getNotificationList(boolean isAllDirectorate,String role) {		
		return homeDAO.getNotificationList(isAllDirectorate,role);
	}
	@Override
	public List<String> getPreviousNotificationList(boolean isAllDirectorate,String role) {		
		return homeDAO.getPreviousNotificationList(isAllDirectorate,role);
	}
	@Override
	public List<CommonStringList> getNewsList(boolean isAllDirectorate,String role) {		
		return homeDAO.getNewsList(isAllDirectorate,role);
	}
	public News getNews(Integer messageId){
		return homeDAO.getNews(messageId);
	}

}
