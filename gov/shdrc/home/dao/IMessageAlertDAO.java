package gov.shdrc.home.dao;

import org.springframework.stereotype.Service;

@Service
public interface IMessageAlertDAO {
	public boolean insertMessageAlert(String[] directorateIds,String sendSMS,String message,String messageType,String userName,String newsHeader);
}
