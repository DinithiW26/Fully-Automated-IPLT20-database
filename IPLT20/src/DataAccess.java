//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataAccess {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/t20ipl2014";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";


	public PlayerStats playerStatsObj;
	public PlayerVsPlayer playerVsPlayerObj;
	public TeamStats teamStatsObj;
	public TeamVsTeam teamVsTeamObj;
	public VenueStats venueStatsObj;
	static List<String> teamNames = new ArrayList<String>();
	static List<String> venueNames = new ArrayList<String>();
	static List<String> teamPlayers = new ArrayList<String>();
	static List<String> batsmanList = new ArrayList<String>();
	static List<String> bowlerList = new ArrayList<String>();
	
	public void insertData() {
		Connection conn = null;
		Statement stmt00 = null, stmt01 = null, stmt10 = null, stmt11 = null, stmt20 = null, stmt21 = null, stmt30 = null, stmt31 = null;
		Statement stmt40 = null, stmt41 = null, stmt50 = null, stmt51 = null, stmt60 = null, stmt61 = null, stmt70 = null, stmt71 = null, stmt80 = null;
		GetURLContent object;
		int batFirstTeamId = 0,	batSecondTeamId = 0;
		int bowlerId = 0, batsmanId = 0;
		object = new GetURLContent();
		CommentaryData comDataObj;

		if(object.urlStr.contains("indian-premier-league-2014") && object.urlStr.contains("commentary")){
			try{
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");

				//STEP 3: Open a connection
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				//STEP 4: Execute a query
				System.out.println("Inserting records into the tables of database \"t20ipl2014\"...");
				stmt00 = conn.createStatement();
				stmt01 = conn.createStatement();
				stmt10 = conn.createStatement();
				stmt11 = conn.createStatement();
				stmt20 = conn.createStatement();
				stmt21 = conn.createStatement();
				stmt30 = conn.createStatement();
				stmt31 = conn.createStatement();
				stmt40 = conn.createStatement();
				stmt80 = conn.createStatement();
				String sql = "";
				ResultSet rs = null, rs2 = null, rs3 = null;

				if(object.team1 != null && object.team2 != null){
					sql = "SELECT COUNT(*) FROM team WHERE TeamName = " + "'" + object.team1 + "'";
					rs = stmt00.executeQuery(sql);
					while(rs.next()){
						if(rs.getInt("COUNT(*)") == 0){
							sql = "INSERT INTO team(TeamName)" + " values('" + object.team1 + "')";
							stmt01.executeUpdate(sql);
						}
					}

					sql = "SELECT COUNT(*) FROM team WHERE TeamName = " + "'" + object.team2 + "'";
					rs2 = stmt10.executeQuery(sql);
					while(rs2.next()){
						if(rs2.getInt("COUNT(*)") == 0){
							sql = "INSERT INTO team(TeamName)" + " values('" + object.team2 + "')";
							stmt11.executeUpdate(sql);
						}
					}
				}

				if(object.matchDetails.getBatFirstTeam() != null && object.matchDetails.getBatSecondTeam() != null){
					sql = "SELECT TeamID FROM team WHERE TeamName = '" + object.matchDetails.getBatFirstTeam() + "'";
					rs2 = stmt21.executeQuery(sql);
					while(rs2.next()){
						batFirstTeamId = rs2.getInt("TeamID");
					}
					sql = "SELECT TeamID FROM team WHERE TeamName = '" + object.matchDetails.getBatSecondTeam() + "'";
					rs3 = stmt30.executeQuery(sql);
					while(rs3.next()){
						batSecondTeamId = rs3.getInt("TeamID");
					}
					if(object.matchDetails.getMatchNumber() != 0){
						sql = "SELECT COUNT(*) FROM matches WHERE MatchNumber = " + "'" + object.matchDetails.getMatchNumber() + "'";
						rs = stmt20.executeQuery(sql);
						while(rs.next()){
							if(rs.getInt("COUNT(*)") == 0){
								if(object.matchDetails.getTeamWon().equals(object.matchDetails.getBatFirstTeam())){
									sql = "INSERT INTO matches" + " values('" + object.matchDetails.getMatchNumber() + "', '" + 
											object.matchDetails.getMatchDesc() + "', '" + object.matchDetails.getVenue() + "', '" 
											+ object.matchDetails.getMatchDate() + "', '" + batFirstTeamId + "', '" + batSecondTeamId + "', '" +
											batFirstTeamId + "', '" + object.matchDetails.getMatchResult() + "')";
									stmt31.executeUpdate(sql);
								}
								else if(object.matchDetails.getTeamWon().equals(object.matchDetails.getBatSecondTeam())){
									sql = "INSERT INTO matches" + " values('" + object.matchDetails.getMatchNumber() + "', '" + 
											object.matchDetails.getMatchDesc() + "', '" + object.matchDetails.getVenue() + "', '" 
											+ object.matchDetails.getMatchDate() + "', '" + batFirstTeamId + "', '" + batSecondTeamId + "', '" +
											batSecondTeamId + "', '" + object.matchDetails.getMatchResult() + "')";
									stmt40.executeUpdate(sql);
								}
								else{
									sql = "INSERT INTO matches" + " values('" + object.matchDetails.getMatchNumber() + "', '" + 
											object.matchDetails.getMatchDesc() + "', '" + object.matchDetails.getVenue() + "', '" 
											+ object.matchDetails.getMatchDate() + "', '" + batFirstTeamId + "', '" + batSecondTeamId + "', '0', '" 
											+ object.matchDetails.getMatchResult() + "')";
									stmt80.executeUpdate(sql);
								}
							}						 
						}
					}
				}

				// System.out.println(object.matchDetails.ballDetails.size());
				for(int i = 0; i < object.matchDetails.ballDetails.size(); i++){

					comDataObj = object.matchDetails.ballDetails.get(i);
					// System.out.println(comDataObj.getOver());
					stmt41 = conn.createStatement();
					stmt50 = conn.createStatement();
					stmt51 = conn.createStatement();
					stmt60 = conn.createStatement();
					stmt61 = conn.createStatement();
					stmt70 = conn.createStatement();
					stmt71 = conn.createStatement();

					if(comDataObj.getInnings() == 1 && batSecondTeamId != 0){
						sql = "SELECT COUNT(*) FROM Bowler WHERE BowlerName = " + "'" + comDataObj.getBowler() + "' AND TeamID = '" + batSecondTeamId + "'";
					}
					else if(comDataObj.getInnings() == 2 && batFirstTeamId != 0){
						sql = "SELECT COUNT(*) FROM Bowler WHERE BowlerName = " + "'" + comDataObj.getBowler() + "' AND TeamID = '" + batFirstTeamId + "'";
					}

					rs = stmt41.executeQuery(sql);
					while(rs.next()){
						if(rs.getInt("COUNT(*)") == 0){
							if(comDataObj.getInnings() == 1 && batSecondTeamId != 0){
								sql = "INSERT INTO Bowler(BowlerName, TeamID)" + " values('" + comDataObj.getBowler() + "', '" + batSecondTeamId + "')";
								stmt50.executeUpdate(sql);
							}
							else if(comDataObj.getInnings() == 2 && batFirstTeamId != 0){
								sql = "INSERT INTO Bowler(BowlerName, TeamID)" + " values('" + comDataObj.getBowler() + "', '" + batFirstTeamId + "')";
								stmt50.executeUpdate(sql);
							}
						}
					}

					if(comDataObj.getInnings() == 1 && batFirstTeamId != 0)
						sql = "SELECT COUNT(*) FROM Batsman WHERE BatsmanName = " + "'" + comDataObj.getBatsman() + "' AND TeamID = '" + batFirstTeamId + "'";
					else if(comDataObj.getInnings() == 2 && batSecondTeamId != 0)
						sql = "SELECT COUNT(*) FROM Batsman WHERE BatsmanName = " + "'" + comDataObj.getBatsman() + "' AND TeamID = '" + batSecondTeamId + "'";
					rs = stmt51.executeQuery(sql);
					while(rs.next()){
						if(rs.getInt("COUNT(*)") == 0){
							if(comDataObj.getInnings() == 1 && batFirstTeamId != 0){
								sql = "INSERT INTO Batsman(BatsmanName, TeamID)" + " values('" + comDataObj.getBatsman() + "', '" + batFirstTeamId + "')";
								stmt60.executeUpdate(sql);
							}
							else if(comDataObj.getInnings() == 2 && batSecondTeamId != 0){
								sql = "INSERT INTO Batsman(BatsmanName, TeamID)" + " values('" + comDataObj.getBatsman() + "', '" + batSecondTeamId + "')";
								stmt60.executeUpdate(sql);
							}
						}
					}

					if(object.matchDetails.getMatchNumber() != 0 && comDataObj.getBowler() != null && comDataObj.getBatsman() != null){
						sql = "SELECT COUNT(*) FROM CommentaryData WHERE (MatchNumber = " + "'" + object.matchDetails.getMatchNumber() + "') AND (Innings = '" +
								comDataObj.getInnings() + "') AND (Over <= '" + comDataObj.getOver() + "' + 1e-6) AND (Over >= '" + comDataObj.getOver() + "' - 1e-6) AND (Score = '" + comDataObj.getScore() +
								"') AND (Runs = '" + comDataObj.getRuns() + "')";
						rs = stmt61.executeQuery(sql);
						while(rs.next()){
							if(rs.getInt("COUNT(*)") == 0){
								if(comDataObj.getInnings() == 1 && batSecondTeamId != 0)
									sql = "SELECT BowlerID FROM Bowler WHERE BowlerName = '" + comDataObj.getBowler() + "' AND TeamID = '" + batSecondTeamId + "'";
								else if(comDataObj.getInnings() == 2 && batFirstTeamId != 0)
									sql = "SELECT BowlerID FROM Bowler WHERE BowlerName = '" + comDataObj.getBowler() + "' AND TeamID = '" + batFirstTeamId + "'";

								rs2 = stmt70.executeQuery(sql);
								while(rs2.next()){
									bowlerId = rs2.getInt("BowlerID");
								}
								if(comDataObj.getInnings() == 1 && batFirstTeamId != 0)
									sql = "SELECT BatsmanID FROM Batsman WHERE BatsmanName = '" + comDataObj.getBatsman() + "' AND TeamID = '" + batFirstTeamId + "'";
								else if(comDataObj.getInnings() == 2 && batSecondTeamId != 0)
									sql = "SELECT BatsmanID FROM Batsman WHERE BatsmanName = '" + comDataObj.getBatsman() + "' AND TeamID = '" + batSecondTeamId + "'";

								rs3 = stmt71.executeQuery(sql);
								while(rs3.next()){
									batsmanId = rs3.getInt("BatsmanID");
								}

								String updateString ="INSERT INTO CommentaryData(MatchNumber, Innings, Over, BowlerID, BatsmanID, Runs, Wicket, DismissalType, NOBall, Wide, LBBye, Four, Six, Score) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

								PreparedStatement preparedStatement = conn.prepareStatement(updateString);

								preparedStatement.setInt(1, object.matchDetails.getMatchNumber());
								preparedStatement.setInt(2, comDataObj.getInnings());
								preparedStatement.setFloat(3, comDataObj.getOver());
								preparedStatement.setInt(4, bowlerId);
								preparedStatement.setInt(5, batsmanId);
								preparedStatement.setInt(6, comDataObj.getRuns());
								preparedStatement.setBoolean(7, comDataObj.isWicket());
								preparedStatement.setString(8, comDataObj.getWicketType());
								preparedStatement.setBoolean(9, comDataObj.isNoBall());
								preparedStatement.setBoolean(10, comDataObj.isWide());
								preparedStatement.setBoolean(11, comDataObj.isLBBye());
								preparedStatement.setBoolean(12, comDataObj.isFour());
								preparedStatement.setBoolean(13, comDataObj.isSix());
								preparedStatement.setInt(14, comDataObj.getScore());


								preparedStatement .executeUpdate();
								/*  sql = "INSERT INTO CommentaryData" + " values('" + object.matchDetails.getMatchNumber() + "', '" + comDataObj.getInnings() + "', '" + 
							  comDataObj.getOver() + "', '" + bowlerId + "', '" + batsmanId + "', '" +
							  comDataObj.getRuns() + "', '" + comDataObj.isWicket() + "', '" + comDataObj.isNoBall() + "', '" + comDataObj.isWide() + 
							  "', '" + comDataObj.isLBBye() + "', '" + comDataObj.isFour() + "', '" + comDataObj.isSix() + "' , '" + comDataObj.getScore() + "')";
							  stmt80.executeUpdate(sql); */
								//code here
							}
						}
					}
				}
				//STEP 6: Clean-up environment
				//rs.close();
				//rs2.close();
				//stmt00.close();
				conn.close();
			}catch(SQLException se){
				//Handle errors for JDBC
				se.printStackTrace();
			}catch(Exception e){
				//Handle errors for Class.forName
				e.printStackTrace();
			}finally{
				//finally block used to close resources
				try{
					if(stmt00!=null)
						stmt00.close();
				}catch(SQLException se2){
				}// nothing we can do
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}//end finally try
			}//end try
			System.out.println("Successfully Done! Goodbye!");
		}
	}

	public void playerStatsData (String player, String team){

		playerStatsObj = new PlayerStats();

		Connection conn = null;
		Statement stmt00 = null, stmt01 = null, stmt10 = null, stmt11 = null, stmt20 = null, stmt21 = null, stmt30 = null, stmt31 = null;
		Statement stmt40 = null, stmt41 = null, stmt50 = null, stmt51 = null, stmt60 = null, stmt61 = null;

		playerStatsObj.setPlayerTeam(team);
		playerStatsObj.setPlayerName(player);

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();
			stmt01 = conn.createStatement();
			stmt10 = conn.createStatement();
			stmt11 = conn.createStatement();
			stmt20 = conn.createStatement();
			stmt21 = conn.createStatement();
			stmt30 = conn.createStatement();
			stmt31 = conn.createStatement();
			stmt40 = conn.createStatement();
			stmt41 = conn.createStatement();
			stmt50 = conn.createStatement();
			stmt51 = conn.createStatement();
			stmt60 = conn.createStatement();
			stmt61 = conn.createStatement();

			String sql;
			ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null, rs5 = null, rs6 = null, rs7 = null, rs8 = null, rs9 = null, rs10 = null, rs11 = null, rs12 = null, rs13 = null, rs14 = null;


			String checkBatsman = "SELECT COUNT(*), BatsmanID  FROM Batsman WHERE BatsmanName = '" + playerStatsObj.getPlayerName() + 
					"' AND TeamID = (SELECT TeamID FROM Team WHERE TeamName = '" + playerStatsObj.getPlayerTeam() + "')";

			int temp = 0;
			int batsmanID = 0;
			rs = stmt00.executeQuery(checkBatsman);
			while(rs.next()){
				temp = rs.getInt("COUNT(*)");
				if(temp != 0){ 	
					batsmanID = rs.getInt("BatsmanID");

					sql = "SELECT COUNT(DISTINCT MatchNumber) AS numOfInn, COUNT(DISTINCT MatchNumber, Innings, Over) AS numOfBalls, (SELECT COUNT(six) FROM commentarydata WHERE six = '1' AND BatsmanID = '" 
							+ batsmanID + "') AS numOfSixes, (SELECT COUNT(Wicket) FROM commentarydata WHERE Wicket = '1' AND BatsmanID = '" + batsmanID + 
							"') AS numOfOuts, (SELECT max(numOfRunsWithNBR - noBallRuns) FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" + 
							batsmanID + "') AS noBallRuns FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID + "') AS t1) AS runs FROM commentarydata WHERE BatsmanID = '" +   batsmanID + "'";

					rs2 = stmt01.executeQuery(sql);
					while(rs2.next()){
						playerStatsObj.setNumOfInningsPlayed_Batting(rs2.getInt("numOfInn"));
						playerStatsObj.setBallsFaced(rs2.getInt("numOfBalls"));
						playerStatsObj.setNumOfSixesHit(rs2.getInt("numOfSixes"));
						playerStatsObj.setRunsScored(rs2.getInt("runs"));
						if(rs2.getInt("numOfOuts") <= playerStatsObj.getNumOfInningsPlayed_Batting())
							playerStatsObj.setNumOfOuts(rs2.getInt("numOfOuts"));
						else
							playerStatsObj.setNumOfOuts(playerStatsObj.getNumOfInningsPlayed_Batting());
					}
					rs2.close();


					sql = "SELECT max(numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS bestScore FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '"
							+ batsmanID + "' GROUP BY MatchNumber) AS noBallRuns FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID + "' GROUP BY MatchNumber)as t";

					rs3 = stmt10.executeQuery(sql);
					while(rs3.next()){
						playerStatsObj.setBestScore(rs3.getInt("bestScore"));
					}
					rs3.close();


					sql = "SELECT BowlerName, teamName FROM bowler INNER JOIN team ON bowler.TeamID = team.TeamID WHERE BowlerID IN (SELECT BowlerID FROM (SELECT (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsScore, BowlerID FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" +
							batsmanID + "' GROUP BY BowlerID) AS noBallRuns, BowlerID FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID +
							"' GROUP BY BowlerID)as t)as bestAgainst WHERE runsScore = (SELECT MAX(numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS maxScore FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" + batsmanID + "' GROUP BY BowlerID) AS noBallRuns, BowlerID FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" +
							batsmanID + "' GROUP BY BowlerID)as t))";

					rs4 = stmt11.executeQuery(sql);
					while(rs4.next()){
						String bestAgainstBowlerName = rs4.getString("BowlerName");
						String bestAgainstBowlerTeam = rs4.getString("teamName");

						if(playerStatsObj.getBatting_BestAgainst() == null || playerStatsObj.getBatting_BestAgainst().isEmpty())
							playerStatsObj.setBatting_BestAgainst(bestAgainstBowlerName + " from " + bestAgainstBowlerTeam);
						else
							playerStatsObj.setBatting_BestAgainst(playerStatsObj.getBatting_BestAgainst() + ",\n" + bestAgainstBowlerName + " from " + bestAgainstBowlerTeam);
					}
					rs4.close();


					sql = "SELECT BowlerName, teamName FROM bowler INNER JOIN team ON bowler.TeamID = team.TeamID WHERE BowlerID IN (SELECT BowlerID FROM (SELECT BowlerID, COUNT(BowlerID) AS Wics FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BatsmanID = '" + batsmanID + 
							"' GROUP BY BowlerID ) AS weakAgainst WHERE Wics = (SELECT MAX(wicks) FROM (SELECT Count(*) as wicks FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BatsmanID = '" + batsmanID + "' GROUP BY BowlerID) as t))";

					rs5 = stmt20.executeQuery(sql);
					while(rs5.next()){
						String weakAgainstBowlerName = rs5.getString("BowlerName");
						String weakAgainstBowlerTeam = rs5.getString("teamName");

						if(playerStatsObj.getBatting_WeakAgainst() == null || playerStatsObj.getBatting_WeakAgainst().isEmpty())
							playerStatsObj.setBatting_WeakAgainst(weakAgainstBowlerName + " from " + weakAgainstBowlerTeam);
						else
							playerStatsObj.setBatting_WeakAgainst(playerStatsObj.getBatting_WeakAgainst() + ",\n" + weakAgainstBowlerName + " from " + weakAgainstBowlerTeam);
					}
					rs5.close();


					sql = "SELECT TeamName FROM team WHERE TeamID IN (SELECT DISTINCT TeamID FROM bowler INNER JOIN commentarydata ON bowler.BowlerID = commentarydata.BowlerID WHERE TeamID IN (SELECT TeamID FROM (SELECT (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS totalRunsAgainstTeam, TeamID FROM(SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" 
							+ batsmanID + "' GROUP BY TeamID) AS noBallRuns, TeamID FROM commentarydata INNER JOIN bowler ON bowler.BowlerID = commentarydata.BowlerID WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID +
							"' GROUP BY TeamID) AS strongAgainst ORDER BY totalRunsAgainstTeam) AS t WHERE totalRunsAgainstTeam = (SELECT MAX(numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS maxRunsAgainstTeam FROM(SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" + batsmanID +
							"' GROUP BY TeamID) AS noBallRuns, TeamID FROM commentarydata INNER JOIN bowler ON bowler.BowlerID = commentarydata.BowlerID WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID + "' GROUP BY TeamID) AS strongAgainst ORDER BY maxRunsAgainstTeam)))";

					rs6 = stmt21.executeQuery(sql);
					while(rs6.next()){
						if(playerStatsObj.getBatting_BestAgainstTeam() == null || playerStatsObj.getBatting_BestAgainstTeam().isEmpty())
							playerStatsObj.setBatting_BestAgainstTeam(rs6.getString("TeamName"));
						else
							playerStatsObj.setBatting_BestAgainstTeam(playerStatsObj.getBatting_BestAgainstTeam() + ",\n" + rs6.getString("TeamName"));
					}
					rs6.close();


					sql = "SELECT TeamName FROM team WHERE TeamID IN (SELECT DISTINCT TeamID FROM bowler INNER JOIN commentarydata ON bowler.BowlerID = commentarydata.BowlerID WHERE TeamID IN (SELECT TeamID FROM (SELECT (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS totalRunsAgainstTeam, TeamID FROM(SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" 
							+ batsmanID + "' GROUP BY TeamID) AS noBallRuns, TeamID FROM commentarydata INNER JOIN bowler ON bowler.BowlerID = commentarydata.BowlerID WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID +
							"' GROUP BY TeamID) AS weakAgainst ORDER BY totalRunsAgainstTeam) AS t WHERE totalRunsAgainstTeam = (SELECT MIN(numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS minRunsAgainstTeam FROM(SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" + batsmanID +
							"' GROUP BY TeamID) AS noBallRuns, TeamID FROM commentarydata INNER JOIN bowler ON bowler.BowlerID = commentarydata.BowlerID WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID + "' GROUP BY TeamID) AS weakAgainst ORDER BY minRunsAgainstTeam)))";

					rs7 = stmt30.executeQuery(sql);
					while(rs7.next()){
						if(playerStatsObj.getBatting_WeakAgainstTeam() == null || playerStatsObj.getBatting_WeakAgainstTeam().isEmpty())
							playerStatsObj.setBatting_WeakAgainstTeam(rs7.getString("TeamName"));
						else
							playerStatsObj.setBatting_WeakAgainstTeam(playerStatsObj.getBatting_WeakAgainstTeam() + ",\n" + rs6.getString("TeamName"));
					}
					rs7.close();

					if(playerStatsObj.getNumOfOuts() != 0){
						playerStatsObj.setSeriesBattingAvg((double)playerStatsObj.getRunsScored() / playerStatsObj.getNumOfOuts());
					}
					if(playerStatsObj.getBallsFaced() != 0){
						playerStatsObj.setOverAllBattingSR((double)playerStatsObj.getRunsScored() * 100 / playerStatsObj.getBallsFaced());
					}
				}

			}

			String checkBowler = "SELECT COUNT(*), BowlerID  FROM Bowler WHERE BowlerName = '" + playerStatsObj.getPlayerName() + 
					"' AND TeamID = (SELECT TeamID FROM Team WHERE TeamName = '" + playerStatsObj.getPlayerTeam() + "')";

			int bowlerID = 0;
			rs8 = stmt31.executeQuery(checkBowler);

			while(rs8.next()){
				temp = rs8.getInt("COUNT(*)");
				if(temp != 0){ 	
					bowlerID = rs8.getInt("BowlerID");

					sql = "SELECT COUNT(DISTINCT MatchNumber) AS numOfInn, COUNT(DISTINCT MatchNumber, Innings, Over) AS numOfBalls, (SELECT COUNT(Wicket) FROM commentarydata WHERE Wicket = '1' AND BowlerID = '" + bowlerID + 
							"' AND DismissalType <> 'Run out') AS numOfWics, (SELECT SUM(Runs) FROM commentarydata WHERE BowlerID = '" + bowlerID + "' AND LBBye = '0') AS runs FROM commentarydata WHERE BowlerID = '" + bowlerID + "'";

					rs9 = stmt40.executeQuery(sql);
					while(rs9.next()){
						playerStatsObj.setNumOfInningsPlayed_Bowling(rs9.getInt("numOfInn"));
						playerStatsObj.setBallsbowled(rs9.getInt("numOfBalls"));
						playerStatsObj.setNumOfWicTook(rs9.getInt("numOfWics"));
						playerStatsObj.setRunsConceeded(rs9.getInt("runs"));
					}
					rs9.close();

					sql = "SELECT COUNT(Wicket) AS maxNumOfWics FROM commentarydata WHERE Wicket = '1' AND BowlerID = '" + bowlerID + "' AND DismissalType <> 'Run out' GROUP BY MatchNumber ORDER BY maxNumOfWics DESC LIMIT 1";

					rs10 = stmt41.executeQuery(sql);
					while(rs10.next()){
						playerStatsObj.setMaxNumOfWicInAMatch(rs10.getInt("maxNumOfWics"));
					}
					rs10.close();


					sql = "SELECT BatsmanName, teamName FROM batsman INNER JOIN team ON batsman.TeamID = team.TeamID WHERE BatsmanID IN (SELECT BatsmanID FROM (SELECT BatsmanID, COUNT(*) AS Wics FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BowlerID = '" + bowlerID + 
							"' GROUP BY BatsmanID ) AS strongAgainst WHERE Wics = (SELECT MAX(wicks) FROM (SELECT Count(*) as wicks FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BowlerID = '" + bowlerID + "' GROUP BY BatsmanID) as t))";

					rs11 = stmt50.executeQuery(sql);
					while(rs11.next()){
						String BestAgainstBatsmanName = rs11.getString("BatsmanName");
						String BestAgainstBatsmanTeam = rs11.getString("teamName");

						if(playerStatsObj.getBowling_BestAgainst() == null || playerStatsObj.getBowling_BestAgainst().isEmpty())
							playerStatsObj.setBowling_BestAgainst(BestAgainstBatsmanName + " from " + BestAgainstBatsmanTeam);
						else
							playerStatsObj.setBowling_BestAgainst(playerStatsObj.getBowling_BestAgainst() + ",\n" + BestAgainstBatsmanName + " from " + BestAgainstBatsmanTeam);
					}
					rs11.close();


					sql = "SELECT TeamName FROM commentarydata INNER JOIN batsman ON batsman.BatsmanID = commentarydata.BatsmanID INNER JOIN team ON batsman.TeamID = team.TeamID WHERE Wicket = '1' AND DismissalType <> 'Run out' AND BowlerID = '" + bowlerID +
							"' GROUP BY team.TeamID HAVING COUNT(*) = (SELECT max(wics) FROM (SELECT COUNT(*) as wics FROM commentarydata INNER JOIN batsman ON batsman.BatsmanID = commentarydata.BatsmanID INNER JOIN team ON batsman.TeamID = team.TeamID WHERE Wicket = '1' AND DismissalType <> 'Run out' AND BowlerID = '" 
							+ bowlerID + "' GROUP BY team.TeamID) as t)";

					rs12 = stmt51.executeQuery(sql);
					while(rs12.next()){
						if(playerStatsObj.getBowling_BestAgainstTeam() == null || playerStatsObj.getBowling_BestAgainstTeam().isEmpty())
							playerStatsObj.setBowling_BestAgainstTeam(rs12.getString("TeamName"));
						else
							playerStatsObj.setBowling_BestAgainstTeam(playerStatsObj.getBowling_BestAgainstTeam() + ",\n" + rs12.getString("TeamName"));
					}
					rs12.close();


					sql = "SELECT TeamName FROM commentarydata INNER JOIN batsman ON batsman.BatsmanID = commentarydata.BatsmanID INNER JOIN team ON batsman.TeamID = team.TeamID WHERE Wicket = '1' AND DismissalType <> 'Run out' AND BowlerID = '" + bowlerID +
							"' GROUP BY team.TeamID HAVING COUNT(*) = (SELECT min(wics) FROM (SELECT COUNT(*) as wics FROM commentarydata INNER JOIN batsman ON batsman.BatsmanID = commentarydata.BatsmanID INNER JOIN team ON batsman.TeamID = team.TeamID WHERE Wicket = '1' AND DismissalType <> 'Run out' AND BowlerID = '" 
							+ bowlerID + "' GROUP BY team.TeamID) as t)";

					rs13 = stmt60.executeQuery(sql);
					while(rs13.next()){
						if(playerStatsObj.getBowling_WeakAgainstTeam() == null || playerStatsObj.getBowling_WeakAgainstTeam().isEmpty())
							playerStatsObj.setBowling_WeakAgainstTeam(rs13.getString("TeamName"));
						else
							playerStatsObj.setBowling_WeakAgainstTeam(playerStatsObj.getBowling_WeakAgainstTeam() + ",\n" + rs13.getString("TeamName"));
					}
					rs13.close();


					sql = "SELECT BatsmanName, teamName FROM batsman INNER JOIN team ON batsman.TeamID = team.TeamID WHERE BatsmanID IN (SELECT BatsmanID FROM (SELECT (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsGave, BatsmanID FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BowlerID = '" +
							bowlerID + "' GROUP BY BatsmanID) AS noBallRuns, BatsmanID FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BowlerID = '" + bowlerID +
							"' GROUP BY BatsmanID)as t)as weakAgainst WHERE runsGave = (SELECT MAX(numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS maxScore FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BowlerID = '" + bowlerID + "' GROUP BY BatsmanID) AS noBallRuns, BatsmanID FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BowlerID = '" +
							bowlerID + "' GROUP BY BatsmanID)as t))";

					rs14 = stmt61.executeQuery(sql);
					while(rs14.next()){
						String weakAgainstBatsmanName = rs14.getString("BatsmanName");
						String weakAgainstBatsmanTeam = rs14.getString("teamName");

						if(playerStatsObj.getBowling_WeakAgainst() == null || playerStatsObj.getBowling_WeakAgainst().isEmpty())
							playerStatsObj.setBowling_WeakAgainst(weakAgainstBatsmanName + " from " + weakAgainstBatsmanTeam);
						else
							playerStatsObj.setBowling_WeakAgainst(playerStatsObj.getBowling_WeakAgainst() + ",\n" + weakAgainstBatsmanName + " from " + weakAgainstBatsmanTeam);
					}
					rs14.close();

					if(playerStatsObj.getBallsbowled() != 0){
						playerStatsObj.setSeriesEcon((double)(playerStatsObj.getRunsConceeded() * 6) / playerStatsObj.getBallsbowled());
					}

					if(playerStatsObj.getNumOfWicTook() != 0){
						playerStatsObj.setSeriesBowlingAvg((double)(playerStatsObj.getRunsConceeded()) / playerStatsObj.getNumOfWicTook());
						playerStatsObj.setOverAllBowlingSR((double)(playerStatsObj.getBallsbowled()) / playerStatsObj.getNumOfWicTook());
					}

				}
			}
			conn.close();

			/*
			  System.out.println("/-- batting stats  --/\n");
			  System.out.println(playerStatsObj.getPlayerName() + "   : playerName");
			  System.out.println(playerStatsObj.getPlayerTeam() + "   : playerTeam");
			  System.out.println(playerStatsObj.getBallsFaced() + "   : getBallsFaced");
			  System.out.println(playerStatsObj.getRunsScored() + "   : getRunsScored");
			  System.out.println(playerStatsObj.getNumOfOuts() + "   : getNumOfOuts");
			  System.out.println(playerStatsObj.getNumOfInningsPlayed_Batting() + "   : getNumOfInningsPlayed_Batting");
			  System.out.println(playerStatsObj.getNumOfSixesHit() + "   : getNumOfSixesHit");
			  System.out.println(playerStatsObj.getBestScore() + "   : getBestScore");
			  System.out.println(playerStatsObj.getOverAllBattingSR() + "   : getOverAllBattingSR");
			  System.out.println(playerStatsObj.getSeriesBattingAvg() + "   : getSeriesBattingAvg");
			  System.out.println(playerStatsObj.getBatting_BestAgainst() + "   : getBatting_BestAgainst");
			  System.out.println(playerStatsObj.getBatting_WeakAgainst() + "   : getBatting_WeakAgainst");
			  System.out.println(playerStatsObj.getBatting_BestAgainstTeam() + "   : getBatting_BestAgainstTeam");
			  System.out.println(playerStatsObj.getBatting_WeakAgainstTeam() + "   : getBatting_WeakAgainstTeam\n\n");

			  System.out.println("/-- bowling stats  --/\n");
			  System.out.println(playerStatsObj.getNumOfInningsPlayed_Bowling() + "   : getNumOfInningsPlayed_Bowling");
			  System.out.println(playerStatsObj.getBallsbowled() + "   : getBallsbowled");
			  System.out.println(playerStatsObj.getRunsConceeded() + "   : getRunsConceeded");
			  System.out.println(playerStatsObj.getNumOfWicTook() + "   : getNumOfWicTook");
			  System.out.println(playerStatsObj.getMaxNumOfWicInAMatch() + "   : getMaxNumOfWicInAMatch");
			  System.out.println(playerStatsObj.getBowling_BestAgainst() + "   : getBowling_BestAgainst");
			  System.out.println(playerStatsObj.getBowling_WeakAgainst() + "   : getBowling_WeakAgainst");
			  System.out.println(playerStatsObj.getBowling_BestAgainstTeam() + "   : getBowling_BestAgainstTeam");
			  System.out.println(playerStatsObj.getBowling_WeakAgainstTeam() + "   : getBowling_WeakAgainstTeam");
			  System.out.println(playerStatsObj.getSeriesBowlingAvg() + "   : getSeriesBowlingAvg");
			  System.out.println(playerStatsObj.getSeriesEcon() + "   : getSeriesEcon");
			  System.out.println(playerStatsObj.getOverAllBowlingSR() + "   : getOverAllBowlingSR");
			 */


		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
	}


	public void playerVsPlayerData (String batsman, String batsmanTeam, String bowler, String bowlerTeam){

		playerVsPlayerObj = new PlayerVsPlayer();

		Connection conn = null;
		Statement stmt00 = null, stmt01 = null, stmt10 = null, stmt11 = null;

		playerVsPlayerObj.setBatsman(batsman);
		playerVsPlayerObj.setBatsmanTeam(batsmanTeam);
		playerVsPlayerObj.setBowler(bowler);
		playerVsPlayerObj.setBowlerTeam(bowlerTeam);

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();
			stmt01 = conn.createStatement();
			stmt10 = conn.createStatement();
			stmt11 = conn.createStatement();
			String sql;
			ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;


			String checkBatsmanID = "SELECT BatsmanID FROM Batsman WHERE BatsmanName = '" + playerVsPlayerObj.getBatsman() + 
					"' AND TeamID = (SELECT TeamID FROM Team WHERE TeamName = '" + playerVsPlayerObj.getBatsmanTeam() + "')";

			String checkBowlerID = "SELECT BowlerID FROM Bowler WHERE BowlerName = '" + playerVsPlayerObj.getBowler() + 
					"' AND TeamID = (SELECT TeamID FROM Team WHERE TeamName = '" + playerVsPlayerObj.getBowlerTeam() + "')";

			int batsmanID = 0;
			int bowlerID = 0;

			rs = stmt00.executeQuery(checkBatsmanID);
			while(rs.next()){
				batsmanID = rs.getInt("BatsmanID");
			}
			rs.close();

			rs2 = stmt01.executeQuery(checkBowlerID);
			while(rs2.next()){
				bowlerID = rs2.getInt("BowlerID");
			}
			rs2.close();


			sql = "SELECT COUNT(DISTINCT MatchNumber) AS numOfInn, COUNT(DISTINCT MatchNumber, Innings, Over) AS numOfBalls, (SELECT COUNT(six) FROM commentarydata WHERE six = '1' AND BatsmanID = '" 
					+ batsmanID + "' AND BowlerID = '" + bowlerID + "') AS numOfSixes, (SELECT COUNT(Wicket) FROM commentarydata WHERE Wicket = '1' AND DismissalType <> 'Run out' AND BatsmanID = '" + batsmanID + 
					"' AND BowlerID = '" + bowlerID + "') AS numOfOuts, (SELECT max(numOfRunsWithNBR - noBallRuns) FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '" + 
					batsmanID + "' AND BowlerID = '" + bowlerID + "') AS noBallRuns FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID + "' AND BowlerID = '" + bowlerID + "') AS t1) AS runs FROM commentarydata WHERE BatsmanID = '" +
					batsmanID + "' AND BowlerID = '" + bowlerID + "'";

			rs3 = stmt10.executeQuery(sql);
			while(rs3.next()){
				playerVsPlayerObj.setNoOfInnings(rs3.getInt("numOfInn"));
				playerVsPlayerObj.setNoOfBalls(rs3.getInt("numOfBalls"));
				playerVsPlayerObj.setNumOfSixesHit(rs3.getInt("numOfSixes"));
				playerVsPlayerObj.setNoOfRuns(rs3.getInt("runs"));
				playerVsPlayerObj.setNoOfOuts(rs3.getInt("numOfOuts"));
			}
			rs3.close();


			sql = "SELECT max(numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS bestScore FROM (SELECT SUM(Runs) AS numOfRunsWithNBR, (SELECT COUNT(NOBall) FROM commentarydata WHERE NOBall = '1' AND BatsmanID = '"
					+ batsmanID + "' AND BowlerID = '" + bowlerID + "' GROUP BY MatchNumber) AS noBallRuns FROM commentarydata WHERE Wide = '0' AND LBBye = '0' AND BatsmanID = '" + batsmanID + "' AND BowlerID = '" + bowlerID + "' GROUP BY MatchNumber)as t";

			rs4 = stmt11.executeQuery(sql);
			while(rs4.next()){
				playerVsPlayerObj.setBestScore(rs4.getInt("bestScore"));
			}
			rs4.close();


			if(playerVsPlayerObj.getNoOfOuts() != 0){
				playerVsPlayerObj.setOverAllAvg((double)playerVsPlayerObj.getNoOfRuns() / playerVsPlayerObj.getNoOfOuts());
				playerVsPlayerObj.setOverAllBowlingSR((double)(playerVsPlayerObj.getNoOfBalls()) / playerVsPlayerObj.getNoOfOuts());
			}
			if(playerVsPlayerObj.getNoOfBalls() != 0){
				playerVsPlayerObj.setOverAllBattingSR((double)playerVsPlayerObj.getNoOfRuns() * 100 / playerVsPlayerObj.getNoOfBalls());
				playerVsPlayerObj.setOverAllBowlingEcon((double)(playerVsPlayerObj.getNoOfRuns() * 6) / playerVsPlayerObj.getNoOfBalls());
			}

			conn.close();

			/*
			System.out.println(playerVsPlayerObj.getBatsman() + "   : getBatsman");
			System.out.println(playerVsPlayerObj.getBatsmanTeam() + "   : getBatsmanTeam");
			System.out.println(playerVsPlayerObj.getBowler() + "   : getBowler");
			System.out.println(playerVsPlayerObj.getBowlerTeam() + "   : getBowlerTeam");
			System.out.println(playerVsPlayerObj.getNoOfInnings() + "   : getNumOfInnings");
			System.out.println(playerVsPlayerObj.getNoOfBalls() + "   : getNoOfBalls");
			System.out.println(playerVsPlayerObj.getNoOfRuns() + "   : getNoOfRuns");
			System.out.println(playerVsPlayerObj.getNoOfOuts() + "   : getNumOfOuts");
			System.out.println(playerVsPlayerObj.getNumOfSixesHit() + "   : getNumOfSixesHit");
			System.out.println(playerVsPlayerObj.getBestScore() + "   : getBestScore");
			System.out.println(playerVsPlayerObj.getOverAllBattingSR() + "   : getOverAllBattingSR");
			System.out.println(playerVsPlayerObj.getOverAllAvg() + "   : getSeriesAvg");
			System.out.println(playerVsPlayerObj.getOverAllBowlingEcon() + "   : getOverAllBowlingEcon");
			System.out.println(playerVsPlayerObj.getOverAllBowlingSR() + "   : getOverAllBowlingSR");
			 */

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try		
	}


	public void venueStatsData (String venue){

		venueStatsObj = new VenueStats();

		Connection conn = null;
		Statement stmt00 = null, stmt01 = null, stmt10 = null, stmt11 = null, stmt20 = null;

		venueStatsObj.setVenueName(venue);

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();
			stmt01 = conn.createStatement();
			stmt10 = conn.createStatement();
			stmt11 = conn.createStatement();
			stmt20 = conn.createStatement();
			String sql;
			ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null, rs5 = null;


			sql = "SELECT COUNT(*) as NoOfMatches FROM Matches WHERE Venue = '" + venueStatsObj.getVenueName() + "'";

			rs = stmt00.executeQuery(sql);
			while(rs.next()){
				venueStatsObj.setNoOfMatches(rs.getInt("NoOfMatches"));
			}
			rs.close();


			sql = "SELECT TeamName as mostSuccessTeam FROM Team Where TeamID IN (SELECT TeamWonID FROM (SELECT TeamWonID, COUNT(*) as NumOfWins FROM matches WHERE Venue = '" + venueStatsObj.getVenueName() 
			+ "' GROUP BY TeamWonID)as t WHERE NumOfWins = (SELECT max(NumOfWins) FROM (SELECT COUNT(*) as NumOfWins FROM matches WHERE Venue = '" + venueStatsObj.getVenueName() + "' GROUP BY TeamWonID)as t1))";

			rs2 = stmt01.executeQuery(sql);
			while(rs2.next()){
				venueStatsObj.setMostSuccessfulTeam(rs2.getString("mostSuccessTeam"));
			}
			rs2.close();


			sql = "SELECT COUNT(*) as noOfWinsForBatFirstTeams, (SELECT COUNT(*) FROM matches WHERE BatSecondTeamID = TeamWonID AND Venue = '" + venueStatsObj.getVenueName() +
					"') as noOfWinsForBatSecondTeams FROM matches WHERE BatFirstTeamID = TeamWonID AND Venue = '" + venueStatsObj.getVenueName() + "'";

			rs3 = stmt10.executeQuery(sql);
			while(rs3.next()){
				venueStatsObj.setNoOfWinsForBatFirstTeams(rs3.getInt("noOfWinsForBatFirstTeams"));
				venueStatsObj.setNoOfWinsForBowlFirstTeams(rs3.getInt("noOfWinsForBatSecondTeams"));
			}
			rs3.close();


			sql = "SELECT sum(runs) as totalFirstInningsRuns FROM commentarydata INNER JOIN matches ON commentarydata.MatchNumber = matches.MatchNumber WHERE venue = '" 
					+ venueStatsObj.getVenueName() + "' AND Innings = '1'";

			rs4 = stmt11.executeQuery(sql);
			while(rs4.next()){
				venueStatsObj.setAvgFirstInningsScore((double)rs4.getInt("totalFirstInningsRuns") / venueStatsObj.getNoOfMatches());
			}
			rs4.close();


			sql = "SELECT sum(runs) as totalSecondInningsRuns FROM commentarydata INNER JOIN matches ON commentarydata.MatchNumber = matches.MatchNumber WHERE venue = '" 
					+ venueStatsObj.getVenueName() + "' AND Innings = '2'";

			rs5 = stmt20.executeQuery(sql);
			while(rs5.next()){
				venueStatsObj.setAvgSecondInningsScore((double)rs5.getInt("totalSecondInningsRuns") / venueStatsObj.getNoOfMatches());
			}
			rs5.close();

			if(venueStatsObj.getNoOfWinsForBatFirstTeams() > venueStatsObj.getNoOfWinsForBowlFirstTeams())
				venueStatsObj.setBetterTossDecision("Bat First");
			else if(venueStatsObj.getNoOfWinsForBatFirstTeams() < venueStatsObj.getNoOfWinsForBowlFirstTeams())
				venueStatsObj.setBetterTossDecision("Field First");
			else
				venueStatsObj.setBetterTossDecision("Either");

			conn.close();

			/*
			System.out.println(venueStatsObj.getVenueName() + "   : getVenueName");
			System.out.println(venueStatsObj.getNoOfMatches() + "   : getNoOfMatches");
			System.out.println(venueStatsObj.getMostSuccessfulTeam() + "   : getMostSuccessfulTeam");
			System.out.println(venueStatsObj.getNoOfWinsForBatFirstTeams() + "   : getNoOfWinsForBatFirstTeams");
			System.out.println(venueStatsObj.getNoOfWinsForBowlFirstTeams() + "   : getNoOfWinsForBowlFirstTeams");
			System.out.println(venueStatsObj.getAvgFirstInningsScore() + "   : getAvgFirstInningsScore");
			System.out.println(venueStatsObj.getAvgSecondInningsScore() + "   : getAvgSecondInningsScore");
			System.out.println(venueStatsObj.getBetterTossDecision() + "   : getBetterTossDecision");
			 */

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try		
	}


	public void teamStatsData (String teamName){

		teamStatsObj = new TeamStats();

		Connection conn = null;
		Statement stmt00 = null, stmt01 = null, stmt10 = null, stmt11 = null, stmt20 = null, stmt21 = null, stmt30 = null, stmt31 = null, stmt40 = null, stmt41 = null, stmt50 = null, stmt51 = null;

		teamStatsObj.setTeamName(teamName);
		int opponentID = 0;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();
			stmt01 = conn.createStatement();
			stmt10 = conn.createStatement();
			stmt11 = conn.createStatement();
			stmt20 = conn.createStatement();
			stmt21 = conn.createStatement();
			stmt30 = conn.createStatement();
			stmt31 = conn.createStatement();
			stmt40 = conn.createStatement();
			stmt41 = conn.createStatement();
			stmt50 = conn.createStatement();
			stmt51 = conn.createStatement();

			String sql;
			ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null, rs5 = null, rs6 = null, rs7 = null, rs8 = null, rs9 = null, rs10 = null, rs11 = null, rs12 = null;


			sql = "SELECT SUM(NoOfMatches) as totalNumOfMatches FROM ((SELECT COUNT(*) as NoOfMatches FROM Matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" +
					teamStatsObj.getTeamName() + "')) UNION (SELECT COUNT(*) as NoOfMatches FROM Matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" +
					teamStatsObj.getTeamName() + "'))) as t";

			rs = stmt00.executeQuery(sql);
			while(rs.next()){
				teamStatsObj.setNoOfMatchesPlayed(rs.getInt("totalNumOfMatches"));
			}
			rs.close();


			sql = "SELECT COUNT(*) as totalWins FROM Matches WHERE TeamWonID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() + "')";

			rs2 = stmt01.executeQuery(sql);
			while(rs2.next()){
				teamStatsObj.setNoOfWins(rs2.getInt("totalWins"));
			}
			rs2.close();


			sql = "SELECT COUNT(*) as totalDraws FROM Matches WHERE TeamWonID = '0' AND ( (BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() +
					"') ) OR (BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() + "') ) )";

			rs3 = stmt10.executeQuery(sql);
			while(rs3.next()){
				teamStatsObj.setNoOfLosses(teamStatsObj.getNoOfMatchesPlayed() - (teamStatsObj.getNoOfWins() + rs3.getInt("totalDraws")));
			}
			rs3.close();



			sql = "SELECT opponent FROM((SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage  FROM (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatSecondTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() +
					"') AND BatFirstTeamID = TeamWonID  GROUP BY BatSecondTeamID ) AS t2 ON t1.opponent = t2.opponent) UNION (SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage FROM (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatFirstTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() +
					"') AND BatSecondTeamID = TeamWonID  GROUP BY BatFirstTeamID ) AS t2 ON t1.opponent = t2.opponent) ) as t4 WHERE winPercentage = (SELECT MAX(winPercentage) FROM ((SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage  FROM (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatSecondTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() +
					"') AND BatFirstTeamID = TeamWonID  GROUP BY BatSecondTeamID ) AS t2 ON t1.opponent = t2.opponent) UNION (SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage FROM (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatFirstTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() + "') AND BatSecondTeamID = TeamWonID  GROUP BY BatFirstTeamID ) AS t2 ON t1.opponent = t2.opponent) ) as t3)";

			rs4 = stmt11.executeQuery(sql);

			while(rs4.next()){
				opponentID = rs4.getInt("opponent");
				sql = "SELECT TeamName FROM Team WHERE TeamID = '" + opponentID + "'";
				rs5 = stmt20.executeQuery(sql);

				while(rs5.next()){
					if(teamStatsObj.getBestAgainst() == null || teamStatsObj.getBestAgainst().isEmpty())
						teamStatsObj.setBestAgainst(rs5.getString("TeamName"));
					else
						teamStatsObj.setBestAgainst(teamStatsObj.getBestAgainst() + ",\n" + rs5.getString("TeamName"));
				}
				rs5.close();
			}
			rs4.close();




			sql = "SELECT opponent FROM((SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage  FROM (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatSecondTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() +
					"') AND BatFirstTeamID = TeamWonID  GROUP BY BatSecondTeamID ) AS t2 ON t1.opponent = t2.opponent) UNION (SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage FROM (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatFirstTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() +
					"') AND BatSecondTeamID = TeamWonID  GROUP BY BatFirstTeamID ) AS t2 ON t1.opponent = t2.opponent) ) as t4 WHERE winPercentage = (SELECT MIN(winPercentage) FROM ((SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage  FROM (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatSecondTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatSecondTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatFirstTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() +
					"') AND BatFirstTeamID = TeamWonID  GROUP BY BatSecondTeamID ) AS t2 ON t1.opponent = t2.opponent) UNION (SELECT t1.opponent, numOfMatchesAgainst, numOfWinsAgainst, (IFNULL(numOfWinsAgainst, 0) * 100 / numOfMatchesAgainst) AS winPercentage FROM (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfMatchesAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" 
					+ teamStatsObj.getTeamName() + "')  GROUP BY BatFirstTeamID ) AS t1 LEFT OUTER JOIN (SELECT BatFirstTeamID AS opponent, COUNT(*) AS numOfWinsAgainst FROM matches WHERE BatSecondTeamID = (SELECT TeamID FROM Team WHERE TEamName = '" + teamStatsObj.getTeamName() + "') AND BatSecondTeamID = TeamWonID  GROUP BY BatFirstTeamID ) AS t2 ON t1.opponent = t2.opponent) ) as t3)";

			rs6 = stmt21.executeQuery(sql);

			while(rs6.next()){
				opponentID = rs6.getInt("opponent");
				sql = "SELECT TeamName FROM Team WHERE TeamID = '" + opponentID + "'";
				rs7 = stmt30.executeQuery(sql);

				while(rs7.next()){
					if(teamStatsObj.getWeakAgainst() == null || teamStatsObj.getWeakAgainst().isEmpty())
						teamStatsObj.setWeakAgainst(rs7.getString("TeamName"));
					else
						teamStatsObj.setWeakAgainst(teamStatsObj.getWeakAgainst() + ",\n" + rs7.getString("TeamName"));
				}
				rs7.close();

			}
			rs6.close();


			sql = "SELECT bowlerName FROM bowler WHERE BowlerID IN (SELECT BowlerID FROM (SELECT BowlerID, COUNT(*) as NumOfWics FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = (SELECT TeamID FROM team WHERE TeamName = '" 
					+ teamStatsObj.getTeamName() + "')) GROUP BY BowlerID) as t WHERE NumOfWics = (SELECT max(NumOfWics) FROM (SELECT BowlerID, COUNT(*) as NumOfWics FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = (SELECT TeamID FROM team WHERE TeamName = '" 
					+ teamStatsObj.getTeamName() + "')) GROUP BY BowlerID) as t1))";

			rs8 = stmt31.executeQuery(sql);

			while(rs8.next()){
				if(teamStatsObj.getBestBowler() == null || teamStatsObj.getBestBowler().isEmpty())
					teamStatsObj.setBestBowler(rs8.getString("bowlerName"));
				else
					teamStatsObj.setBestBowler(teamStatsObj.getBestBowler() + ",\n" + rs8.getString("bowlerName"));
			}
			rs8.close();

			int teamId = 0;
			sql = "SELECT TeamID FROM Team WHERE TeamName = '" + teamStatsObj.getTeamName() + "'";

			rs9 = stmt40.executeQuery(sql);

			while(rs9.next()){
				teamId = rs9.getInt("TeamID");
			}
			rs9.close();


			sql = "SELECT batsmanName FROM (SELECT t1.BatsmanID, t1.batsmanName, (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsScored FROM (SELECT batsman.BatsmanID, batsmanName, SUM(Runs) AS numOfRunsWithNBR FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE batsman.TeamID = '" 
					+ teamId + "' GROUP BY commentarydata.BatsmanID ) as t1 LEFT OUTER JOIN (SELECT batsman.BatsmanID, batsmanName, COUNT(NOBall) AS noBallRuns FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE NOBall = '1' AND batsman.TeamID = '" + teamId +
					"' GROUP BY commentarydata.BatsmanID)as t2 ON t1.batsmanID = t2.batsmanID) as t3 WHERE runsScored = (SELECT max(runsScored) FROM ((SELECT t1.BatsmanID, t1.batsmanName, (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsScored FROM (SELECT batsman.BatsmanID, batsmanName, SUM(Runs) AS numOfRunsWithNBR FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE batsman.TeamID = '" 
					+ teamId + "' GROUP BY commentarydata.BatsmanID ) as t1 LEFT OUTER JOIN (SELECT batsman.BatsmanID, batsmanName, COUNT(NOBall) AS noBallRuns FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE NOBall = '1' AND batsman.TeamID = '" + teamId + "' GROUP BY commentarydata.BatsmanID)as t2 ON t1.batsmanID = t2.batsmanID) as t4))";

			rs10 = stmt41.executeQuery(sql);

			while(rs10.next()){
				if(teamStatsObj.getBestBatsman() == null || teamStatsObj.getBestBatsman().isEmpty())
					teamStatsObj.setBestBatsman(rs10.getString("batsmanName"));
				else
					teamStatsObj.setBestBatsman(teamStatsObj.getBestBatsman() + ",\n" + rs10.getString("batsmanName"));
			}
			rs10.close();


			List<String> performanceOrderBatting = new ArrayList<String>();
			List<String> performanceOrderBowling = new ArrayList<String>();


			sql = "SELECT BatsmanName FROM (SELECT t1.BatsmanID, t1.batsmanName, (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsScored FROM (SELECT batsman.BatsmanID, batsmanName, SUM(Runs) AS numOfRunsWithNBR FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE batsman.TeamID = '" 
					+ teamId + "' GROUP BY commentarydata.BatsmanID ) as t1 LEFT OUTER JOIN (SELECT batsman.BatsmanID, batsmanName, COUNT(NOBall) AS noBallRuns FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE NOBall = '1' AND batsman.TeamID = '" + teamId +
					"' GROUP BY commentarydata.BatsmanID)as t2 ON t1.batsmanID = t2.batsmanID ORDER BY runsScored DESC) as t3";

			rs11 = stmt50.executeQuery(sql);

			while(rs11.next()){
				performanceOrderBatting.add(rs11.getString("BatsmanName"));
			}
			rs11.close();


			sql = "SELECT bowlerName FROM (SELECT commentarydata.BowlerID,bowlerName, COUNT(*) as NumOfWics FROM commentarydata INNER JOIN bowler ON bowler.BowlerID = commentarydata.BowlerID WHERE wicket = '1' AND DismissalType <> 'Run out' AND commentarydata.BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = (SELECT TeamID FROM team WHERE TeamName = '" 
					+ teamStatsObj.getTeamName() + "')) GROUP BY commentarydata.BowlerID ORDER BY NumOfWics DESC) as t";

			rs12 = stmt51.executeQuery(sql);

			while(rs12.next()){
				performanceOrderBowling.add(rs12.getString("bowlerName"));
			}
			rs12.close();

			for(int i = 0; i < 6; i ++){
				teamStatsObj.bestSquad.add(performanceOrderBatting.get(i));
			}

			for(int i = 0; teamStatsObj.bestSquad.size() < 11; i ++){
				teamStatsObj.bestSquad.add(performanceOrderBowling.get(i));
			}

			//System.out.println(teamStatsObj.bestSquad.size());


			/*	
		 	System.out.println(teamStatsObj.getTeamName() + "   : getTeamName");
			System.out.println(teamStatsObj.getNoOfMatchesPlayed() + "   : getNoOfMatches");
			System.out.println(teamStatsObj.getNoOfWins() + "   : getNoOfWins");
			System.out.println(teamStatsObj.getNoOfLosses() + "   : getNoOfLosses");
			System.out.println(teamStatsObj.getBestAgainst() + "   : getBestAgainst");
			System.out.println(teamStatsObj.getWeakAgainst() + "   : getWeakAgainst");
			System.out.println(teamStatsObj.getBestBatsman() + "   : getBestBatsman");
			System.out.println(teamStatsObj.getBestBowler() + "   : getBestBowler");

			for(int i = 0; i < 11; i ++){
				System.out.println("Player " + (i + 1) + " :  " + teamStatsObj.bestSquad.get(i));
			}
			 */

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try		
	}


	public void teamVsTeamData (String yourTeam, String opponent){

		teamVsTeamObj = new TeamVsTeam();

		Connection conn = null;
		Statement stmt00 = null, stmt01 = null, stmt10 = null, stmt11 = null, stmt20 = null, stmt21 = null, stmt30 = null, stmt50 = null, stmt51 = null;

		teamVsTeamObj.setYourTeam(yourTeam);
		teamVsTeamObj.setOpponent(opponent);

		int yourTeamID = 0;
		int opponentID = 0;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();
			stmt01 = conn.createStatement();
			stmt10 = conn.createStatement();
			stmt11 = conn.createStatement();
			stmt20 = conn.createStatement();
			stmt21 = conn.createStatement();
			stmt30 = conn.createStatement();
			stmt50 = conn.createStatement();
			stmt51 = conn.createStatement();

			String sql;
			ResultSet rs = null, rs1 = null, rs2 = null, rs3 = null, rs4 = null, rs5 = null, rs6 = null, rs11 = null, rs12 = null;


			sql = "SELECT TeamID FROM team WHERE TeamName = '" + teamVsTeamObj.getYourTeam() + "'";

			rs = stmt00.executeQuery(sql);
			while(rs.next()){
				yourTeamID = rs.getInt("TeamID");
			}
			rs.close();


			sql = "SELECT TeamID FROM team WHERE TeamName = '" + teamVsTeamObj.getOpponent() + "'";

			rs1 = stmt01.executeQuery(sql);
			while(rs1.next()){
				opponentID = rs1.getInt("TeamID");
			}
			rs1.close();


			sql = "SELECT SUM(NoOfMatches) as totalNumOfMatches FROM ((SELECT COUNT(*) as NoOfMatches, matchNumber FROM Matches WHERE BatFirstTeamID = '" 
					+ yourTeamID + "' AND BatSecondTeamID = '" + opponentID + "') UNION (SELECT COUNT(*) as NoOfMatches, matchNumber FROM Matches WHERE BatSecondTeamID = '" 
					+ yourTeamID + "' AND BatFirstTeamID = '" + opponentID + "')) as t";

			rs2 = stmt10.executeQuery(sql);
			while(rs2.next()){
				teamVsTeamObj.setNoOfMatches(rs2.getInt("totalNumOfMatches"));
			}
			rs2.close();


			sql = "SELECT COUNT(*) as totalWins FROM Matches WHERE TeamWonID = '" + yourTeamID + "' AND (BatFirstTeamID = '" + opponentID + "' OR BatSecondTeamID = '" + opponentID + "')";

			rs3 = stmt11.executeQuery(sql);
			while(rs3.next()){
				teamVsTeamObj.setNoOfWins(rs3.getInt("totalWins"));
			}
			rs3.close();


			sql = "SELECT COUNT(*) as totalDraws FROM Matches WHERE TeamWonID = '0' AND ((BatFirstTeamID = '" + yourTeamID + "' AND BatSecondTeamID = '" + opponentID + "') OR (BatFirstTeamID = '" + opponentID + "' AND BatSecondTeamID = '" + yourTeamID + "'))";

			rs4 = stmt20.executeQuery(sql);
			while(rs4.next()){
				teamVsTeamObj.setNoOfLosses(teamVsTeamObj.getNoOfMatches() - (teamVsTeamObj.getNoOfWins() + rs4.getInt("totalDraws")));
			}
			rs4.close();



			sql = "SELECT bowlerName FROM bowler WHERE BowlerID IN (SELECT BowlerID FROM (SELECT BowlerID, COUNT(*) as NumOfWics FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" 
					+ yourTeamID + "') AND BatsmanID IN (SELECT BatsmanID FROM batsman WHERE TeamID = '" + opponentID +
					"') GROUP BY BowlerID) as t WHERE NumOfWics = (SELECT max(NumOfWics) FROM (SELECT BowlerID, COUNT(*) as NumOfWics FROM commentarydata WHERE wicket = '1' AND DismissalType <> 'Run out' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" 
					+ yourTeamID + "') AND BatsmanID IN (SELECT BatsmanID FROM batsman WHERE TeamID = '" + opponentID + "') GROUP BY BowlerID) as t1))";

			rs5 = stmt21.executeQuery(sql);

			while(rs5.next()){
				if(teamVsTeamObj.getBestBowler() == null || teamVsTeamObj.getBestBowler().isEmpty())
					teamVsTeamObj.setBestBowler(rs5.getString("bowlerName"));
				else
					teamVsTeamObj.setBestBowler(teamVsTeamObj.getBestBowler() + ",\n" + rs5.getString("bowlerName"));
			}
			rs5.close();


			sql = "SELECT batsmanName FROM (SELECT t1.BatsmanID, t1.batsmanName, (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsScored FROM (SELECT batsman.BatsmanID, batsmanName, SUM(Runs) AS numOfRunsWithNBR FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE batsman.TeamID = '" 
					+ yourTeamID + "' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" + opponentID + "')  GROUP BY commentarydata.BatsmanID ) as t1 LEFT OUTER JOIN (SELECT batsman.BatsmanID, batsmanName, COUNT(NOBall) AS noBallRuns FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE NOBall = '1' AND batsman.TeamID = '" + yourTeamID +
					"' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" + opponentID +
					"') GROUP BY commentarydata.BatsmanID)as t2 ON t1.batsmanID = t2.batsmanID) as t3 WHERE runsScored = (SELECT max(runsScored) FROM ((SELECT t1.BatsmanID, t1.batsmanName, (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsScored FROM (SELECT batsman.BatsmanID, batsmanName, SUM(Runs) AS numOfRunsWithNBR FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE batsman.TeamID = '" 
					+ yourTeamID + "' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" + opponentID + "') GROUP BY commentarydata.BatsmanID ) as t1 LEFT OUTER JOIN (SELECT batsman.BatsmanID, batsmanName, COUNT(NOBall) AS noBallRuns FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE NOBall = '1' AND batsman.TeamID = '" + yourTeamID +
					"' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" + opponentID + "') GROUP BY commentarydata.BatsmanID)as t2 ON t1.batsmanID = t2.batsmanID) as t4))";

			rs6 = stmt30.executeQuery(sql);

			while(rs6.next()){
				if(teamVsTeamObj.getBestBatsman() == null || teamVsTeamObj.getBestBatsman().isEmpty())
					teamVsTeamObj.setBestBatsman(rs6.getString("batsmanName"));
				else
					teamVsTeamObj.setBestBatsman(teamVsTeamObj.getBestBatsman() + ",\n" + rs6.getString("batsmanName"));
			}
			rs6.close();


			List<String> performanceOrderBatting = new ArrayList<String>();
			List<String> performanceOrderBowling = new ArrayList<String>();


			sql = "SELECT BatsmanName FROM (SELECT t1.BatsmanID, t1.batsmanName, (numOfRunsWithNBR - IFNULL(noBallRuns, 0)) AS runsScored FROM (SELECT batsman.BatsmanID, batsmanName, SUM(Runs) AS numOfRunsWithNBR FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE batsman.TeamID = '" 
					+ yourTeamID + "' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" + opponentID + "') GROUP BY commentarydata.BatsmanID ) as t1 LEFT OUTER JOIN (SELECT batsman.BatsmanID, batsmanName, COUNT(NOBall) AS noBallRuns FROM batsman INNER JOIN commentarydata ON batsman.BatsmanID = commentarydata.BatsmanID WHERE NOBall = '1' AND batsman.TeamID = '" 
					+ yourTeamID + "' AND BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = '" + opponentID + "') GROUP BY commentarydata.BatsmanID)as t2 ON t1.batsmanID = t2.batsmanID ORDER BY runsScored DESC) as t3";

			rs11 = stmt50.executeQuery(sql);

			while(rs11.next()){
				performanceOrderBatting.add(rs11.getString("BatsmanName"));
			}
			rs11.close();


			sql = "SELECT bowlerName FROM (SELECT commentarydata.BowlerID,bowlerName, COUNT(*) as NumOfWics FROM commentarydata INNER JOIN bowler ON bowler.BowlerID = commentarydata.BowlerID WHERE wicket = '1' AND DismissalType <> 'Run out' AND commentarydata.BowlerID IN (SELECT BowlerID FROM bowler WHERE TeamID = (SELECT TeamID FROM team WHERE TeamName = '" 
					+ teamVsTeamObj.getYourTeam() + "')  AND BatsmanID IN (SELECT BatsmanID FROM batsman WHERE TeamID = '" + opponentID + "')) GROUP BY commentarydata.BowlerID ORDER BY NumOfWics DESC) as t";

			rs12 = stmt51.executeQuery(sql);

			while(rs12.next()){
				performanceOrderBowling.add(rs12.getString("bowlerName"));
			}
			rs12.close();

			for(int i = 0; i < 6; i ++){
				teamVsTeamObj.bestSquad.add(performanceOrderBatting.get(i));
			}

			for(int i = 0; teamVsTeamObj.bestSquad.size() < 11; i ++){
				teamVsTeamObj.bestSquad.add(performanceOrderBowling.get(i));
			}

			//System.out.println(teamVsTeamObj.bestSquad.size());
			/*
			System.out.println(teamVsTeamObj.getYourTeam() + "   : getYourTeam");
			System.out.println(teamVsTeamObj.getOpponent() + "   : getOpponent");
			System.out.println(teamVsTeamObj.getNoOfMatches() + "   : getNoOfMatches");
			System.out.println(teamVsTeamObj.getNoOfWins() + "   : getNoOfWins");
			System.out.println(teamVsTeamObj.getNoOfLosses() + "   : getNoOfLosses");
			System.out.println(teamVsTeamObj.getBestBatsman() + "   : getBestBatsman");
			System.out.println(teamVsTeamObj.getBestBowler() + "   : getBestBowler");


			for(int i = 0; i < 11; i ++){
				System.out.println("Player " + (i + 1) + " :  " + teamVsTeamObj.bestSquad.get(i));
			}
			 */


		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try		
	}

	public void setTeamNameList(){

		Connection conn = null;
		Statement stmt00 = null;

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();

			String sql;
			ResultSet rs = null;
			
			sql = "SELECT TeamName FROM team";

			rs = stmt00.executeQuery(sql);
			while(rs.next()){
				teamNames.add(rs.getString("TeamName"));
			}
			rs.close();

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try		
	}
	

	public void setTeamPlayers(String TeamName){

		Connection conn = null;
		Statement stmt00 = null, stmt01 = null;

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();
			stmt01 = conn.createStatement();
			
			String sql;
			ResultSet rs = null, rs1 = null;
			
			sql = "SELECT BatsmanName FROM Batsman INNER JOIN Team ON Batsman.TeamID = Team.TeamID WHERE TeamName = '" + TeamName + "'";

			rs = stmt00.executeQuery(sql);
			while(rs.next()){
				teamPlayers.add(rs.getString("BatsmanName"));
				batsmanList.add(rs.getString("BatsmanName"));
			}
			rs.close();
			
			sql = "SELECT BowlerName FROM Bowler INNER JOIN Team ON Bowler.TeamID = Team.TeamID WHERE TeamName = '" + TeamName + "'";

			rs1 = stmt01.executeQuery(sql);
			while(rs1.next()){
				bowlerList.add(rs1.getString("BowlerName"));
				if(!teamPlayers.contains(rs1.getString("BowlerName")))
					teamPlayers.add(rs1.getString("BowlerName"));
			}
			rs1.close();

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try		
	}
	
	public void setVenueNameList(){

		Connection conn = null;
		Statement stmt00 = null;

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query

			stmt00 = conn.createStatement();

			String sql;
			ResultSet rs = null;
			
			sql = "SELECT DISTINCT Venue FROM matches";

			rs = stmt00.executeQuery(sql);
			while(rs.next()){
				venueNames.add(rs.getString("Venue"));
			}
			rs.close();

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt00!=null)
					stmt00.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try		
	}
	

}//end 