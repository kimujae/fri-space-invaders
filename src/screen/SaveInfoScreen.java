package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import engine.*;
import sound.SoundPlay;
import sound.SoundType;

import java.util.*;

import static engine.Core.getFileManager;

public class SaveInfoScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    private PermanentState permanentState = PermanentState.getInstance();

    private SoundPlay soundPlay = SoundPlay.getInstance();

    private LoadGameState loadGameState;


    private int menuCode = 0;
    //0 slot1
    //1 slot2
    //2 slot3
    private int start_or_delete = 1;

    //private int coins = getFileManager().loadCoins(0);
    private String save [] = getFileManager().loadInfo();
    public String info1 = "Stage: " + save[0] + " Lives: " + save[2]+ " " +  save[1];
    public String info2 = "Stage: " + save[5] + " Lives: " + save[7]+ " " +  save[6];
    public String info3 = "Stage: " + save[10] + " Lives: " + save[12]+ " " +  save[11];

    private String save_or_init;

    private GameScreen gameScreen;

    private boolean isPauseStateScreen;

    private GameState gameState;

    private int level;

    private String[] ogdata;

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
    public SaveInfoScreen (LoadGameState loadGameState,
                           final int width, final int height, final int fps) {
        super(width, height, fps);

//        this.isPauseStateScreen = isPauseStateScreen;
        this.loadGameState = loadGameState;
//        this.gameScreen = gameScreen;
        this.gameState = gameState;

        this.returnCode = -2;

//        save_or_init = from;
//        if (Objects.equals(from, "Load") || Objects.equals(from, "init") )
//            this.returnCode = 1;
//        else
//            this.returnCode = 11;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }

    // 1. 코인 저장이 안됨(시작 전에 coins 파일에 1000 2000 3000 저장하고 껐다 키면
    // coins파일 내용 다 사라짐)

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
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                nextCol();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                prevCol();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                loadGameState.setSaveSlot(menuCode);
                // start 버튼
                if (start_or_delete == 1) {

                    this.returnCode = -2;

                    loadGameState.setSaveSlot(menuCode);
                    loadGameState.setGameState(loadGameState.getGameState());
                    this.isRunning = false;
                }
                // delete 버튼
                else if (start_or_delete == 2) {
                    getFileManager().Savefile(new GameState(
                            0,
                            0,
                            3,
                            0,
                            0),
                            menuCode, loadGameState.getData());
                    loadGameState.initData(getFileManager().loadInfo());
                    save = getFileManager().loadInfo();
                    infoUpdate();
                }
            }
        }
    }
    /**
     * Shifts the focus to the next menu item.
     */
    private void nextRow() {
        if (menuCode == 2) {
            menuCode = 0;
        }
        else {
            menuCode++;
        }
        start_or_delete = 1;
        soundPlay.play(SoundType.menuSelect);
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void prevRow() {
        if (menuCode == 0)
            menuCode = 2;
        else
            menuCode--;
        start_or_delete = 1;
        soundPlay.play(SoundType.menuSelect);
    }

    private void nextCol() {
       if (start_or_delete == 2)
           start_or_delete = 1;
       else
           start_or_delete++;
        soundPlay.play(SoundType.menuSelect);
    }

    private void prevCol() {
        if (start_or_delete == 1)
            start_or_delete = 2;
        else
            start_or_delete--;
        soundPlay.play(SoundType.menuSelect);
    }

    private void infoUpdate() {
        info1 = "Stage: " + save[0] + " Lives: " + save[2] + " " +  save[1];
        info2 = "Stage: " + save[5] + " Lives: " + save[7] + " " + save[6];
        info3 = "Stage: " + save[10] + " Lives: " + save[12] + " " + save[11];
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSaveInfo(this, info1, info2, info3);

        drawManager.drawSaveSlots(this, menuCode);

        drawManager.drawSaveStartDelete(this, menuCode, start_or_delete);

        drawManager.completeDrawing(this);
    }

}
