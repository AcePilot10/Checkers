package com.codygordon.checkers.ui;

import java.awt.Color;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = -4055139307267640840L;
	
	public GameFrame() {
		setSize(500, 500);
		setTitle("Checkers");
		setBackground(Color.GREEN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}