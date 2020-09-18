package com.codygordon.checkers.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.codygordon.checkers.Settings;
import com.codygordon.checkers.ui.BoardSpot;

public class Piece {
	
	public int[] location = new int[2];	

	private Color color;
	private boolean isKinged = false;
	
	public Piece(Color color) {
		this.color = color;
	}
	
	public void paint(Graphics g, BoardSpot spot) {
		int size = Settings.PIECE_SIZE;
		int x =(spot.getWidth() - size) / 2;
		int y = (spot.getHeight() - size) / 2;
		g.setColor(color);
		g.fillOval(x, y, size, size);
		
		if(isKinged) {
			size = Settings.PIECE_SIZE / 2;
			x =(spot.getWidth() - size) / 2;
			y = (spot.getHeight() - size) / 2;
			g.setColor(Color.BLUE);
			g.fillOval(x, y, size, size);
		}
	}
	
	public void move(int row, int col) {
		location[0] = row;
		location[1] = col;
	}
	
	public void king() {
		this.isKinged = true;
	}
	
	public boolean isKinged() {
		return this.isKinged;
	}
	
	public int[] getLocation() {
		return location;
	}
	
	public Color getColor() {
		return this.color;
	}
}