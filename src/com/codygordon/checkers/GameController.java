package com.codygordon.checkers;

import java.awt.Color;
import java.util.ArrayList;

import com.codygordon.checkers.ai.AIHelper;
import com.codygordon.checkers.ai.CanMoveOnceResult;
import com.codygordon.checkers.entities.Piece;
import com.codygordon.checkers.ui.Board;
import com.codygordon.checkers.ui.BoardSpot;
import com.codygordon.checkers.ui.GameFrame;
import com.codygordon.checkers.ui.MenuPanel;
import com.codygordon.checkers.util.CanJumpResult;
import com.codygordon.checkers.util.CanMoveResult;
import com.codygordon.checkers.util.MoveHelper;

public class GameController {

	private Board board;
	private GameFrame frame;
	private ArrayList<Piece> pieces;
	private GameLoop loop;
	private BoardSpot currentMoveSelection;

	private static GameController instance;

	private int redScore = 0;
	private int blackScore = 0;
	private Color currentPlayer = Color.RED;

	private boolean singleplayer = false;

	private GameController() {
	}

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}

	public void start() {
		frame = new GameFrame();
		MenuPanel menu = new MenuPanel();
		frame.add(menu);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void playSingleplayer() {
		singleplayer = true;
		startGame();
	}
	
	public void playMultiplayer() {
		singleplayer = false;
		startGame();
	}
	
	private void startGame() {
		pieces = new ArrayList<Piece>();
		frame.getContentPane().removeAll();
		board = new Board();
		frame.add(board);
		initBoard();
		frame.pack();
		frame.setVisible(true);
		loop = new GameLoop();
		loop.run();
	}

	private void initBoard() {
		// red team
		for (int row = 5; row <= 7; row++) {
			for (int col = 0; col < 8; col += 2) {
				if (row == 6 && col == 0)
					col = 1;
				Piece piece = new Piece(Color.RED);
				pieces.add(piece);
				board.getBoardSpot(row, col).setPiece(piece);
			}
		}

		// black team
		for (int row = 0; row <= 2; row++) {
			for (int col = 0; col < 8; col += 2) {
				if (row == 0 || row == 2) {
					if (col == 0) {
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
		frame.repaint();
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
		if (spot.hasPiece()) {
			if (spot.getPiece().getColor() == currentPlayer) {
				selectSpot(spot);
				clearBorders();
				spot.addBorder();
				System.out.println("Spot selected");
			}

		} else if (!spot.hasPiece() && currentMoveSelection != null) {
			MoveHelper.doMove(currentMoveSelection, spot);
		}
	}

	public void selectSpot(BoardSpot spot) {
		currentMoveSelection = spot;
	}

	public void jumpPiece(Piece pieceToJump, Piece jumpingPiece) {
		board.forEachSpot((spot) -> {
			if (spot.getPiece() == pieceToJump) {
				spot.removePiece();
			}
		});

		pieces.remove(pieceToJump);

		if (jumpingPiece.getColor() == Color.RED) {
			redScore++;
			getFrame().updateRedScore(redScore);
		} else {
			blackScore++;
			getFrame().updateBlackScore(blackScore);
		}
		System.out.println("Current Score");
		System.out.println("Red: " + redScore);
		System.out.println("Black: " + blackScore);
		checkForEndOfGame();
	}

	private void checkForEndOfGame() {
		int redPieces = 0;
		int bluePieces = 0;
		for (Piece piece : pieces) {
			if (piece.getColor() == Color.RED)
				redPieces++;
			else
				bluePieces++;
		}
		if (redPieces == 0) {
			getFrame().gameOver(Color.BLACK);
		} else if (bluePieces == 0) {
			getFrame().gameOver(Color.RED);
		}
	}

	public void swapCurrentPlayer() {
		if (currentPlayer == Color.RED) {
			currentPlayer = Color.BLACK;
			if (singleplayer) {
				doAIMove();
			}
		} else {
			currentPlayer = Color.RED;
		}
		frame.updateCurrentTeamText(currentPlayer);
	}

	public void doAIMove() {
		if (currentPlayer != Color.BLACK)
			return;

		CanMoveResult move = AIHelper.findPieceToMove();
		currentMoveSelection = move.fromSpot;
		BoardSpot from = move.fromSpot;
		if (move instanceof CanJumpResult) {
			CanJumpResult result = (CanJumpResult) move;
			MoveHelper.doMove(from, result.targetSpot);
		} else if (move instanceof CanMoveResult) {
			CanMoveOnceResult result = (CanMoveOnceResult) move;
			MoveHelper.doMove(from, result.targetSpot);
		}
	}

	public void movePiece(BoardSpot to) {
		to.setPiece(currentMoveSelection.getPiece());
		currentMoveSelection.removePiece();
		currentMoveSelection = null;
		clearBorders();
	}
}