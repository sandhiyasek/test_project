/**
 * 
 */
package gov.shdrc.home.service.impl;

import gov.shdrc.home.dao.IMessageAlertDAO;
import gov.shdrc.home.service.IMessageAlertManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageAlertManagerImpl implements IMessageAlertManager {
@Autowired(required=true)
IMessageAlertDAO messageAlertDAO;

	public boolean insertMessageAlert(String[] directorateIds,String sendSMS,String message,String messageType,String userName,String newsHeader){
		return messageAlertDAO.insertMessageAlert(directorateIds,sendSMS,message,messageType,userName,newsHeader);
	}
}
