import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetURLContent {
	
	public static int count = 0;
	//public static ArrayList<int> overLineNum = new ArrayList<int>();
	public static String overNumPatt = "<div class=\"commentary-overs\">([^\"]+)</div>";
	public static int overLineNumber = 0;
	public static String titlePatt = "<title>([^\"]+)</title>";
	public static int titleLineNumber = 0;
	public static int wicketLineNumber = 0;
	public static int wicketComTextLineNumber = 0;
	
	URL url;
	PatternMatcherGroupHtml obj;
	Match matchDetails;
	CommentaryData ComData;
	String bowlerPatt = "<p>([^\"]+) to";
	String batsmanPatt = "to ([^\"]+),";
	String resultPatt = "([^\"]+),";
	String resultImpPatt = "<span class=\"commsImportant\">([^\"]+)</span>,";
	String matchDescPatt = "<title>([^\"]+):";
	String team1Patt = ": ([^\"]+) v";
	String team2Patt = "v ([^\"]+) at";
	String venuePatt = "at ([^\"]+)";
	String matchResultPatt = "<div class=\"innings-requirement\">([^\"]+)</div>";
	//String matchDatePatt = ", ([^\"]+), ";
	String title;
	String bowler;
	String batsman;
	String result;
	String resultImp;
	String matchDescription;
	String matchNum;
	String team1 = null;
	String team2 = null;
	String urlStr = "http://www.espncricinfo.com/indian-premier-league-2014/engine/match/733995.html?innings=1;view=commentary";
	int runs;
	int total = 0;
	boolean exceptFirstComOver = false;
		
	public GetURLContent() {
		
		if(urlStr.contains("indian-premier-league-2014") && urlStr.contains("commentary")){
			try {
				// get URL content
				url = new URL(urlStr);
				URLConnection conn = url.openConnection();

				// open the stream and put it into BufferedReader
				BufferedReader br = new BufferedReader(
								   new InputStreamReader(conn.getInputStream()));

				String inputLine;

				//save to this filename
				String fileName = "C:/Users/Pranavan/Desktop/PROJECT/test1.html";
				File file = new File(fileName);

				if (!file.exists()) {
					file.createNewFile();
				}

				//use FileWriter to write file
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				obj = new PatternMatcherGroupHtml();
				matchDetails = new Match();
			
				//obj.checkPatternMatch(urlStr, inningsPatt);
				//innings = Integer.parseInt(PatternMatcherGroupHtml.codeGroup);
				
				
				while ((inputLine = br.readLine()) != null) {
					bw.write(inputLine);
					bw.write(System.lineSeparator());
					obj.checkPatternMatch(inputLine, titlePatt);
					
					/* if(inputLine.contains("commentary-overs")){
						j ++;
					} */
					
					if(inputLine.contains("commentary-overs") && (exceptFirstComOver)){
						matchDetails.ballDetails.add(ComData);
					}
					else if(inputLine.contains("commentary-overs") && (!exceptFirstComOver)){
						exceptFirstComOver = true;
					}
					
					if(inputLine.contains("commentary-overs")){
						ComData = new CommentaryData();
						if(urlStr.contains("innings=2")){
							ComData.setInnings(2);
						}
						else{
							ComData.setInnings(1);
						}
					}
					
					if(count == titleLineNumber && titleLineNumber != 0){
						title = PatternMatcherGroupHtml.codeGroup;
						
						obj.checkPatternMatch(inputLine, matchDescPatt);
						matchDescription = PatternMatcherGroupHtml.codeGroup;
						matchDetails.setMatchDesc(matchDescription);
						
						if(matchDescription.equals("Qualifier 1")){
							matchDetails.setMatchNumber(57);
						}
						else if(matchDescription.equals("Eliminator")){
							matchDetails.setMatchNumber(58);
						}
						else if(matchDescription.equals("Qualifier 2")){
							matchDetails.setMatchNumber(59);
						}
						else if(matchDescription.equals("Final")){
							matchDetails.setMatchNumber(60);
						}
						else{
						matchNum = matchDescription.replaceAll("\\D+","");
						matchDetails.setMatchNumber(Integer.parseInt(matchNum));
						}
						
						obj.checkPatternMatch(inputLine, team1Patt);
						team1 = PatternMatcherGroupHtml.codeGroup;
						
						obj.checkPatternMatch(inputLine, team2Patt);
						team2 = PatternMatcherGroupHtml.codeGroup;
						
						String[] titleParts = title.split(", ");
						String[] titleLastElementParts = titleParts[2].split(" ");
						
						obj.checkPatternMatch(titleParts[0], venuePatt);
						matchDetails.setVenue(PatternMatcherGroupHtml.codeGroup);

						matchDetails.setMatchDate(titleParts[1] + ", " + titleLastElementParts[0]);
					}
					
					if(inputLine.contains("innings-requirement")){
						obj.checkPatternMatch(inputLine, matchResultPatt);
						matchDetails.setMatchResult(PatternMatcherGroupHtml.codeGroup);
					}
					
					if(team1 != null && team2 != null && matchDetails.getMatchResult() != null){
						if(matchDetails.getMatchResult().contains(team1)){
							matchDetails.setTeamWon(team1);
						}
						else if(matchDetails.getMatchResult().contains(team2)){
							matchDetails.setTeamWon(team2);
						}
						else{
							matchDetails.setTeamWon("Neither");
						}
					
						if(matchDetails.getMatchResult().contains(team1) && matchDetails.getMatchResult().contains("wickets")){
							matchDetails.setBatFirstTeam(team2);
							matchDetails.setBatSecondTeam(team1);
						}
						else if(matchDetails.getMatchResult().contains(team2) && matchDetails.getMatchResult().contains("wickets")){
							matchDetails.setBatFirstTeam(team1);
							matchDetails.setBatSecondTeam(team2);
						}
						else if(matchDetails.getMatchResult().contains(team1) && matchDetails.getMatchResult().contains("runs")){
							matchDetails.setBatFirstTeam(team1);
							matchDetails.setBatSecondTeam(team2);
						}
						else if(matchDetails.getMatchResult().contains(team2) && matchDetails.getMatchResult().contains("runs")){
							matchDetails.setBatFirstTeam(team2);
							matchDetails.setBatSecondTeam(team1);
						}
					
						/* if(i == false && matchDetails.getBatFirstTeam() != null){
							System.out.println(matchDetails.getBatFirstTeam());
							System.out.println(matchDetails.getBatSecondTeam());
							System.out.println(matchDetails.getTeamWon());
							System.out.println(matchDetails.getMatchResult());
							i = true;
						} */
					}

					obj.checkPatternMatch(inputLine, overNumPatt);
			
					if(count == (overLineNumber + 2) && overLineNumber != 0){
						ComData.setOver(Float.parseFloat(PatternMatcherGroupHtml.codeGroup)); 
						obj.checkPatternMatch(inputLine, bowlerPatt);
						ComData.setBowler(PatternMatcherGroupHtml.codeGroup);
						obj.checkPatternMatch(inputLine, batsmanPatt);
						ComData.setBatsman(PatternMatcherGroupHtml.codeGroup);
					}
					if(count == (overLineNumber + 3) && overLineNumber != 0){
						String temp = inputLine;
						temp = temp.replaceAll("\\D+","");
						if (temp.trim().length() > 0){
							ComData.setRuns(Integer.parseInt(temp));
							//System.out.println(ComData.getRuns());
						}
						if(inputLine.contains("commsImportant")){
							obj.checkPatternMatch(inputLine, resultImpPatt);
							resultImp = PatternMatcherGroupHtml.codeGroup;
							if(inputLine.contains("FOUR")){
								ComData.setFour(true);
								ComData.setRuns(4);
							}
							else if(inputLine.contains("SIX")){
								ComData.setSix(true);
								ComData.setRuns(6);
							}
							else if(inputLine.contains("OUT")){
								ComData.setWicket(true);
								wicketLineNumber = count;
							}
						}
						else{
							obj.checkPatternMatch(inputLine, resultPatt);
							result = PatternMatcherGroupHtml.codeGroup;
							//System.out.println(inputLine);
						}
			
						
						if(inputLine.contains("(no ball)")){
							ComData.setRuns(ComData.getRuns() + 1);
							//System.out.println(ComData.getRuns());
						}	
						
						if(inputLine.contains("no ball")){
							ComData.setNoBall(true);
						}
						
						if(inputLine.contains("wides") || inputLine.contains("wide")){
							ComData.setWide(true);
						}
						
						if(inputLine.contains("leg bye") || inputLine.contains("leg byes")){
							ComData.setLBBye(true);
						}
						total = total + ComData.getRuns();
						ComData.setScore(total);						
					}
					
					if(inputLine.contains("commentary-text") && (count - wicketLineNumber) < 10){
						wicketComTextLineNumber = count;
					}
					
					if(wicketComTextLineNumber != 0 && count == wicketComTextLineNumber + 1){
						
						if(inputLine.contains(" lbw ")){
							ComData.setWicketType("LBW");
						}
						else if(inputLine.contains(" st ")){
							ComData.setWicketType("Stumped");
						}
						else if(inputLine.contains(" c ")){
							ComData.setWicketType("Caught");
						}
						else if(inputLine.contains("run out")){
							ComData.setWicketType("Run out");
						}
						else{
							ComData.setWicketType("Bowled");
						}
					}
					
					count ++;
				}
				
				matchDetails.ballDetails.add(ComData);
				//System.out.println(matchDetails.ballDetails.size());

				bw.close();
				br.close();

				//System.out.println("Done" + j);
				//System.out.println("Done");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(urlStr.contains("indian-premier-league-2014")){
			System.out.println("Please provide the URL of the commentary page of the match you've entered.");
		}
		
		else{
			System.out.println("Sorry, the URL you entered does not belong to IPL 2014 series.");
		}
	}
}