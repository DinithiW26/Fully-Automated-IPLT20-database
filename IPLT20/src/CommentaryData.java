
public class CommentaryData{
	
	private int innings;
	private float over;
	private String bowler;
	private String batsman;
	private int runs;
	private boolean wicket;
	private String wicketType;
	private boolean noBall;
	private boolean wide;
	private boolean LBBye;
	private boolean four;
	private boolean six;
	private int score;
	
	public void setInnings(int innings) {
       this.innings = innings;
    }

    public int getInnings() {
       return innings;
    }
	
	public void setBowler(String bowler) {
       this.bowler = bowler;
    }

    public String getBowler() {
       return bowler;
    }
	
	public void setBatsman(String batsman) {
       this.batsman = batsman;
    }

    public String getBatsman() {
       return batsman;
    }
	
	public void setOver(float over) {
       this.over = over;
    }

    public float getOver() {
       return over;
    }
	
	public void setWicket(boolean wicket)
	{
		this.wicket = wicket;
	}
	
	public boolean isWicket()
	{
		return this.wicket;
	}
	
	public String getWicketType() {
		return wicketType;
	}

	public void setWicketType(String wicketType) {
		this.wicketType = wicketType;
	}

	public void setNoBall(boolean noBall)
	{
		this.noBall = noBall;
	}
	
	public boolean isNoBall()
	{
		return this.noBall;
	}
	
	public void setWide(boolean wide)
	{
		this.wide = wide;
	}
	
	public boolean isWide()
	{
		return this.wide;
	}
	
	public void setLBBye(boolean LBBye)
	{
		this.LBBye = LBBye;
	}
	
	public boolean isLBBye()
	{
		return this.LBBye;
	}
	
	public void setFour(boolean four)
	{
		this.four = four;
	}
	
	public boolean isFour()
	{
		return this.four;
	}
	
	public void setSix(boolean six)
	{
		this.six = six;
	}
	
	public boolean isSix()
	{
		return this.six;
	}
	
	public void setRuns(int runs) {
       this.runs = runs;
    }

    public int getRuns() {
       return runs;
    }
	
	public void setScore(int score) {
       this.score = score;
    }

    public int getScore() {
       return score;
    }
}