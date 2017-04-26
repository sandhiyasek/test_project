package gov.shdrc.reports.dao;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ISDGSDao {
	public JSONArray getNewColumnData(String month,String indicaor);
	public JSONArray getChartColumnHeader(String indicaor);
	public JSONArray heatMapData(String currentMonth,String previousMonth,String previousMonth1,String indicaor);
	public JSONArray getndAxisLineGTData(String indicaor);
	public JSONArray getndAxisLineLTData(String indicaor);
	public JSONArray ncaDel(String month,String indicaor);			
	public JSONArray del1(String month,String indicaor);
	public JSONArray anmst(String month,String indicaor);
	public JSONArray drisk(String month,String indicaor);
	public JSONArray getChartColumnRange(String indicaor);
	public JSONArray getInstDataByDist(String districtName,String month,String indicaor);
	public JSONArray getIndDataByDist(String districtName,String month,String indicaor);
	public JSONArray getDelDataByDist(String districtName,String previousMonth1,String indicaor);
	public JSONArray getLdDataByDist(String districtName,String month,String indicaor);
	public JSONArray getndDataByDist(String districtName,String month,String indicaor);
	public JSONArray getHMDataByDist(String districtName,String month,String previousMonth,String previousMonth1,String indicaor);
	public JSONArray getndAxisLineGTData1(String districtName,String month,String indicaor);
	public JSONArray getndAxisLineLTData1(String districtName,String month,String indicaor);
	public JSONArray getDistrictDataCd(String firstCDName,String month,Integer year);
	public JSONArray getInstDataCd(String firstCDDistName,String firstCDName,String month,Integer year);
	public JSONArray getDistrictDataNcd(String flag,String param);
	public JSONArray getcd();
	public JSONArray getncd();
	public JSONArray getInstitutionData(String districtName,String submitType,String generalName);
	public JSONArray getGrandChildDataNcd(String districtName,String submitType,String generalName);
	public JSONArray getGrandChildDataCd(String districtName,String submitType,String generalName);
	public JSONArray getDistrictDataNCd(String firstCDName,String month,Integer year);
	public JSONArray getInstDataNCd(String firstCDDistName,String firstCDName,String month,Integer year);
	
	//sdgs Dashboard
	public JSONArray getDistMaternalDeathData(int year,String month);
	public JSONArray getMaternalDeathMonVal(int year,String month);
	public JSONArray getMaternalDeathThreshold();
}
