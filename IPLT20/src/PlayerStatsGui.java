import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class PlayerStatsGui {

	protected Shell shlPlayerStatistics;
	final JPanel panel = new JPanel();
	DataAccess teamPlayers = new DataAccess();;
	boolean start = true;
	String teamName;
	private static DataAccess playerStatsObjDA = new DataAccess();
	private Text text;

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlPlayerStatistics.open();
		shlPlayerStatistics.layout();
		while (!shlPlayerStatistics.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
				IplGui.visible = false;
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {

		shlPlayerStatistics = new Shell();
		
		shlPlayerStatistics.setToolTipText("To get Player Statistics");
		shlPlayerStatistics.setSize(633, 804);
		shlPlayerStatistics.setText("Player Statistics");

		Label lblEnterYourPlayers = new Label(shlPlayerStatistics, SWT.NONE);
		lblEnterYourPlayers.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblEnterYourPlayers.setBounds(23, 31, 284, 36);
		lblEnterYourPlayers.setText("Select your player's Team name:");

		Combo combo = new Combo(shlPlayerStatistics, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setBounds(332, 32, 198, 36);

		for(int i = 0; i < DataAccess.teamNames.size(); i++)
			combo.add((String) DataAccess.teamNames.get(i));

		Label lblSelectYourPlayers = new Label(shlPlayerStatistics, SWT.NONE);
		lblSelectYourPlayers.setText("Select your player's name:");
		lblSelectYourPlayers.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectYourPlayers.setBounds(23, 83, 284, 36);

		Combo combo_1 = new Combo(shlPlayerStatistics, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_1.setBounds(332, 83, 198, 23);

		text = new Text(shlPlayerStatistics, SWT.MULTI);
		text.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		text.setEditable(false);
		text.setBounds(23, 125, 507, 606);

		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				if(start == true){
					teamName = combo.getText();
					teamPlayers.setTeamPlayers(teamName);
					//System.out.println(DataAccess.teamPlayers.size());

					for(int i = 0; i < DataAccess.teamPlayers.size(); i++)
						combo_1.add((String) DataAccess.teamPlayers.get(i));

					start = false;
				}
				else{
					DataAccess.teamPlayers.clear();
					combo_1.removeAll();

					teamName = combo.getText();
					teamPlayers.setTeamPlayers(teamName);
					//System.out.println(DataAccess.teamPlayers.size());

					for(int i = 0; i < DataAccess.teamPlayers.size(); i++)
						combo_1.add((String) DataAccess.teamPlayers.get(i));								
				}
			}
		});

		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				text.setText("");
				playerStatsObjDA.playerStatsData(combo_1.getText(), combo.getText());
				text.append("/-- BATTING STATISTICS  --/\n");
				text.append("---------------------------\n");
				text.append("Player Name :			 		" + playerStatsObjDA.playerStatsObj.getPlayerName() + "\n");
				text.append("Player Team : 			 		" + playerStatsObjDA.playerStatsObj.getPlayerTeam() + "\n");
				text.append("Number of Balls faced : 			" + playerStatsObjDA.playerStatsObj.getBallsFaced() + "\n");
				text.append("Number of Runs got :			" + playerStatsObjDA.playerStatsObj.getRunsScored() + "\n");
				text.append("Number of outs : 				" + playerStatsObjDA.playerStatsObj.getNumOfOuts() + "\n");
				text.append("Number of Innings Played : 		" + playerStatsObjDA.playerStatsObj.getNumOfInningsPlayed_Batting() + "\n");
				text.append("Number of Sixes : 				" + playerStatsObjDA.playerStatsObj.getNumOfSixesHit() + "\n");
				text.append("Best Score : 					" + playerStatsObjDA.playerStatsObj.getBestScore() + "\n");
				text.append("Batting Strike rate :				" + playerStatsObjDA.playerStatsObj.getOverAllBattingSR() + "\n");
				text.append("Batting Average : 				" + playerStatsObjDA.playerStatsObj.getSeriesBattingAvg() + "\n");
				text.append("Best Against : 					" + playerStatsObjDA.playerStatsObj.getBatting_BestAgainst() + "\n");
				text.append("Weak Against : 					" + playerStatsObjDA.playerStatsObj.getBatting_WeakAgainst() + "\n");
				text.append("Best Against_Team : 				" + playerStatsObjDA.playerStatsObj.getBatting_BestAgainstTeam() + "\n");
				text.append("Weak Against_Team : 			" + playerStatsObjDA.playerStatsObj.getBatting_WeakAgainstTeam() + "\n\n\n");

				text.append("/--BOWLING STATISTICS  --/\n");
				text.append("---------------------------\n");
				text.append("Number of Innings Played : 		" + playerStatsObjDA.playerStatsObj.getNumOfInningsPlayed_Bowling() + "\n");
				text.append("Balls Bowled : 					" + playerStatsObjDA.playerStatsObj.getBallsbowled() + "\n");
				text.append("Runs Conceeded : 				" + playerStatsObjDA.playerStatsObj.getRunsConceeded() + "\n");
				text.append("Number of Wickets : 			" + playerStatsObjDA.playerStatsObj.getNumOfWicTook() + "\n");
				text.append("Max Num of Wics in a match : 		" + playerStatsObjDA.playerStatsObj.getMaxNumOfWicInAMatch() + "\n");
				text.append("Best Against : 					" + playerStatsObjDA.playerStatsObj.getBowling_BestAgainst() + "\n");
				text.append("Weak Against :			 		" + playerStatsObjDA.playerStatsObj.getBowling_WeakAgainst() + "\n");
				text.append("Best Against_Team : 				" + playerStatsObjDA.playerStatsObj.getBowling_BestAgainstTeam() + "\n");
				text.append("Weak Against_Team : 			" + playerStatsObjDA.playerStatsObj.getBowling_WeakAgainstTeam() + "\n");
				text.append("Bowling Average : 				" + playerStatsObjDA.playerStatsObj.getSeriesBowlingAvg() + "\n");
				text.append("Econ : 						" + playerStatsObjDA.playerStatsObj.getSeriesEcon() + "\n");
				text.append("Bowling  Strike rate : 			" + playerStatsObjDA.playerStatsObj.getOverAllBowlingSR() + "\n");

			}
		});
		
		shlPlayerStatistics.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				DataAccess.teamPlayers.clear();
				combo.removeAll();
				combo_1.removeAll();
				text.setText("");
			}
		});

	}
}
