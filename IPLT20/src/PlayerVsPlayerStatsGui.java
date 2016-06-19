import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class PlayerVsPlayerStatsGui {

	protected Shell shlPlayerVsPlayer;
	private Text text;
	boolean start = true, start1 = true;
	String batsmanTeamName;
	String bowlerTeamName;
	DataAccess batsmen = new DataAccess();
	DataAccess bowlers = new DataAccess();
	private static DataAccess playerVsPlayerObjDA = new DataAccess();

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlPlayerVsPlayer.open();
		shlPlayerVsPlayer.layout();
		while (!shlPlayerVsPlayer.isDisposed()) {
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
		shlPlayerVsPlayer = new Shell();
		shlPlayerVsPlayer.setSize(596, 783);
		shlPlayerVsPlayer.setText("Player Vs. Player Statistics");
		
		Label lblSelectYourBatsmans = new Label(shlPlayerVsPlayer, SWT.NONE);
		lblSelectYourBatsmans.setText("Select batsman's Team name:");
		lblSelectYourBatsmans.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectYourBatsmans.setBounds(22, 26, 284, 36);
		
		Label lblSelectBowlersTeam = new Label(shlPlayerVsPlayer, SWT.NONE);
		lblSelectBowlersTeam.setText("Select bowler's Team name:");
		lblSelectBowlersTeam.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectBowlersTeam.setBounds(22, 117, 284, 36);
		
		Label lblSelectBatsmansName = new Label(shlPlayerVsPlayer, SWT.NONE);
		lblSelectBatsmansName.setText("Select batsman's name:");
		lblSelectBatsmansName.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectBatsmansName.setBounds(22, 72, 284, 36);
		
		Label lblSelectBowlersName = new Label(shlPlayerVsPlayer, SWT.NONE);
		lblSelectBowlersName.setText("Select bowler's name:");
		lblSelectBowlersName.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectBowlersName.setBounds(22, 163, 284, 36);
		
		Combo combo = new Combo(shlPlayerVsPlayer, SWT.READ_ONLY);
		combo.setBounds(328, 27, 198, 23);
		
		for(int i = 0; i < DataAccess.teamNames.size(); i++)
			combo.add((String) DataAccess.teamNames.get(i));

		Combo combo_1 = new Combo(shlPlayerVsPlayer, SWT.READ_ONLY);
		combo_1.setBounds(328, 72, 198, 23);
		
		Combo combo_3 = new Combo(shlPlayerVsPlayer, SWT.READ_ONLY);
		combo_3.setBounds(328, 163, 198, 23);
		
		Combo combo_2 = new Combo(shlPlayerVsPlayer, SWT.READ_ONLY);
		combo_2.setBounds(328, 117, 198, 23);
				
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				
				if(start == true){
					DataAccess.batsmanList.clear();
					batsmanTeamName = combo.getText();
					batsmen.setTeamPlayers(batsmanTeamName);
					//System.out.println(DataAccess.teamPlayers.size());

					for(int i = 0; i < DataAccess.batsmanList.size(); i++)
						combo_1.add((String) DataAccess.batsmanList.get(i));
					
					for(int i = 0; i < DataAccess.teamNames.size(); i++)
						combo_2.add((String) DataAccess.teamNames.get(i));
					combo_2.remove(combo.getText());
					start = false;
				}
				else{
					DataAccess.batsmanList.clear();
					combo_1.removeAll();
					combo_2.removeAll();
					combo_3.removeAll();
					batsmanTeamName = combo.getText();
					batsmen.setTeamPlayers(batsmanTeamName);
					//System.out.println(DataAccess.batsmanList.size());

					for(int i = 0; i < DataAccess.batsmanList.size(); i++)
						combo_1.add((String) DataAccess.batsmanList.get(i));
					
					for(int i = 0; i < DataAccess.teamNames.size(); i++)
						combo_2.add((String) DataAccess.teamNames.get(i));
					combo_2.remove(combo.getText());
				}
			}
		});
		
		combo_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				if(start1 == true){
					combo_3.removeAll();
					DataAccess.bowlerList.clear();
					bowlerTeamName = combo_2.getText();
					bowlers.setTeamPlayers(bowlerTeamName);
					//System.out.println(DataAccess.teamPlayers.size());

					for(int i = 0; i < DataAccess.bowlerList.size(); i++)
						combo_3.add((String) DataAccess.bowlerList.get(i));

					start1 = false;
				}
				else{
					DataAccess.bowlerList.clear();
					combo_3.removeAll();

					bowlerTeamName = combo_2.getText();
					bowlers.setTeamPlayers(bowlerTeamName);
					//System.out.println(DataAccess.teamPlayers.size());

					for(int i = 0; i < DataAccess.bowlerList.size(); i++)
						combo_3.add((String) DataAccess.bowlerList.get(i));								
				}
			}
		});
		
		text = new Text(shlPlayerVsPlayer, SWT.MULTI);
		text.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		text.setEditable(false);
		text.setBounds(22, 218, 504, 492);
		
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				combo_2.deselectAll();
				combo_3.removeAll();
			}
		});
		
		combo_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				if(combo_1.getText() != null || !combo_1.getText().isEmpty()){
					
					playerVsPlayerObjDA.playerVsPlayerData(combo_1.getText(), batsmanTeamName, combo_3.getText(), bowlerTeamName);
					text.append("\n/--  STATISTICS  --/\n");
					text.append("----------------------\n");
					text.append("Batsman Name :			 		" + playerVsPlayerObjDA.playerVsPlayerObj.getBatsman() + "\n");
					text.append("Batsman's Team : 			 		" + playerVsPlayerObjDA.playerVsPlayerObj.getBatsmanTeam() + "\n");
					text.append("Bowler Name : 						" + playerVsPlayerObjDA.playerVsPlayerObj.getBowler() + "\n");
					text.append("Bowler's Team :						" + playerVsPlayerObjDA.playerVsPlayerObj.getBowlerTeam() + "\n");
					text.append("Number of Innings Played : 			" + playerVsPlayerObjDA.playerVsPlayerObj.getNoOfInnings() + "\n");
					text.append("Number of balls : 					" + playerVsPlayerObjDA.playerVsPlayerObj.getNoOfBalls() + "\n");
					text.append("Number of runs : 					" + playerVsPlayerObjDA.playerVsPlayerObj.getNoOfRuns() + "\n");
					text.append("Number of outs : 					" + playerVsPlayerObjDA.playerVsPlayerObj.getNoOfOuts() + "\n");
					text.append("Number of sixes :					" + playerVsPlayerObjDA.playerVsPlayerObj.getNumOfSixesHit() + "\n");
					text.append("Best Score : 						" + playerVsPlayerObjDA.playerVsPlayerObj.getBestScore() + "\n");
					text.append("Batting Strike rate : 					" + playerVsPlayerObjDA.playerVsPlayerObj.getOverAllBattingSR() + "\n");
					text.append("Average : 							" + playerVsPlayerObjDA.playerVsPlayerObj.getOverAllAvg() + "\n");
					text.append("Bowling Econ : 						" + playerVsPlayerObjDA.playerVsPlayerObj.getOverAllBowlingEcon() + "\n");
					text.append("Bowling Strike rate : 					" + playerVsPlayerObjDA.playerVsPlayerObj.getOverAllBowlingSR() + "\n\n\n");

				}
			}
		});
		
		shlPlayerVsPlayer.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				DataAccess.batsmanList.clear();
				DataAccess.bowlerList.clear();
				DataAccess.teamPlayers.clear();
				combo.removeAll();
				combo_1.removeAll();
				combo_2.removeAll();
				combo_3.removeAll();
				text.setText("");
			}
		});


	}

}
