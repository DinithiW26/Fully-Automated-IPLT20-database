import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class VenueStatsGui {

	protected Shell shlVenueStatistics;
	private Text text;
	private static DataAccess venueStatsObjDA = new DataAccess();

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlVenueStatistics.open();
		shlVenueStatistics.layout();
		while (!shlVenueStatistics.isDisposed()) {
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
		shlVenueStatistics = new Shell();
		shlVenueStatistics.setSize(661, 694);
		shlVenueStatistics.setText("Venue Statistics");
		
		Label lblSelectVenueName = new Label(shlVenueStatistics, SWT.NONE);
		lblSelectVenueName.setText("Select Venue name:");
		lblSelectVenueName.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 12, SWT.NORMAL));
		lblSelectVenueName.setBounds(21, 34, 284, 36);
		
		Combo combo = new Combo(shlVenueStatistics, SWT.READ_ONLY);
		combo.setBounds(335, 34, 257, 23);
		
		text = new Text(shlVenueStatistics, SWT.MULTI);
		text.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		text.setEditable(false);
		text.setBounds(21, 86, 550, 547);
		
		for(int i = 0; i < DataAccess.venueNames.size(); i++)
			combo.add((String) DataAccess.venueNames.get(i));
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				venueStatsObjDA.venueStatsData(combo.getText());
				
				text.append("\n/--  STATISTICS  --/\n");
				text.append("----------------------\n");
				text.append("Venue Name :			 			" + venueStatsObjDA.venueStatsObj.getVenueName() + "\n");
				text.append("Number of Matches : 				" + venueStatsObjDA.venueStatsObj.getNoOfMatches() + "\n");
				text.append("Most Successful Team : 				" + venueStatsObjDA.venueStatsObj.getMostSuccessfulTeam() + "\n");
				text.append("No Of Wins For Bat First Teams :		" + venueStatsObjDA.venueStatsObj.getNoOfWinsForBatFirstTeams() + "\n");
				text.append("No Of Wins For Bowl First Teams : 		" + venueStatsObjDA.venueStatsObj.getNoOfWinsForBowlFirstTeams() + "\n");
				text.append("Avg First Innings Score : 				" + venueStatsObjDA.venueStatsObj.getAvgFirstInningsScore() + "\n");
				text.append("Avg Second Innings Score : 			" + venueStatsObjDA.venueStatsObj.getAvgSecondInningsScore() + "\n");
				text.append("Better Toss Decision : 				" + venueStatsObjDA.venueStatsObj.getBetterTossDecision() + "\n\n\n");
			}
		});
		

	}
}
