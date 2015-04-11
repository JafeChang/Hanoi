package info.jafe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HanoiWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5552212355424279950L;
	HanoiPanel panel;
	JButton restartB;
	JMenuBar menuBar = new JMenuBar();
	JMenu hanoiMenu = new JMenu("Hanoi");
	JMenu gameMenu = new JMenu("Game");
	JMenu helpMenu = new JMenu("Help");
	JMenuItem aboutM = new JMenuItem("about Hanoi", KeyEvent.VK_A);// TODO
	JMenuItem preferenceM = new JMenuItem("Preference", KeyEvent.VK_P);// TODO
	JMenuItem exitM = new JMenuItem("Exit Hanoi", KeyEvent.VK_E);// TODO
	JMenuItem newGameM = new JMenuItem("New Game", KeyEvent.VK_N);
	JMenuItem loadGameM = new JMenuItem("Load Game", KeyEvent.VK_L);// TODO
	JMenuItem saveGameM = new JMenuItem("Save Game", KeyEvent.VK_S);// TODO
	JMenuItem test = new JMenuItem("test");// TODO
	JMenuItem helpM = new JMenuItem("How to play?", KeyEvent.VK_H);// TODO

	public HanoiWindow() {
		Point screenSize = new Point(java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().width, java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().height);
		this.setBounds((int) (0.5 * screenSize.x - 400),
				(int) (0.5 * screenSize.y - 300), 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// restartB=new JButton("restart");
		// restartB.addActionListener(new BMonitor());
		// restartB.addKeyListener(new HMonitor());
		// this.add(restartB,"South");

		// panel=null;
		repaint();
		panel = new HanoiPanel(HanoiPanel.getHanoiNumber(), this);
		panel.setBackground(Color.WHITE);
		panel.repaint();

		// restartm.setText("New Game");
		MenuMonitor mMonitor = new MenuMonitor();

		preferenceM.addActionListener(mMonitor);
		hanoiMenu.add(aboutM);
		hanoiMenu.addSeparator();
		hanoiMenu.add(preferenceM);
		hanoiMenu.addSeparator();
		hanoiMenu.add(exitM);

		newGameM.addActionListener(mMonitor);
		test.addActionListener(mMonitor);// TODO
		gameMenu.add(newGameM);
		gameMenu.addSeparator();
		gameMenu.add(loadGameM);
		gameMenu.add(saveGameM);
		gameMenu.addSeparator();
		gameMenu.add(test);// TODO

		helpMenu.add(helpM);

		menuBar.add(hanoiMenu);
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);

		this.addKeyListener(new HMonitor());
		this.add(panel);
		this.setJMenuBar(menuBar);

		this.setVisible(true);

	}

	// public void restart(int hanoiNumber) {
	//
	// }
	public void success() {
		int opt = JOptionPane
				.showOptionDialog(
						this,
						"Congratulations! You have successed this game! Would you like a new game?",
						"Congratulations", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, 0);
		if (opt == 0) {
			panel.restart(HanoiPanel.getHanoiNumber());// TODO
		}
	}

	private HanoiWindow getHanoiWindow() {
		return this;
	}

	class HMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				panel.up();
				panel.repaint();
				break;
			case KeyEvent.VK_DOWN:
				panel.down();
				panel.repaint();
				break;
			case KeyEvent.VK_LEFT:
				panel.left();
				panel.repaint();
				break;
			case KeyEvent.VK_RIGHT:
				panel.right();
				panel.repaint();
				break;
			default:
				break;
			}
		}
	}

	// class BMonitor implements ActionListener {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// if (e.getSource() == restartB) {
	// // System.out.println("!");
	// // restart(9);
	// // requestFocusInWindow();
	// panel.restart();
	// panel.repaint();
	// }
	// }
	//
	// }

	class MenuMonitor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == preferenceM) {
//				tt.wait();
				new preferenceFrame(getHanoiWindow());
			} else if (e.getSource() == newGameM) {
				panel.restart(HanoiPanel.getHanoiNumber());
				panel.repaint();
			} else if (e.getSource() == test) {// TODO
				success();
			}
		}

	}

}

class preferenceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 549910809556598971L;

	private JButton cancelB = new JButton("Cancel");
	private JButton okB = new JButton("OK");
	private JPanel buttonP = new JPanel();
	private HanoiWindow hanoiWindow;
	private boolean changed = false;
	private int hanoiNumber = HanoiPanel.getHanoiNumber();
	private JSlider slider = new JSlider(JSlider.HORIZONTAL, 3, 9, hanoiNumber);
	private JLabel label = new JLabel(hanoiNumber + "");
	private JLabel informationLabel = new JLabel("Hanoi Order");
	private JPanel slidePanel = new JPanel();
	private JLabel label3=new JLabel("3");
	private JLabel label9=new JLabel("9");

	preferenceFrame(HanoiWindow hanoiWindow) {
		this.hanoiWindow = hanoiWindow;
		Point screenSize = new Point(java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().width, java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().height);
		this.setBounds((int) (0.5 * screenSize.x - hanoiWindow.getWidth() / 4),
				(int) (0.5 * screenSize.y - hanoiWindow.getHeight() / 6),
				hanoiWindow.getWidth() / 2, hanoiWindow.getHeight() / 3);

		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		slidePanel.setLayout(null);
		informationLabel.setBounds(50, 10, 200, 20);
		informationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		slider.setBounds(50, this.getHeight() / 2-20 , 300, 20);
		label.setBounds(this.getWidth() / 2 - 5, this.getHeight() / 2 - 60, 40,
				20);
		label.setFont(new Font("Dialog",Font.BOLD,20));
		label3.setBounds(this.getWidth()/2-slider.getWidth()/2+10, this.getHeight()/2+5, 20, 10);
		label9.setBounds(this.getWidth()/2+slider.getWidth()/2-20, this.getHeight()/2+5, 20, 10);
		slider.setMajorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.addChangeListener(new CMonitor());
		slidePanel.add(informationLabel);
		slidePanel.add(slider);
		slidePanel.add(label);
		slidePanel.add(label3);
		slidePanel.add(label9);
		this.add(slidePanel, BorderLayout.CENTER);

		buttonP.add(cancelB);
		buttonP.add(okB);
		cancelB.addActionListener(new BMonitor());
		okB.addActionListener(new BMonitor());

		this.add(buttonP, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private preferenceFrame getThis() {
		return this;
	}

	class BMonitor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == okB) {
				if (changed) {
					int opt = JOptionPane
							.showOptionDialog(
									getThis(),
									"Game setting has been changed, do you want to start a new game?",
									"", JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.INFORMATION_MESSAGE, null,
									null, 1);
					if (opt == 0) {
						hanoiWindow.panel.restart(hanoiNumber);
						dispose();
					}
				} else {
					dispose();
				}

			}
			if (e.getSource() == cancelB) {
				dispose();
			}
		}

	}

	class CMonitor implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == slider) {
				label.setText(slider.getValue() + "");
				changed = true;
				hanoiNumber = slider.getValue();
			}
		}

	}
}
