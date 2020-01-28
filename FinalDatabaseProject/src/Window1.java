import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Window1 {

	private JFrame frame;
	private Database data;
	private String teamSelect;
	private String opppenentSelect = "";

	/**
	 * Create the application.
	 */
	public Window1(Database db) {
		data = db;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JList<Object> list = new JList<Object>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				teamSelect = e.toString();
				System.out.println(teamSelect);
			}
		});
		list.setVisibleRowCount(16);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Hawks", "Celtics", "Nets", "Hornets", "Bulls", "Cavaliers", "Mavericks", "Nuggets", "Pistons", "Warriors", "Rockets", "Pacers", "Clippers", "Lakers", "Grizzlies ", "Heat", "Bucks", "Timberwolves", "Pelicans", "Knicks", "Thunder", "Magic", "76ers", "Suns", "Trailblazers", "Kings", "Spurs", "Raptors", "Jazz", "Wizards "};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(list);
		scrollPane.setBounds(0, 0, 150, 139);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblTeam = new JLabel("Team");
		lblTeam.setForeground(new Color(255, 0, 0));
		lblTeam.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblTeam);
		
		JList<Object> opponent = new JList<Object>();
		opponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		opponent.setModel(new AbstractListModel() {
			String[] values = new String[] {"Hawks", "Celtics", "Nets", "Hornets", "Bulls", "Cavaliers", "Mavericks", "Nuggets", "Pistons", "Warriors", "Rockets", "Pacers", "Clippers", "Lakers", "Grizzlies ", "Heat", "Bucks", "Timberwolves", "Pelicans", "Knicks", "Thunder", "Magic", "76ers", "Suns", "Trailblazers", "Kings", "Spurs", "Raptors", "Jazz", "Wizards "};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(150, 0, 150, 139);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setViewportView(opponent);
		frame.getContentPane().add(scrollPane2);
		
		JLabel lblOpponent = new JLabel("Opponent");
		lblOpponent.setForeground(new Color(255, 0, 0));
		lblOpponent.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane2.setColumnHeaderView(lblOpponent);
		
		JCheckBox chckbxFavorite = new JCheckBox("Favorite?");
		chckbxFavorite.setBounds(300, 0, 150, 139);
		chckbxFavorite.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(chckbxFavorite);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Home");
		chckbxNewCheckBox.setBounds(0, 211, 150, 67);
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Away");
		chckbxNewCheckBox_1.setBounds(0, 139, 150, 67);
		chckbxNewCheckBox_1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		
		JButton btnNewButton = new JButton("RUN");
		btnNewButton.setBounds(352, 222, 98, 56);
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBackground(Color.BLACK);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnYoungest = new JButton("Youngest");
		btnYoungest.addActionListener(new ActionListener() {
		
	//YOUNGEST BUTTON PRESS: Triggers teamAge query
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet results = data.teamAge();
					JFrame display = new JFrame();
					JTextArea toShow = new JTextArea();
					JScrollPane scr = new JScrollPane();
					
					while(results.next()) {
						String teamName = results.getString("TeamName");
						String age = results.getString("PlayerAge");
						toShow.append(teamName + " " + age + "\n");

					}
					
					display.setBounds(100, 100, 450, 300);
					display.getContentPane().add(toShow);
					
					scr.setBounds(150, 0, 150, 139);
					scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					scr.setViewportView(toShow);
					display.getContentPane().add(scr);
					
					display.setVisible(true);
					
				} catch (SQLException exc) {
					System.out.println("Error when working with database...");
					exc.printStackTrace();
				}
			}
		});
		
		btnYoungest.setForeground(Color.BLUE);
		btnYoungest.setBounds(352, 171, 98, 50);
		frame.getContentPane().add(btnYoungest);
		
	}
}











