import java.util.*;

public class Match{
	
	private int matchNumber;
	private String matchDesc;
	private String venue;
	private String matchDate;
	private String batFirstTeam;	
	private String batSecondTeam;
	private String teamWon;
	private	String matchResult;
	public List<CommentaryData> ballDetails;
	
	public Match(){
		ballDetails = new ArrayList<CommentaryData>();
	}
	
	public void setMatchNumber(int matchNumber) {
       this.matchNumber = matchNumber;
    }

    public int getMatchNumber() {
       return matchNumber;
    }
	
	public void setMatchDesc(String matchDesc) {
       this.matchDesc = matchDesc;
    }

    public String getMatchDesc() {
       return matchDesc;
    }
	
	public void setVenue(String venue) {
       this.venue = venue;
    }

    public String getVenue() {
       return venue;
    }
	
	public void setMatchDate(String matchDate) {
       this.matchDate = matchDate;
    }

    public String getMatchDate() {
       return matchDate;
    }
	
	public void setBatFirstTeam(String batFirstTeam) {
       this.batFirstTeam = batFirstTeam;
    }

    public String getBatFirstTeam() {
       return batFirstTeam;
    }
	
	public void setBatSecondTeam(String batSecondTeam) {
       this.batSecondTeam = batSecondTeam;
    }

    public String getBatSecondTeam() {
       return batSecondTeam;
    }
	
	public void setTeamWon(String teamWon) {
       this.teamWon = teamWon;
    }

    public String getTeamWon() {
       return teamWon;
    }
	
	public void setMatchResult(String matchResult) {
       this.matchResult = matchResult;
    }

    public String getMatchResult() {
       return matchResult;
    }
}