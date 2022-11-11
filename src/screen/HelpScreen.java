package screen;

import java.awt.event.KeyEvent;


import engine.Cooldown;
import engine.Core;
import sound.SoundPlay;
import sound.SoundType;

/**
 * Implements the high scores screen, it shows player records.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class HelpScreen extends Screen {

	private Cooldown selectionCooldown;
	private SoundPlay soundPlay = SoundPlay.getInstance();
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
	public HelpScreen(final int width, final int height, final int fps) {
		super(width, height, fps);

		// Defualts는 opeartion 메뉴부터
		this.returnCode = 9;
		this.selectionCooldown = Core.getCooldown(200);
		this.selectionCooldown.reset();
		this.insNum = 0;

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
				previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
					|| inputManager.isKeyDown(KeyEvent.VK_S)) {
				nextMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
				this.isRunning = false;
				soundPlay.play(SoundType.menuClick);
			}
		}

	}
	/**
	 * Shifts the focus to the next menu item.
	 */
	private void nextMenuItem() {
		if (this.returnCode == 9)
			this.returnCode = 10;
		else if (this.returnCode == 10)
			this.returnCode = 1;
		else if (this.returnCode == 1)
			this.returnCode = 9;

		soundPlay.play(SoundType.menuSelect);
	}

	/**
	 * Shifts the focus to the previous menu item.
	 */
	private void previousMenuItem() {
		if (this.returnCode == 9)
			this.returnCode = 1;
		else if (this.returnCode == 10)
			this.returnCode = 9;
		else if (this.returnCode == 1)
			this.returnCode = 10;

		soundPlay.play(SoundType.menuSelect);
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawHelp(this, this.insNum);
		drawManager.drawHelpMenu(this, this.returnCode);

		drawManager.completeDrawing(this);
	}
}