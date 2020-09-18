package com.codygordon.checkers.util;

import java.awt.Color;

import com.codygordon.checkers.GameController;
import com.codygordon.checkers.entities.Piece;
import com.codygordon.checkers.ui.BoardSpot;

public class MoveHelper {

	public static boolean moveIsValid(BoardSpot from, BoardSpot to) {
		int[] fromLocation = GameController.getInstance().getBoard().getBoardSpot(from);
		int[] toLocation = GameController.getInstance().getBoard().getBoardSpot(to);
		int fromY = fromLocation[0];
		int fromX = fromLocation[1];
		int toY = toLocation[0];
		int toX = toLocation[1];
		int deltaX = toX - fromX;
		int deltaY = toY - fromY;
		Piece piece = from.getPiece();
		//Assuming red is the bottom team
		Color color = from.getPiece().getColor();
		if(deltaY == 0 || deltaX == 0) 
			return false;
		
		if(canJump(from, to))
			return true;
		
		if(piece.isKinged())
			if(deltaY == 1 || deltaY == -1) {
				return true;
			}
		if(color == Color.RED) {
			//red team can only go up
			if(deltaY == -1) {
				return true;
			}
			//Check for jump
			else if(deltaY == -2) {
				return true;
			}
		} else {
			//black team can only go down
			if(deltaY == 1) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean pieceShouldBeKinged(BoardSpot newSpot) {
		Piece piece = newSpot.getPiece();
		Color color = piece.getColor();
		int spotY = GameController.getInstance().getBoard().getBoardSpot(newSpot)[0];
		if(color == Color.RED) {
			if(spotY == 0)
				return true;
		} else if(color == Color.BLACK) {
			if(spotY == 7)
				return true;
		}
		return false;
	}
	
	public static boolean canJump(BoardSpot oldSpot, BoardSpot newSpot) {
		int[] fromLocation = GameController.getInstance().getBoard().getBoardSpot(oldSpot);
		int[] toLocation = GameController.getInstance().getBoard().getBoardSpot(newSpot);
		int fromY = fromLocation[0];
		int fromX = fromLocation[1];
		int toY = toLocation[0];
		int toX = toLocation[1];
		int deltaX = toX - fromX;
		int deltaY = toY - fromY;
		Piece piece = oldSpot.getPiece();
		Color movedPieceColor = piece.getColor();
		if(movedPieceColor == Color.RED) {
			if(deltaY == -2) {
				if(deltaX < 0) {
					//move left
					int jumpedX = toX - 1;
					int jumpedY = toY + 1;
					BoardSpot spotToJump = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
					if(spotToJump.hasPiece()) {
						if(spotToJump.getPiece().getColor() != movedPieceColor) {
							return true;
						}
					}
				} else if(deltaX > 0) {
					//move right
					int jumpedX = toX + 1;
					int jumpedY = toY + 1;
					BoardSpot spotToJump = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
					if(spotToJump.hasPiece()) {
						if(spotToJump.getPiece().getColor() != movedPieceColor) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}