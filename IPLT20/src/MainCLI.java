/**
 * 
 */

/**
 * @author Pranavan
 *
 */
public class MainCLI {
	
	private static DataAccess insertDataObj = new DataAccess();
	//private static DataAccess playerStatsObjDA = new DataAccess();
	//private static DataAccess playerVsPlayerObjDA = new DataAccess();
	//private static DataAccess venueStatsObjDA = new DataAccess();
	//private static DataAccess teamStatsObjDA = new DataAccess();
	//private static DataAccess teamVsTeamObjDA = new DataAccess();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		insertDataObj.insertData();
		//playerStatsObjDA.playerStatsData("Pandey", "Chennai Super Kings");
		//playerVsPlayerObjDA.playerVsPlayerData("Sehwag", "Kings XI Punjab", "Narine", "Kolkata Knight Riders");
		//teamStatsObjDA.teamStatsData("Kings XI Punjab");
		//venueStatsObjDA.venueStatsData("Sharjah");
		//teamVsTeamObjDA.teamVsTeamData("Kings XI Punjab", "Kolkata Knight Riders");
	}

}
