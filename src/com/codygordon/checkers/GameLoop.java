package com.codygordon.checkers;

public class GameLoop extends Thread {

	private boolean isRunning = true;
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			render();

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}
	
	public void kill() {
		isRunning = false;
	}
	
	public void update() {}
	
	public void render() {
		GameController.getInstance().update();
	}
}