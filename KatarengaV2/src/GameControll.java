import java.awt.Point;
import java.util.Arrays;
import ledControl.BoardController;

public class GameControll {
	int x = 1;
	int y = 1;
	int player;

	public boolean ready = false;
	// public int[][] figur = new int[11][11];
	public int[][] posibility = new int[11][11];
	public static BoardController controller = BoardController.getBoardController();


	public Point play(int player) {
		this.player = player;
		if (player == 1) {
			controller.setColor(0, 0, 127, 127, 127);
			controller.setColor(x, y, 0, 0, 0);
			controller.updateLedStripe();

		} else {
			controller.setColor(0, 0, 127, 0, 127);
			controller.setColor(x, y, 0, 0, 0);
			controller.updateLedStripe();
		}
		controller.updateLedStripe();
		return new Point(x, y);
	}

	public void rightSelect(int[][] figur, int compare) {
		int i = x + 1;
		int j = y;
		if (i > 8) {
			i = 1;
			j++;
			if (j > 8) {
				j = 1;
			}
		}
		while (figur[i][j] != compare) {
			i++;
			if (i > 8) {
				i = 1;
				j++;
				if (j > 8) {
					j = 1;
				}
			}
		}
		x = i;
		y = j;
	}

	public void leftSelect(int[][] figur, int compare) {
		int i = x - 1;
		int j = y;
		if (i < 0) {
			i = 8;
			j--;
			if (j < 0) {
				j = 8;
			}
		}
		while (figur[i][j] != compare) {
			i--;
			if (i < 0) {
				i = 8;
				j--;
				if (j < 0) {
					j = 8;
				}
			}
		}
		x = i;
		y = j;
	}

	public int[][] select(int[][] feld, int[][] figur) {
		if (feld[x][y] == 0) {
			turm(feld, figur);
		} else if (feld[x][y] == 1) {
			springer(figur);
		} else if (feld[x][y] == 2) {
			koenig(figur);
		} else if (feld[x][y] == 3) {
			laeufer(feld, figur);
		}
		return posibility;
	}
	
	public int[][] selectFeld(int[][] feld, int[][] figur, int xPos, int yPos){
		if (feld[xPos][yPos] == 0) {
			turm(feld, figur);
		} else if (feld[xPos][yPos] == 1) {
			springer(figur);
		} else if (feld[xPos][yPos] == 2) {
			koenig(figur);
		} else if (feld[xPos][yPos] == 3) {
			laeufer(feld, figur);
		}
		return posibility;
	}

	public void turm(int[][] feld, int[][] figur) {//---------------Fertig.
		clearPosibility();
		System.out.println("Turm");

		boolean goon1 = true;
		boolean goon2 = true;
		boolean goon3 = true;
		boolean goon4 = true;

		for (int m = 1; m <= 8; m++) {
			if (x + m <= 8 && goon1) {
				if (figur[x + m][y] != player) {
					posibility[x + m][y] = 1;
				}
				if(figur[x + m][y] != 0) {
					goon1 = false;
				}
				if (feld[x + m][y] == 0) {
					goon1 = false;
				}
			}
			if (x - m > 0 && goon2) {
				if (figur[x - m][y] != player) {
					posibility[x - m][y] = 1;
				}
				if(figur[x - m][y] != 0) {
					goon2 = false;
				}
				if (feld[x - m][y] == 0) {
					goon2 = false;
				}
			}
		}

		for (int m = 1; m <= 8; m++) {
			if (y + m <= 8 && goon3) {
				if (figur[x][y + m] != player) {
					posibility[x][y + m] = 1;
				}
				if(figur[x][y + m] != 0) {
					goon3 = false;
				}
				if (feld[x][y + m] == 0) {
					goon3 = false;
				}
			}
			if (y - m > 0 && goon4) {
				if (figur[x][y - m] != player) {
					posibility[x][y - m] = 1;
				}
				if(figur[x][y - m] != 0) {
					goon4 = false;
				}
				if (feld[x][y - m] == 0) {
					goon4 = false;
				}
			}
		}
	}

	private void clearPosibility() {
		for (int i = 0; i < posibility.length; i++) {
			Arrays.fill(posibility[i], 0);
		}
	}

