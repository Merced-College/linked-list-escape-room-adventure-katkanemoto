
package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GameGUI extends JFrame {

	private JPanel contentPane;
	private static JTextArea sceneDescription;
	private static JLabel lblImage;
	private static JButton btnChoice1;
	private static JButton btnChoice2;
	private static Scene currentScence;
	private static AdventureGame game;
	private static JTextArea txtItemsRoom;
	private JTextField userInventory;
	private JButton btnPickUpItem;

	
	public static void main(String[] args) {
        //game.play();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI frame = new GameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameGUI() {
		
		game = new AdventureGame();
		currentScence = game.getCurrentScene();
		//updateSceneDisplay();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 511);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Choose Your Own Adventure Game");
		lblTitle.setFont(new Font("Fahkwang", Font.BOLD, 14));
		lblTitle.setBounds(148, -14, 282, 79);
		contentPane.add(lblTitle);
		
		lblImage = new JLabel("");
		//lblImage.setIcon(new ImageIcon(GameGUI.class.getResource("/images2/lobby.png")));
		
	    ImageIcon icon = new ImageIcon(GameGUI.class.getResource("/images2/lobby.png"));
		Image img = icon.getImage();

		Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);

		lblImage.setIcon(scaledIcon);
		lblImage.setBounds(145, 47, 300, 300);
		contentPane.add(lblImage);
		
		sceneDescription = new JTextArea();
		sceneDescription.setBounds(170, 363, 285, 37);
		contentPane.add(sceneDescription);
		
		btnChoice1 = new JButton("New button");
		btnChoice1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScence.getChoices().get(0).getNextSceneId();
				currentScence = game.getScences().findSceneById(nextId);
	            
	            updateSceneDisplay();
	            
	            if (currentScence.getSceneId() == 5) {
	    			checkWinCondition();
	    		}
				
			}
		});
		btnChoice1.setBounds(6, 170, 99, 79);
		contentPane.add(btnChoice1);
		
		btnChoice2 = new JButton("New button");
		btnChoice2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScence.getChoices().get(1).getNextSceneId();
				currentScence = game.getScences().findSceneById(nextId);
	            
	            updateSceneDisplay();
	            
	            if (currentScence.getSceneId() == 5) {
	    			checkWinCondition();
	    		}
				
			}
		});
		btnChoice2.setBounds(463, 170, 101, 79);
		contentPane.add(btnChoice2);
		
		txtItemsRoom = new JTextArea();
		txtItemsRoom.setBounds(28, 334, 130, 75);
		txtItemsRoom.setLineWrap(true);
		txtItemsRoom.setWrapStyleWord(true);
		txtItemsRoom.setEditable(false);
		contentPane.add(txtItemsRoom);
		//txtItemsRoom.setColumns(10);
		
		userInventory = new JTextField();
		userInventory.setText("Inventory: empty");
		userInventory.setColumns(10);
		userInventory.setBounds(166, 422, 305, 55);
		contentPane.add(userInventory);
		
		btnPickUpItem = new JButton("Pick up Item");
		btnPickUpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item itemInRoom = currentScence.getItem();
				
				if (itemInRoom != null) {
					game.getPlayer().addItem(itemInRoom);
					currentScence.removeItem();
					updateSceneDisplay();
				}
			}
		});
		btnPickUpItem.setBounds(37, 421, 117, 29);
		contentPane.add(btnPickUpItem);
		
		updateSceneDisplay();
	}
	
	public void checkWinCondition() {
		boolean hasKeycard = game.getPlayer().hasItem("Keycard");
		boolean hasCodeNote = game.getPlayer().hasItem("Code Note");

		if (hasKeycard && hasCodeNote) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"You used the Keycard and the Code Note to unlock the exit.\nYou escaped. You win!");
		} else {
			javax.swing.JOptionPane.showMessageDialog(this,
					"The exit will not open.\nYou are missing the required items.\nYou need the Keycard and the Code Note.");
		}

		btnChoice1.setEnabled(false);
		btnChoice2.setEnabled(false);
		btnPickUpItem.setEnabled(false);	
		
	}

	public void updateSceneDisplay() {
	    //Scene scene = game.getCurrentScene();

	    //lblTitle.setText(scene.getTitle());
	    sceneDescription.setText(currentScence.getDescription());
	    System.out.println(currentScence);
	    Item itemInRoom = currentScence.getItem();
	    if(itemInRoom == null)
	    	txtItemsRoom.setText("nothing in here");
	    else
	    	txtItemsRoom.setText(currentScence.getItem().toString());
	    
	    userInventory.setText(game.getPlayer().getInventoryText());

	    ImageIcon icon = new ImageIcon(currentScence.getImagePath());
	    Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
	    lblImage.setIcon(new ImageIcon(img));

	    btnChoice1.setText("<html>" + currentScence.getChoices().get(0).getText() + "</html>");
	    btnChoice2.setText("<html>" + currentScence.getChoices().get(1).getText() + "</html>");

	    //btnPickup.setVisible(scene.getItem() != null);
	}
}
