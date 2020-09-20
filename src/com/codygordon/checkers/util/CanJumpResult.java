package com.codygordon.checkers.util;

import com.codygordon.checkers.entities.Piece;
import com.codygordon.checkers.ui.BoardSpot;

public class CanJumpResult extends CanMoveResult {
	public boolean canJump = false;
	public Piece pieceToJump;
	public BoardSpot targetSpot;
}