	public void springer(int[][] figur) { // -----------------Fertig.
		clearPosibility();
		System.out.println("Springer");

		if (x + 1 <= 8) {
			if (y + 2 <= 8) {
				if (figur[x + 1][y + 2] != player) {
					posibility[x + 1][y + 2] = 1;
				}
			}
			if (y - 2 > 0) {
				if (figur[x + 1][y - 2] != player) {
					posibility[x + 1][y - 2] = 1;
				}
			}
		}
		if (x - 1 > 0) {
			if (y + 2 <= 8) {
				if (figur[x - 1][y + 2] != player) {
					posibility[x - 1][y + 2] = 1;
				}
			}
			if (y - 2 > 0) {
				if (figur[x - 1][y - 2] != player) {
					posibility[x - 1][y - 2] = 1;
				}
			}
		}
		if (x + 2 <= 8) {
			if (y + 1 <= 8) {
				if (figur[x + 2][y + 1] != player) {
					posibility[x + 2][y + 1] = 1;
				}
			}
			if (y - 1 > 0) {
				if (figur[x + 2][y - 1] != player) {
					posibility[x + 2][y - 1] = 1;
				}
			}
		}
		if (x - 2 > 0) {
			if (y + 1 <= 8) {
				if (figur[x - 2][y + 1] != player) {
					posibility[x - 2][y + 1] = 1;
				}
			}
			if (y - 1 > 0) {
				if (figur[x - 2][y - 1] != player) {
					posibility[x - 2][y - 1] = 1;
				}
			}
		}
	}

	public void laeufer(int[][] feld, int[][] figur) { // ---------------Fertig.
		clearPosibility();
		System.out.println("Läufer");
		boolean goon1 = true;
		boolean goon2 = true;
		boolean goon3 = true;
		boolean goon4 = true;
		for (int m = 1; m <= 8; m++) {
			if (x + m <= 8 && y + m <= 8 && goon1) {
				if (figur[x + m][y + m] != player) {
					posibility[x + m][y + m] = 1;
				}
				if (figur[x + m][y + m] != 0) {
					goon1 = false;
				}
				if (feld[x + m][y + m] == 3) {
					goon1 = false;
				}
			}
			if (x + m <= 8 && y - m > 0 && goon2) {
				if (figur[x + m][y - m] != player) {
					posibility[x + m][y - m] = 1;
				}
				if (figur[x + m][y - m] != 0) {
					goon2 = false;
				}
				if (feld[x + m][y - m] == 3) {
					goon2 = false;
				}
			}
			if (x - m > 0 && y + m <= 8 && goon3) {
				if (figur[x - m][y + m] != player) {
					posibility[x - m][y + m] = 1;
				}
				if (figur[x - m][y + m] != 0) {
					goon3 = false;
				}
				if (feld[x - m][y + m] == 3) {
					goon3 = false;
				}
			}
			if (x - m > 0 && y - m > 0 && goon4) {
				if (figur[x - m][y - m] != player) {
					posibility[x - m][y - m] = 1;
				}
				if (figur[x - m][y - m] != 0) {
					goon4 = false;
				}
				if (feld[x - m][y - m] == 3) {
					goon4 = false;
				}
			}
		}

	}

	public void koenig(int[][] figur) { // -----------------Fertig.
		clearPosibility();
		System.out.println("König");

		if (x + 1 <= 8) {
			if (figur[x + 1][y] != player) {
				posibility[x + 1][y] = 1;
			}
			if (y + 1 <= 8) {
				if (figur[x + 1][y + 1] != player) {
					posibility[x + 1][y + 1] = 1;
				}
				if (figur[x][y + 1] != player) {
					posibility[x][y + 1] = 1;
				}
			}
			if (y - 1 > 0) {
				if (figur[x + 1][y - 1] != player) {
					posibility[x + 1][y - 1] = 1;
				}
				if (figur[x][y - 1] != player) {
					posibility[x][y - 1] = 1;
				}
			}
		}
		if (x - 1 > 0) {
			if (figur[x - 1][y] != player) {
				posibility[x - 1][y] = 1;
			}
			if (y + 1 <= 8) {
				if (figur[x - 1][y + 1] != player) {
					posibility[x - 1][y + 1] = 1;
				}
				if (figur[x][y + 1] != player) {
					posibility[x][y + 1] = 1;
				}
			}
			if (y - 1 > 0) {
				if (figur[x - 1][y - 1] != player) {
					posibility[x - 1][y - 1] = 1;
				}
				if (figur[x][y - 1] != player) {
					posibility[x][y - 1] = 1;
				}
			}
		}
	}
}
