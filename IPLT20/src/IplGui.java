import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class IplGui {

	protected static Shell shell;
	public static boolean visible = true;
	public static DataAccess teamList = new DataAccess();
	public static DataAccess venueNamesList = new DataAccess();
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		teamList.setTeamNameList();
		venueNamesList.setVenueNameList();
		try {
			IplGui window = new IplGui();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(595, 411);
		shell.setText("IPL T20 - Year 2014");
		
		Button playerStatistics = new Button(shell, SWT.BORDER | SWT.CENTER);
		playerStatistics.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 11, SWT.NORMAL));
		playerStatistics.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		playerStatistics.setToolTipText("To list player statistics");
		playerStatistics.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(visible == true){
						PlayerStatsGui window = new PlayerStatsGui();
						window.open();
						visible = true;
					}
					/*else{
						
					}*/
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				
			}
		});
		playerStatistics.setBounds(164, 70, 245, 43);
		playerStatistics.setText("Player Statistics");
		
		Button playerVsplayerStatistics = new Button(shell, SWT.BORDER | SWT.CENTER);
		playerVsplayerStatistics.setToolTipText("To list Player Vs Player Statistics");
		playerVsplayerStatistics.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 11, SWT.NORMAL));
		playerVsplayerStatistics.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(visible == true){
						PlayerVsPlayerStatsGui window = new PlayerVsPlayerStatsGui();
						window.open();
						visible = true;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		playerVsplayerStatistics.setBounds(164, 125, 245, 43);
		playerVsplayerStatistics.setText("Player Vs. Player Statistics");
		
		Button teamStatstics = new Button(shell, SWT.BORDER | SWT.CENTER);
		teamStatstics.setToolTipText("To list Team Statistics\r\n");
		teamStatstics.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 11, SWT.NORMAL));
		teamStatstics.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(visible == true){
						TeamStatsGui window = new TeamStatsGui();
						window.open();
						visible = true;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		teamStatstics.setText("Team Statistics");
		teamStatstics.setBounds(164, 180, 245, 43);
		
		Button teamVsTeamStatistics = new Button(shell, SWT.BORDER | SWT.CENTER);
		teamVsTeamStatistics.setToolTipText("To list Team Vs Team Statistics");
		teamVsTeamStatistics.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 11, SWT.NORMAL));
		teamVsTeamStatistics.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(visible == true){
						TeamVsTeamStatsGui window = new TeamVsTeamStatsGui();
						window.open();
						visible = true;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		teamVsTeamStatistics.setText("Team Vs. Team Statistics");
		teamVsTeamStatistics.setBounds(164, 235, 245, 43);
		
		Button venueStatistics = new Button(shell, SWT.BORDER | SWT.CENTER);
		venueStatistics.setToolTipText("To list Venue Statistics");
		venueStatistics.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 11, SWT.NORMAL));
		venueStatistics.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(visible == true){
						VenueStatsGui window = new VenueStatsGui();
						window.open();
						visible = true;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		venueStatistics.setText("Venue Statistics");
		venueStatistics.setBounds(164, 290, 245, 43);
		
		Label heading = new Label(shell, SWT.NONE);
		heading.setFont(SWTResourceManager.getFont("Segoe Script", 13, SWT.BOLD | SWT.ITALIC));
		heading.setAlignment(SWT.CENTER);
		heading.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		heading.setBounds(22, 21, 547, 34);
		heading.setText("WELCOME TO IPL - YEAR 2014 STATISTICS WORLD !!!");

	}
}
