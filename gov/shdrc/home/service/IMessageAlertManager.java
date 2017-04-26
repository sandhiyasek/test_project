package gov.shdrc.home.service;

import org.springframework.stereotype.Service;

@Service
public interface IMessageAlertManager {
	public boolean insertMessageAlert(String[] directorateIds,String sendSMS,String message,String messageType,String userName,String newsHeader);
}
