package screen;

import engine.*;
import sound.SoundPlay;
import sound.SoundType;

import java.awt.event.KeyEvent;


public class SelectItemScreen extends Screen{

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 100;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    private PermanentState permanentState = PermanentState.getInstance();

    private CashItemManager cashItemManager = CashItemManager.getInstance();
    private SoundPlay soundPlay = SoundPlay.getInstance();

    private int menuCode = 0;

    private GameScreen gameScreen;

    private GameState gameState;

    private LoadGameState loadGameState;


    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public SelectItemScreen(int width, int height, int fps) {
        super(width, height, fps);
        this.returnCode = 1;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run()  {
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
                prevRow();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextRow();
                this.selectionCooldown.reset();
            }

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                if (menuCode != 0) {
                    boolean check;
                    do {
                        if(menuCode < 7)
                            check = cashItemManager.useItem(menuCode);
                        else
                            check = cashItemManager.usePotionItem(menuCode);
                    } while (!check);
                }
                this.isRunning = false;
            }
        }
    }
    /**
     * Shifts the focus to the next menu item.
     */
    private void nextRow() {
        if (menuCode == 7) {
            menuCode = 0;
        }
        else {
            menuCode++;
        }
        soundPlay.play(SoundType.menuSelect);
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void prevRow() {
        if (menuCode == 0)
            menuCode = 7;
        else
            menuCode--;
        soundPlay.play(SoundType.menuSelect);
    }

//    private void nextCol() {
//        if (start_or_delete == 2)
//            start_or_delete = 1;
//        else
//            start_or_delete++;
//        soundPlay.play(SoundType.menuSelect);
//    }

//    private void prevCol() {
//        if (start_or_delete == 1)
//            start_or_delete = 2;
//        else
//            start_or_delete--;
//        soundPlay.play(SoundType.menuSelect);
//    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSelectItemScreen(this, menuCode);

        drawManager.completeDrawing(this);
    }

}
