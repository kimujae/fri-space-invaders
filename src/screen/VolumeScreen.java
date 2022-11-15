package screen;

import java.awt.event.KeyEvent;

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

		else if(leftright ==1 && this.menuCode ==1) {
			menuCode++;
			bgmVolumeControl(10);
		}
		else if(leftright ==1 && this.menuCode ==2) {
			menuCode++;
			bgmVolumeControl(15);
		}
		else if(leftright ==1 && this.menuCode  ==3) {
			menuCode++;
			bgmVolumeControl(20);
		}
		else if(leftright ==1 && this.menuCode  ==4) {
			menuCode++;
			bgmVolumeControl(25);
		}
		else if(leftright ==1 && this.menuCode  ==5) {
			menuCode++;
			bgmVolumeControl(30);
		}
		else if(leftright ==1 && this.menuCode  ==6) {
			menuCode++;
			bgmVolumeControl(35);
		}
		else if(leftright ==1 && this.menuCode  ==7) {
			menuCode++;
			bgmVolumeControl(40);
		}
		else if(leftright ==1 && this.menuCode ==8) {
			menuCode++;
			bgmVolumeControl(45);
		}
		else if(leftright ==1 && this.menuCode  ==9) {
			menuCode++;
			bgmVolumeControl(55);
		}
		else if(leftright ==1 && this.menuCode  ==10) {
			menuCode = 1;
			bgmVolumeControl(5);
		}

		else if(leftright ==1 && this.menuCode ==11) {
			menuCode++;
			effectVolumeControl(9);
		}
		else if(leftright ==1 && this.menuCode ==12) {
			menuCode++;
			effectVolumeControl(13);
		}
		else if(leftright ==1 && this.menuCode  ==13){
			menuCode ++;
			effectVolumeControl(17);
		}
		else if(leftright ==1 && this.menuCode  ==14) {
			menuCode++;
			effectVolumeControl(21);
		}
		else if(leftright ==1 && this.menuCode  ==15) {
			menuCode++;
			effectVolumeControl(25);
		}
		else if(leftright ==1 && this.menuCode  ==16) {
			menuCode++;
			effectVolumeControl(29);
		}
		else if(leftright ==1 && this.menuCode  ==17) {
			menuCode++;
			effectVolumeControl(33);
		}
		else if(leftright ==1 && this.menuCode ==18) {
			menuCode++;
			effectVolumeControl(37);
		}
		else if(leftright ==1 && this.menuCode  ==19) {
			menuCode++;
			effectVolumeControl(50);
		}
		else if(leftright ==1 && this.menuCode  ==20) {
			menuCode = 11;
			effectVolumeControl(5);
		}
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
			this.menuCode =0;
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
		else if(leftright ==1 && this.menuCode ==1) {
			menuCode = 10;
			bgmVolumeControl(55);
		}
		else if(leftright ==1 && this.menuCode ==2) {
			menuCode--;
			bgmVolumeControl(5);
		}
		else if(leftright ==1 && this.menuCode  ==3) {
			menuCode--;
			bgmVolumeControl(10);
		}
		else if(leftright ==1 && this.menuCode  ==4) {
			menuCode--;
			bgmVolumeControl(15);
		}
		else if(leftright ==1 && this.menuCode  ==5) {
			menuCode--;
			bgmVolumeControl(20);
		}
		else if(leftright ==1 && this.menuCode  ==6) {
			menuCode--;
			bgmVolumeControl(25);
		}
		else if(leftright ==1 && this.menuCode  ==7) {
			menuCode--;
			bgmVolumeControl(30);
		}
		else if(leftright ==1 && this.menuCode ==8) {
			menuCode--;
			bgmVolumeControl(35);
		}
		else if(leftright ==1 && this.menuCode  ==9) {
			menuCode--;
			bgmVolumeControl(40);
		}
		else if(leftright ==1 && this.menuCode  ==10) {
			menuCode--;
			bgmVolumeControl(45);
		}


		else if(leftright ==1 && this.menuCode ==11) {
			this.menuCode = 20;
			effectVolumeControl(50);
		}
		else if(leftright ==1 && this.menuCode ==12) {
			this.menuCode--;
			effectVolumeControl(5);
		}
		else if(leftright ==1 && this.menuCode  ==13) {
			this.menuCode--;
			effectVolumeControl(9);
		}
		else if(leftright ==1 && this.menuCode  ==14) {
			this.menuCode--;
			effectVolumeControl(13);
		}
		else if(leftright ==1 && this.menuCode  ==15) {
			this.menuCode--;
			effectVolumeControl(17);
		}
		else if(leftright ==1 && this.menuCode  ==16) {
			this.menuCode--;
			effectVolumeControl(21);
		}
		else if(leftright ==1 && this.menuCode  ==17) {
			this.menuCode--;
			effectVolumeControl(25);
		}
		else if(leftright ==1 && this.menuCode ==18) {
			this.menuCode--;
			effectVolumeControl(29);
		}
		else if(leftright ==1 && this.menuCode  ==19) {
			this.menuCode--;
			effectVolumeControl(33);
		}
		else if(leftright ==1 && this.menuCode  ==20) {
			this.menuCode--;
			effectVolumeControl(37);
		}
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


