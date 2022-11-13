package engine;

import screen.SaveInfoScreen;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Implements an object that stores the persistent game state.
 * 
 * @author <a href="mailto:rhdqor213@gmail.com">Junho Lee</a>
 * 
 */
public final class PermanentState {

	/** Money used in game. */
	private static int coin = 0;
	/** Current ship shape. */
	private int shipShape = FileManager.getPlayerShipShape();
	/** Current ship color. */
	private int shipColor = FileManager.getPlayerShipColor();
	/** Current background Music. */
	private int BGM = 1;
	/** Current bullet sound effect. */
	private int bulletSFX = 1;
	/** Application logger. */

	private int slotNum = 0;
	private static Logger logger;

	private static PermanentState ps;

	private PermanentState() {
		this.coin = 0;

//				this.slotNum = sis.getSlotNum();
	}

	public static PermanentState getInstance(){
		if (ps == null)
			ps = new PermanentState();
		return ps;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int income , int slotNum) {
		this.coin += income;
		try {
			FileManager.getInstance().saveCoins(coin, slotNum, FileManager.getInstance().loadCoins(0),
					FileManager.getInstance().loadCoins(1), FileManager.getInstance().loadCoins(0));
		}
		catch(IOException e){

		}

	}

	public int getShipShape() {
		return shipShape;
	}

	public void setShipShape(int shape) {
		this.shipShape = shape;
	}

	public int getShipColor() {
		return shipColor;
	}

	public void setShipColor(int color) { this.shipColor = color; }

	public int getBGM() {
		return BGM;
	}

	public void setBGM(int bgm) { this.BGM = bgm; }

	public int getBulletSFX() {
		return bulletSFX;
	}

	public void setBulletSFX(int sfx) { this.bulletSFX = sfx; }

	public void setSlotCoin(int SlotCoin){this.coin = SlotCoin;}
}
