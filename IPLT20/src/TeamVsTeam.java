import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author Pranavan
 *
 */
public class TeamVsTeam {
	
	private String yourTeam;
	private String opponent;
	private int NoOfMatches;
	private int NoOfWins;
	private int NoOfLosses;
	private String bestBatsman;
	private String bestBowler;
	public List<String> bestSquad;
	
	public TeamVsTeam(){
		bestSquad = new ArrayList<String>();
	}
	/**
	 * @return the yourTeam
	 */
	public String getYourTeam() {
		return yourTeam;
	}
	/**
	 * @param yourTeam the yourTeam to set
	 */
	public void setYourTeam(String yourTeam) {
		this.yourTeam = yourTeam;
	}
	/**
	 * @return the opponent
	 */
	public String getOpponent() {
		return opponent;
	}
	/**
	 * @param opponent the opponent to set
	 */
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	/**
	 * @return the noOfMatches
	 */
	public int getNoOfMatches() {
		return NoOfMatches;
	}
	/**
	 * @param noOfMatches the noOfMatches to set
	 */
	public void setNoOfMatches(int noOfMatches) {
		NoOfMatches = noOfMatches;
	}
	/**
	 * @return the noOfWins
	 */
	public int getNoOfWins() {
		return NoOfWins;
	}
	/**
	 * @param noOfWins the noOfWins to set
	 */
	public void setNoOfWins(int noOfWins) {
		NoOfWins = noOfWins;
	}
	/**
	 * @return the noOflosses
	 */
	public int getNoOfLosses() {
		return NoOfLosses;
	}
	/**
	 * @param noOflosses the noOflosses to set
	 */
	public void setNoOfLosses(int noOfLosses) {
		NoOfLosses = noOfLosses;
	}
	/**
	 * @return the bestBatsman
	 */
	public String getBestBatsman() {
		return bestBatsman;
	}
	/**
	 * @param bestBatsman the bestBatsman to set
	 */
	public void setBestBatsman(String bestBatsman) {
		this.bestBatsman = bestBatsman;
	}
	/**
	 * @return the bestBowler
	 */
	public String getBestBowler() {
		return bestBowler;
	}
	/**
	 * @param bestBowler the bestBowler to set
	 */
	public void setBestBowler(String bestBowler) {
		this.bestBowler = bestBowler;
	}
	
}
