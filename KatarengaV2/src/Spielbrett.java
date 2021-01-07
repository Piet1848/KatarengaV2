
import java.awt.Point;
import java.awt.event.KeyEvent;
import ledControl.BoardController;
import ledControl.gui.KeyBuffer;

public class Spielbrett {
	private static KeyBuffer buffer;
	public static BoardController controller;
	public boolean selectFeld;
	public int[][] feld;
	public int[][] figur;
	public int[][] posibility;
	public boolean figAnzeige;
	public long lastMovingTime;
	public final int speed = 200;
	public int x;
	public int y;
	public int p;
	public int q;
	public int punktePink;
	public int punkteWeiss;
	public int player;
	public Point xy;
	GameControll game;

	public static void main(String[] args) {
		new Spielbrett();
	}

	private Spielbrett() {
		play();
	}

	private void play() {
		player = 1;
		punktePink = 0;
		punkteWeiss = 0;
		game = new GameControll();
		lastMovingTime = System.currentTimeMillis();
		figAnzeige = true;
		posibility = new int[11][11];
		figur = new int[11][11];
		feld = new int[11][11];
		selectFeld = false;

		controller = BoardController.getBoardController();
		reset();
		generate();
		felder();
		figuren();
		controller.updateLedStripe();
		while (punktePink < 2 && punkteWeiss < 2) {
			key();
			if (System.currentTimeMillis() - lastMovingTime >= speed) {
				wechsel();
			}
			if (System.currentTimeMillis() - lastMovingTime >= speed * 2) {
				xy = game.play(player);
				x = xy.x;
				y = xy.y;
				if (x != p) {
					controller.addColor(p, q, 0, 0, 0);
					controller.updateLedStripe();
				}
				lastMovingTime = System.currentTimeMillis();
			}
		}
		controller.resetColors();
		if (punktePink == 2) {
			for (int i = 0; i <= 11; i++) {
				for (int j = 0; j <= 11; j++) {
					controller.addColor(i, j, 127, 0, 127);
				}
			}
			System.out.println("Pink hat gewonnen! :D");
			double end = System.currentTimeMillis();
			while(System.currentTimeMillis()-end < 500) {}//Pause
			play();
		} else {
			for (int i = 0; i <= 11; i++) {
				for (int j = 0; j <= 11; j++) {
					controller.addColor(i, j, 127, 127, 127);
				}
			}
			System.out.println("Weiß hat gewonnen! :P");
			double end = System.currentTimeMillis();
			while(System.currentTimeMillis()-end < 500) {}//Pause
			play();
		}
		controller.updateLedStripe();
	}

