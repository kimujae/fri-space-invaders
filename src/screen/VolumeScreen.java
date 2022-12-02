package screen;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import engine.Cooldown;
import engine.Core;
import sound.SoundPlay;
import sound.SoundType;

/**
 * Implements the title screen.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class VolumeScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 150;
	
	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;
	private SoundPlay soundPlay = SoundPlay.getInstance();
	private int menuCode;
	private int soundCode;
	private int updown = 0;
	private int leftright =0;

	/**
	 * Constructor, establishes the properties of the screen.
	 * 
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */
	public VolumeScreen(final int width, final int height, final int fps) {
		super(width, height, fps);
		this.menuCode = 1;

		this.bgmVolumeControl(5);

		this.soundCode = 100;
		// Defaults to play.
		this.returnCode = 0;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
	}

	/**
	 * Starts the action.
	 * 
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update();

		draw();
		if (this.selectionCooldown.checkFinished()
				&& this.inputDelay.checkFinished()) {
			if (inputManager.isKeyDown(KeyEvent.VK_UP)
					|| inputManager.isKeyDown(KeyEvent.VK_W)) {
				updown = 1;
				previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
					|| inputManager.isKeyDown(KeyEvent.VK_S)) {
				updown = 1;
				nextMenuItem();
				this.selectionCooldown.reset();
			}
			if ((this.soundCode == 100 || this.soundCode == 101) && (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
					|| inputManager.isKeyDown(KeyEvent.VK_D))) {
				leftright = 1;
				nextMenuItem();
				this.selectionCooldown.reset();
			}
			if ((this.soundCode == 100 || this.soundCode == 101) && (inputManager.isKeyDown(KeyEvent.VK_LEFT)
					|| inputManager.isKeyDown(KeyEvent.VK_A))) {
				leftright = 1;
				previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE)&&this.returnCode==6){
				this.isRunning = false;
				soundPlay.play(SoundType.menuClick);
			}


			}

		}








	@FunctionalInterface
	public interface VolumeControl{
		void control( int left_right, int menu_code);
	}
	VolumeControl next_bgmVolume = ( a, b) -> {if(b == 10) this.menuCode =1;
		else this.menuCode ++; this.bgmVolumeControl(this.menuCode * 5);};

	VolumeControl next_effectVolume = ( a, b) -> {if(b == 20) this.menuCode =11;
		else this.menuCode ++; this.effectVolumeControl((this.menuCode-10) * 5 + (this.menuCode-11)*4);};

	VolumeControl prev_bgmVolume = ( a, b) -> {if(b == 1) this.menuCode =10;
	else this.menuCode --; this.bgmVolumeControl(this.menuCode * 5);};

	VolumeControl prev_effectVolume = ( a, b) -> {if(b == 11) this.menuCode =20;
	else this.menuCode --; this.effectVolumeControl((this.menuCode-10) * 5 + (this.menuCode-11)*4);};





	/**
	 * Shifts the focus to the next menu item.
	 */
	private void nextMenuItem() {
		if (updown == 1 && this.soundCode == 100) {
			this.soundCode ++;
			this.menuCode = 11;
		}
		else if(updown == 1 && this.soundCode == 101) {
			this.soundCode++;
			this.menuCode = 0;
			this.returnCode = 6;
		}
		else if(updown == 1 && this.soundCode  == 102) {
			this.soundCode=100;
			this.menuCode =1;

		}

		else if(menuCode <=10)
		next_bgmVolume.control(leftright,menuCode);

		else
		next_effectVolume.control(leftright,menuCode);



		soundPlay.play(SoundType.menuSelect);
		updown =0;
		leftright =0;
	}


	/**
	 * Shifts the focus to the previous menu item.
	 */
	private void previousMenuItem() {
		if (updown == 1 && this.soundCode == 100) {
			this.soundCode = 102;
			this.menuCode = 0;
			this.returnCode = 6;
		}
		else if(updown == 1 && this.soundCode == 101) {
			this.soundCode--;
			this.menuCode =1;
		}
		else if(updown == 1 && this.soundCode == 102) {
			this.soundCode--;
			this.menuCode = 11;

		}
		else if(menuCode <=10)
			prev_bgmVolume.control(leftright,menuCode);

		else
			prev_effectVolume.control(leftright,menuCode);



		soundPlay.play(SoundType.menuSelect);
		updown =0;
		leftright =0;
	}


	private void bgmVolumeControl(int value){
		if((soundPlay.getBgmVolume()<100||value==-1)&&(soundPlay.getBgmVolume()>0||value==1)) {
			soundPlay.setBgmVolume(value);
		}
	}

	private void effectVolumeControl(int value) {
		if((soundPlay.getEffectVolume()<100||value==-1)&&(soundPlay.getEffectVolume()>0||value==1)) {
			soundPlay.setEffectVolume(value);

		}
	}

	private boolean isBgm(){
		return this.returnCode == 100;
	}

	private boolean isEffect(){
		return this.returnCode == 101;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
			drawManager.initDrawing(this);
			
			drawManager.drawVolumeTitle(this);
			drawManager.drawVolume(this, 100, soundCode, menuCode);
			
			drawManager.completeDrawing(this);
		
	}
}


