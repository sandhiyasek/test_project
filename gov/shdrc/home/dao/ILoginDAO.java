package gov.shdrc.home.dao;

import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.News;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
/**
 * @author Upendra G
 *
 */
@Service
public interface ILoginDAO {
	public void insertLoginAudit(String clientIpAddress,String userName);
	public User getuserList(String userName);

}
