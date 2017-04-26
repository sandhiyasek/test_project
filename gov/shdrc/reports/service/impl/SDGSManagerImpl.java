package gov.shdrc.reports.service.impl;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.shdrc.reports.dao.ISDGSDao;
import gov.shdrc.reports.service.ISDGSManager;

@Service
public class SDGSManagerImpl implements ISDGSManager{

	@Autowired
	ISDGSDao SDGSDao;

	public JSONArray getNewColumnData(String month, String indicaor) {
		return SDGSDao.getNewColumnData(month, indicaor);
	}

	public JSONArray getChartColumnHeader(String indicaor) {
		return SDGSDao.getChartColumnHeader(indicaor);
	}

	public JSONArray heatMapData(String currentMonth, String previousMonth,
			String previousMonth1, String indicaor) {
		return SDGSDao.heatMapData(currentMonth, previousMonth, previousMonth1, indicaor);
	}

	public JSONArray getndAxisLineGTData(String indicaor) {
		return SDGSDao.getndAxisLineGTData(indicaor);
	}

	public JSONArray getndAxisLineLTData(String indicaor) {
		return SDGSDao.getndAxisLineLTData(indicaor);
	}

	public JSONArray ncaDel(String month, String indicaor) {
		return SDGSDao.ncaDel(month, indicaor);
	}

	public JSONArray del1(String month, String indicaor) {
		return SDGSDao.del1(month, indicaor);
	}

	public JSONArray anmst(String month, String indicaor) {
		return SDGSDao.anmst(month, indicaor);
	}

	public JSONArray drisk(String month, String indicaor) {
		return SDGSDao.drisk(month, indicaor);
	}

	public JSONArray getChartColumnRange(String indicaor) {
		return SDGSDao.getChartColumnRange(indicaor);
	}

	public JSONArray getInstDataByDist(String districtName, String month,
			String indicaor) {
		return SDGSDao.getInstDataByDist(districtName, month, indicaor);
	}

	public JSONArray getIndDataByDist(String districtName, String month,
			String indicaor) {
		return SDGSDao.getIndDataByDist(districtName, month, indicaor);
	}

	public JSONArray getDelDataByDist(String districtName,
			String previousMonth1, String indicaor) {
		return SDGSDao.getDelDataByDist(districtName, previousMonth1, indicaor);
	}

	public JSONArray getLdDataByDist(String districtName, String month,
			String indicaor) {
		return SDGSDao.getLdDataByDist(districtName, month, indicaor);
	}

	public JSONArray getndDataByDist(String districtName, String month,
			String indicaor) {
		return SDGSDao.getndDataByDist(districtName, month, indicaor);
	}

	public JSONArray getHMDataByDist(String districtName, String month,
			String previousMonth, String previousMonth1, String indicaor) {
		return SDGSDao.getHMDataByDist(districtName, month, previousMonth, previousMonth1, indicaor);
	}

	public JSONArray getndAxisLineGTData1(String districtName, String month,
			String indicaor) {
		return SDGSDao.getndAxisLineGTData1(districtName, month, indicaor);
	}

	public JSONArray getndAxisLineLTData1(String districtName, String month,
			String indicaor) {
		return SDGSDao.getndAxisLineLTData1(districtName, month, indicaor);
	}

	public JSONArray getDistrictDataCd(String firstCDName,String month,Integer year) {
		return SDGSDao.getDistrictDataCd(firstCDName,month,year);
	}
	
	public JSONArray getInstDataCd(String firstCDDistName,String firstCDName,String month,Integer year){
		return SDGSDao.getInstDataCd(firstCDDistName,firstCDName,month,year);
	}
	
	public JSONArray getDistrictDataNCd(String firstCDName,String month,Integer year) {
		return SDGSDao.getDistrictDataNCd(firstCDName,month,year);
	}
	
	public JSONArray getInstDataNCd(String firstCDDistName,String firstCDName,String month,Integer year){
		return SDGSDao.getInstDataNCd(firstCDDistName,firstCDName,month,year);
	}

	public JSONArray getDistrictDataNcd(String flag, String param) {
		return SDGSDao.getDistrictDataNcd(flag, param);
	}

	public JSONArray getcd() {
		return SDGSDao.getcd();
	}

	public JSONArray getncd() {
		return SDGSDao.getncd();
	}

	public JSONArray getInstitutionData(String districtName, String submitType,
			String generalName) {
		return SDGSDao.getInstitutionData(districtName, submitType, generalName);
	}

	public JSONArray getGrandChildDataNcd(String districtName,
			String submitType, String generalName) {
		return SDGSDao.getGrandChildDataNcd(districtName, submitType, generalName);
	}

	public JSONArray getGrandChildDataCd(String districtName,
			String submitType, String generalName) {
		return SDGSDao.getGrandChildDataCd(districtName, submitType, generalName);
	}

	public JSONArray getDistMaternalDeathData(int year, String month) {
		return SDGSDao.getDistMaternalDeathData(year, month);
	}

	public JSONArray getMaternalDeathMonVal(int year, String month) {
		return SDGSDao.getMaternalDeathMonVal(year, month);
	}

	public JSONArray getMaternalDeathThreshold() {
		return SDGSDao.getMaternalDeathThreshold();
	}
	
}
