package gov.shdrc.home.dao;

import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.News;

import java.util.List;

import org.springframework.stereotype.Service;
/**
 * @author Upendra G
 *
 */
@Service
public interface IHomeDAO {
	public List<String> getNotificationList(boolean isAllDirectorate,String role);
	public List<String> getPreviousNotificationList(boolean isAllDirectorate,String role);
	public List<CommonStringList> getNewsList(boolean isAllDirectorate,String role);
	public News getNews(Integer messageId);
}
