package com.codygordon.checkers;

import java.awt.Color;
import java.util.ArrayList;

import com.codygordon.checkers.entities.Piece;
import com.codygordon.checkers.ui.Board;
import com.codygordon.checkers.ui.BoardSpot;
import com.codygordon.checkers.ui.GameFrame;
import com.codygordon.checkers.util.MoveHelper;

public class GameController {

	private Board board;
	private GameFrame frame;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private GameLoop loop;
	private BoardSpot currentMoveSelection;

	private static GameController instance;
	
	private int redScore = 0;
	private int blackScore = 0;

	private GameController() {
	}

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}

	public void startGame() {
		frame = new GameFrame();
		board = new Board();
		frame.add(board);
		initBoard();
		loop = new GameLoop();
		loop.run();
	}

	private void initBoard() {
		// red team
		for(int row = 5; row <= 7; row++) {
			for(int col = 0; col < 8; col += 2) {
				if(row == 6 && col == 0)
					col = 1;
				Piece piece = new Piece(Color.RED);
				pieces.add(piece);
				board.getBoardSpot(row, col).setPiece(piece);
			}
		}
		
		//black team
		for(int row = 0; row <= 2; row++) {
			for(int col = 0; col < 8; col+= 2) {
				if(row == 0 || row == 2) {
					if(col == 0) {
						col = 1;
					}
				}
				Piece piece = new Piece(Color.BLACK);
				pieces.add(piece);
				board.getBoardSpot(row, col).setPiece(piece);
			}
		}
	}

	public void update() {
		board.repaint();
	}

	public Board getBoard() {
		return this.board;
	}

	public ArrayList<Piece> getPieces() {
		return this.pieces;
	}

	public GameFrame getFrame() {
		return this.frame;
	}

	public void clearBorders() {
		board.forEachSpot(spot -> spot.removeBorder());
	}

	public void spotSelected(BoardSpot spot) {
		clearBorders();
		spot.addBorder();
		// TODO check if it's the players team
		if (currentMoveSelection == null) {
			if (spot.hasPiece()) {
				currentMoveSelection = spot;
				System.out.println("Spot selected");
			}
		}
		else if(!spot.hasPiece()) {
			System.out.println("Piece moved");
			if(MoveHelper.moveIsValid(currentMoveSelection, spot)) {
				spot.setPiece(currentMoveSelection.getPiece());
				if(MoveHelper.pieceShouldBeKinged(spot)) {
					currentMoveSelection.getPiece().king();
				}
				currentMoveSelection.removePiece();
				currentMoveSelection = null;
				clearBorders();
			} else {
				System.out.println("Move was invalid");
			}
		} else {
			currentMoveSelection = null;
		}
	}
}