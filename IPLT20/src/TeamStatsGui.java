import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Text;

public class TeamStatsGui {

	protected Shell shlTeamStatistics;
	private Text text;
	private static DataAccess teamStatsObjDA = new DataAccess();

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlTeamStatistics.open();
		shlTeamStatistics.layout();
		while (!shlTeamStatistics.isDisposed()) {
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
		shlTeamStatistics = new Shell();
		shlTeamStatistics.setSize(638, 706);
		shlTeamStatistics.setText("Team Statistics");
		
		Label lblSelectYourTeam = new Label(shlTeamStatistics, SWT.NONE);
		lblSelectYourTeam.setText("Select your Team name:");
		lblSelectYourTeam.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectYourTeam.setBounds(34, 33, 284, 36);
		
		Combo combo = new Combo(shlTeamStatistics, SWT.READ_ONLY);
		combo.setBounds(324, 34, 257, 23);
		
		text = new Text(shlTeamStatistics, SWT.MULTI);
		text.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		text.setEditable(false);
		text.setBounds(34, 96, 513, 561);
		
		for(int i = 0; i < DataAccess.teamNames.size(); i++)
			combo.add((String) DataAccess.teamNames.get(i));
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				teamStatsObjDA.teamStatsData(combo.getText());
				
				text.append("\n/--  STATISTICS  --/\n");
				text.append("----------------------\n");
				text.append("Team Name :			 		" + teamStatsObjDA.teamStatsObj.getTeamName() + "\n");
				text.append("Number of Matches Played : 		" + teamStatsObjDA.teamStatsObj.getNoOfMatchesPlayed() + "\n");
				text.append("Number of wins : 				" + teamStatsObjDA.teamStatsObj.getNoOfWins() + "\n");
				text.append("Number of losses :				" + teamStatsObjDA.teamStatsObj.getNoOfLosses() + "\n");
				text.append("Best Against : 					" + teamStatsObjDA.teamStatsObj.getBestAgainst() + "\n");
				text.append("Weak Against : 					" + teamStatsObjDA.teamStatsObj.getWeakAgainst() + "\n");
				text.append("Best Batsman name : 			" + teamStatsObjDA.teamStatsObj.getBestBatsman() + "\n");
				text.append("Best Bowler name : 				" + teamStatsObjDA.teamStatsObj.getBestBowler() + "\n\n\n");
				
				text.append("\n/-- OVERALL BEST SQUAD  --/\n");
				text.append("---------------------------\n");
				for(int i = 0; i < 11; i ++){
					text.append("Player " + (i + 1) + " :  " + teamStatsObjDA.teamStatsObj.bestSquad.get(i) + "\n");
				}
			}
		});
		
		shlTeamStatistics.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				combo.removeAll();
				text.setText("");
			}
		});
	}
}
