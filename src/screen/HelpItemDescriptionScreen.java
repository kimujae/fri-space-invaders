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
public class HelpItemDescriptionScreen extends Screen {

    /** Time between changes in user selection. */
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
    public HelpItemDescriptionScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

        // Back to Help Menu
        this.returnCode = 7;
        this.selectionCooldown = Core.getCooldown(100);
        this.selectionCooldown.reset();
        this.insNum = 1;
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
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                this.isRunning = false;
                soundPlay.play(SoundType.menuClick);
            }
        }
    }
    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawHelp(this, this.insNum);
        drawManager.drawHelpItems(this, returnCode);

        drawManager.completeDrawing(this);
    }
}

