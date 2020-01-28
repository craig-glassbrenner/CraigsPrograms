import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import java.awt.Font;

public class Window1 {

	private JFrame frame;
	private Database data;
	private String teamSelect;
	private String opponentSelect = "";
	private String homeAway = "";
	private String fav = "";

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
		frame.setBounds(200, 200, 550, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				try {
					data.disconnect();
				} catch (SQLException s) {
					System.out.println("Unable to disconnect.");
					s.printStackTrace();
				}
				
				System.out.println("\nDisconnected.");
			}
		});
		
		JList<Object> list = new JList<Object>();
		list.addListSelectionListener(new ListSelectionListener() {
			
		// Team user wants info about	
			public void valueChanged(ListSelectionEvent e) {
				teamSelect = (String) list.getSelectedValue();
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
		scrollPane.setBounds(0, 0, 150, 372);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblTeam = new JLabel("Team");
		lblTeam.setForeground(new Color(255, 0, 0));
		lblTeam.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblTeam);
		
		JList<Object> opponent = new JList<Object>();
		opponent.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				opponentSelect = (String) opponent.getSelectedValue();
				
			}
		});
		
		
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
		scrollPane2.setBounds(150, 0, 150, 372);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setViewportView(opponent);
		frame.getContentPane().add(scrollPane2);
		
		JLabel lblOpponent = new JLabel("Opponent");
		lblOpponent.setForeground(new Color(255, 0, 0));
		lblOpponent.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane2.setColumnHeaderView(lblOpponent);
		
		JCheckBox chckbxFavorite = new JCheckBox("Favorite");
		chckbxFavorite.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fav = "YES";
			}
		});
		
		chckbxFavorite.setBounds(427, 0, 123, 67);
		chckbxFavorite.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(chckbxFavorite);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Home");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				homeAway = "Home";
			}
		});
		
		chckbxNewCheckBox.setBounds(312, 68, 115, 67);
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Away");
		chckbxNewCheckBox_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				homeAway = "Away";
			}
		});
		
		chckbxNewCheckBox_1.setBounds(312, 0, 115, 67);
		chckbxNewCheckBox_1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(chckbxNewCheckBox_1);
		
		JButton btnNewButton = new JButton("Execute");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			
		// MAIN ACTION FUNCTION
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(teamSelect.compareTo("") == 0) {
						teamSelect = "Rockets";
					}
					data.figureOutQuery(teamSelect, opponentSelect, homeAway, fav);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(312, 282, 238, 90);
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
					
					display.setBounds(200, 200, 550, 400);
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
		btnYoungest.setBounds(435, 133, 115, 74);
		frame.getContentPane().add(btnYoungest);
		
		JCheckBox chckbxUnderdog = new JCheckBox("Underdog");
		chckbxUnderdog.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fav = "NO";
			}
		});
		
		chckbxUnderdog.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxUnderdog.setBounds(427, 65, 123, 74);
		frame.getContentPane().add(chckbxUnderdog);
		
		JButton btnNewButton_1 = new JButton("BestVsOU");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet results = data.bestvsOU();
					JFrame display = new JFrame();
					JTextArea toShow = new JTextArea();
					JScrollPane scr = new JScrollPane();
					
					while(results.next()) {
						String fav = results.getString("Favorite");
						double actVSpred = results.getDouble("ActualVsPredict");
						toShow.append(fav + " " + actVSpred + "\n");

					}
					
					display.setBounds(200, 200, 550, 400);
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
		btnNewButton_1.setForeground(Color.BLUE);
		btnNewButton_1.setBounds(312, 207, 123, 74);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cover %");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet results = data.coverPercentage();
					JFrame display = new JFrame();
					JTextArea toShow = new JTextArea();
					JScrollPane scr = new JScrollPane();
					
					while(results.next()) {
						String fav = results.getString("Favorite");
						double coverPer = results.getDouble("percentCover");
						toShow.append(fav + " " + coverPer + "\n");

					}
					
					display.setBounds(200, 200, 550, 400);
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
		btnNewButton_2.setForeground(Color.BLUE);
		btnNewButton_2.setBounds(435, 207, 115, 74);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnDivisonvsspread = new JButton("DivisonVsSpread");
		btnDivisonvsspread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet results = data.divisonVSSpread();
					JFrame display = new JFrame();
					JTextArea toShow = new JTextArea();
					JScrollPane scr = new JScrollPane();
					
					while(results.next()) {
						String div = results.getString("DivisonName");
						double Per = results.getDouble("percent");
						toShow.append(div + " " + Per + "\n");

					}
					
					display.setBounds(200, 200, 550, 400);
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
		btnDivisonvsspread.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnDivisonvsspread.setForeground(Color.BLUE);
		btnDivisonvsspread.setBounds(312, 133, 123, 74);
		frame.getContentPane().add(btnDivisonvsspread);
		
	}
}











