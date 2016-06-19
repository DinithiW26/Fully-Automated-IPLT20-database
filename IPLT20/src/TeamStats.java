import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author Pranavan
 *
 */
public class TeamStats {
	
	private String teamName;
	private int noOfMatchesPlayed;
	private int NoOfWins;
	private int NoOfLosses;
	private String bestAgainst;
	private String weakAgainst;
	private String bestBatsman;
	private String bestBowler;
	public List<String> bestSquad;
	
	public TeamStats(){
		bestSquad = new ArrayList<String>();
	}
	
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the noOfMatchesPlayed
	 */
	public int getNoOfMatchesPlayed() {
		return noOfMatchesPlayed;
	}

	/**
	 * @param noOfMatchesPlayed the noOfMatchesPlayed to set
	 */
	public void setNoOfMatchesPlayed(int noOfMatchesPlayed) {
		this.noOfMatchesPlayed = noOfMatchesPlayed;
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
	 * @return the noOfLosses
	 */
	public int getNoOfLosses() {
		return NoOfLosses;
	}

	/**
	 * @param noOfLosses the noOfLosses to set
	 */
	public void setNoOfLosses(int noOfLosses) {
		NoOfLosses = noOfLosses;
	}

	/**
	 * @return the bestAgainst
	 */
	public String getBestAgainst() {
		return bestAgainst;
	}

	/**
	 * @param bestAgainst the bestAgainst to set
	 */
	public void setBestAgainst(String bestAgainst) {
		this.bestAgainst = bestAgainst;
	}

	/**
	 * @return the weakAgainst
	 */
	public String getWeakAgainst() {
		return weakAgainst;
	}

	/**
	 * @param weakAgainst the weakAgainst to set
	 */
	public void setWeakAgainst(String weakAgainst) {
		this.weakAgainst = weakAgainst;
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
