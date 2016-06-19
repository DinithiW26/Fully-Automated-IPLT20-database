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

public class TeamVsTeamStatsGui {

	protected Shell shlTeamVsTeam;
	private Text text;
	private static DataAccess teamVsTeamObjDA = new DataAccess();

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlTeamVsTeam.open();
		shlTeamVsTeam.layout();
		while (!shlTeamVsTeam.isDisposed()) {
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
		shlTeamVsTeam = new Shell();
		shlTeamVsTeam.setSize(690, 796);
		shlTeamVsTeam.setText("Team Vs. Team Statistics");
		
		Label lblSelectYourTeams = new Label(shlTeamVsTeam, SWT.NONE);
		lblSelectYourTeams.setText("Select your Team's name:");
		lblSelectYourTeams.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectYourTeams.setBounds(25, 31, 284, 36);
		
		Label lblSelectYourOpponent = new Label(shlTeamVsTeam, SWT.NONE);
		lblSelectYourOpponent.setText("Select your Opponent Team's name:");
		lblSelectYourOpponent.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectYourOpponent.setBounds(25, 73, 326, 36);
		
		Combo combo = new Combo(shlTeamVsTeam, SWT.READ_ONLY);
		combo.setBounds(364, 32, 257, 23);
		
		for(int i = 0; i < DataAccess.teamNames.size(); i++)
			combo.add((String) DataAccess.teamNames.get(i));
		
		Combo combo_1 = new Combo(shlTeamVsTeam, SWT.READ_ONLY);
		combo_1.setBounds(364, 74, 257, 23);
		
		text = new Text(shlTeamVsTeam, SWT.MULTI);
		text.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		text.setEditable(false);
		text.setBounds(25, 141, 550, 574);
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				combo_1.removeAll();
				for(int i = 0; i < DataAccess.teamNames.size(); i++)
					combo_1.add((String) DataAccess.teamNames.get(i));
				combo_1.remove(combo.getText());
			}
		});
		
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				teamVsTeamObjDA.teamVsTeamData(combo.getText(), combo_1.getText());
				
				text.append("\n/--  STATISTICS  --/\n");
				text.append("----------------------\n");
				text.append("Your Team's Name :			 		" + teamVsTeamObjDA.teamVsTeamObj.getYourTeam() + "\n");
				text.append("Opponent Team's Name : 				" + teamVsTeamObjDA.teamVsTeamObj.getOpponent() + "\n");
				text.append("Number of Matches Played : 			" + teamVsTeamObjDA.teamVsTeamObj.getNoOfMatches() + "\n");
				text.append("Number of wins :					" + teamVsTeamObjDA.teamVsTeamObj.getNoOfWins() + "\n");
				text.append("Number of losses : 					" + teamVsTeamObjDA.teamVsTeamObj.getNoOfLosses() + "\n");
				text.append("Best Batsman name : 				" + teamVsTeamObjDA.teamVsTeamObj.getBestBatsman() + "\n");
				text.append("Best Bowler name : 					" + teamVsTeamObjDA.teamVsTeamObj.getBestBowler() + "\n\n\n");
				
				text.append("\n/-- BEST SQUAD AGAINST THIS OPPONENT --/\n");
				text.append("-----------------------------------------\n");
				for(int i = 0; i < 11; i ++){
					text.append("Player " + (i + 1) + " :  " + teamVsTeamObjDA.teamVsTeamObj.bestSquad.get(i) + "\n");
				}
			}
		});
		
		shlTeamVsTeam.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				combo.removeAll();
				combo_1.removeAll();
				text.setText("");
			}
		});
	}

}