	public void generate() {
		for (int i = 0; i < 10; i++) {
			controller.setColor(i, 0, 70, 70, 70);
			controller.setColor(i, 9, 70, 70, 70);
		}
		for (int i = 0; i < 10; i++) {
			controller.setColor(0, i, 70, 70, 70);
			controller.setColor(9, i, 70, 70, 70);
		}

		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				feld[i][j] = 5;
			}
		}

		for (int k = 1; k < 8; k += 2) {
			for (int l = 1; l < 8; l += 2) {
				for (int i = 0; i < 4; i++) {
					int x = (int) (Math.random() * 2);
					int y = (int) (Math.random() * 2);
					while (feld[x + k][y + l] != 5) {
						x = (int) (Math.random() * 2);
						y = (int) (Math.random() * 2);
					}
					feld[x + k][y + l] = i;
				}
			}
		}

		for (int i = 1; i < 9; i++) {
			figur[i][1] = 1;
		}
		for (int i = 1; i < 9; i++) {
			figur[i][8] = 2;
		}
	}

	public void figuren() {
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (figur[i][j] == 1) {
					controller.setColor(i, j, 127, 127, 127);
				} else if (figur[i][j] == 2) {
					controller.setColor(i, j, 127, 0, 127);
				}
			}
		}
	}

	public void felder() {
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (feld[i][j] == 0) {
					controller.setColor(i, j, 100, 0, 0); //Rot
				} else if (feld[i][j] == 1) {
					controller.setColor(i, j, 0, 100, 0); //Grün
				} else if (feld[i][j] == 2) {
					controller.setColor(i, j, 0, 0, 100); //Blau
				} else if (feld[i][j] == 3) {
					controller.setColor(i, j, 100, 100, 0); //Gelb
				}
			}
		}
	}

	public void wechsel() {
		felder();
		if (figAnzeige) {
			figuren();
		}
		controller.updateLedStripe();
	}

	public void key() {
		buffer = controller.getKeyBuffer();
		KeyEvent event = buffer.pop();

		if (event != null) {
			if (event.getID() == java.awt.event.KeyEvent.KEY_PRESSED) {
				switch (event.getKeyCode()) {
				case java.awt.event.KeyEvent.VK_UP:
					figAnzeige = false;
					break;
				case java.awt.event.KeyEvent.VK_DOWN:
					figAnzeige = true;
					break;
				case java.awt.event.KeyEvent.VK_LEFT:
					if (selectFeld) {
						game.leftSelect(posibility, 1);
					} else {
						game.leftSelect(figur, player);
					}
					break;
				case java.awt.event.KeyEvent.VK_RIGHT:
					if (selectFeld) {
						game.rightSelect(posibility, 1);
					} else {
						game.rightSelect(figur, player);
					}
					break;
				case java.awt.event.KeyEvent.VK_ENTER:
					if (selectFeld) {
						figur[x][y] = figur[p][q];
						figur[p][q] = 0;
						selectFeld = false;
						if (player == 1) {
							player = 2;
						} else {
							player = 1;
						}
						geschlagen();
						if(punktePink < 2 && punkteWeiss < 2) {
							game.leftSelect(figur, player);
						}
					} else {
						p = x;
						q = y;
						selectFeld = true;
						posibility = game.select(feld, figur);
						game.rightSelect(posibility, 1);
					}
					break;
				case java.awt.event.KeyEvent.VK_SPACE:
					if (selectFeld) {
						if (player == 1) {
							if (q == 8) {
								selectFeld = false;
								figur[p][q] = 0;
								punkteWeiss++;
								System.out.println("Punkte Weiß: " + punkteWeiss);
								player = 2;
								game.leftSelect(figur, player);
							}
						} else {
							if (q == 1) {
								selectFeld = false;
								figur[p][q] = 0;
								punktePink++;
								System.out.println("Punkte Pink: " + punktePink);
								player = 1;
								game.leftSelect(figur, player);
							}
						}
					}
					break;
				default:
				}
			}
			buffer.clear();
		}
	}

	private boolean move(int startX, int startY, int endeX, int endeY) {
		if(controll(startX, startY, endeX, endeY)) {
			figur[endeX][endeY] = figur[startX][startY];
			figur[startX][startY] = 0;
			selectFeld = false;
			if (player == 1) {
				player = 2;
			} else {
				player = 1;
			}
			geschlagen();
			if(punktePink < 2 && punkteWeiss < 2) {
				game.leftSelect(figur, player);
			}
			return true;
		}
		return false;
	}
	
	private boolean getPoint(int startX, int startY) {
		if (player == 1) {
			if (startY == 8) {
				selectFeld = false;
				figur[startX][startY] = 0;
				punkteWeiss++;
				System.out.println("Punkte Weiß: " + punkteWeiss);
				player = 2;
				game.leftSelect(figur, player);
			}
		} else {
			if (startY == 1) {
				selectFeld = false;
				figur[startX][startY] = 0;
				punktePink++;
				System.out.println("Punkte Pink: " + punktePink);
				player = 1;
				game.leftSelect(figur, player);
			}
		}
		return false;
	}


	private boolean controll(int startX, int startY, int endeX, int endeY) {
		posibility = game.selectFeld(feld, figur, startX, startY);
		if(posibility[endeX][endeY] == 1) {
			return true;
		}
		return false;
	}

	private void geschlagen() {
		int weissFiguren = 0;
		int pinkFiguren = 0;

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (figur[i][j] == 1) {
					weissFiguren++;
				}
				if (figur[i][j] == 2) {
					pinkFiguren++;
				}
			}
		}

		for (int i = 0; i < 8 - weissFiguren - punkteWeiss; i++) {
			controller.addColor(11, i, 127, 127, 127);
		}

		for (int i = 8; i > pinkFiguren + punktePink; i--) {
			controller.addColor(i+3, 11, 127, 0, 127);
		}

		if(weissFiguren + punkteWeiss < 2) {
			punktePink = 2;
		}

		if(pinkFiguren + punktePink < 2) {
			punkteWeiss = 2;
		}
	}

	public void reset() {
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++){
				controller.addColor(i, j, 0, 0, 0);;
			}
		}
	}
}
