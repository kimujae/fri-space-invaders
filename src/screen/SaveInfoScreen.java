package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import engine.Cooldown;
import engine.Core;
import engine.LoadGameState;
import engine.PermanentState;
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
    //3 exit
    private int start_or_delete = 0;

    //    private int starts[] = {1, 4, 7};
    Set<Integer> starts = new HashSet<>(Arrays.asList(1, 4, 7));
    //    private int deletion[] = {2, 5, 8};
    Set<Integer> deletion = new HashSet<>(Arrays.asList(2, 5, 8));

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
    public SaveInfoScreen (LoadGameState loadGameState, final int width, final int height, final int fps, final String from) {
        super(width, height, fps);

        this.loadGameState = loadGameState;
        if (Objects.equals(from, "Load") || Objects.equals(from, "init"))
            this.returnCode = 1;
        else
            this.returnCode = 11;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }

    // 1. 코인 저장이 안됨(시작 전에 coins 파일에 1000 2000 3000 저장하고 껐다 키면
    // coins파일 내용 다 사라짐)
    // 2. 메인메뉴에서 플레이 버튼 누르면 세이브화면으로 가기
    // 3. 세이브 화면에서 start누르면 데이터 읽어와서 로드하기
    // 4. 딜리트 누르면 세이브 파일 지워지고 인터페이스 newgame으로 바꾸기
    // 5. 모든 스테이지 클리어하면 세이브 파일 지우기

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() throws IOException {
        super.run();

        return this.returnCode;
    }

    String save [] = getFileManager().loadInfo();
    String info1 = "Stage: " + save[0] + " Score: " + save[1];
    String info2 = "Stage: " + save[5] + " Score: " + save[6];
    String info3 = "Stage: " + save[10] + " Score: " + save[11];

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
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT) // start or delete 선택하기
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                if (menuCode != 3) {
                    nextCol(menuCode);
                }
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT) // 저장 slot으로 되돌아가기
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                if (menuCode != 3) {
                    prevCol(menuCode);
                }
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)){
                if (menuCode == 3)
                    this.isRunning = false;
                else {
                    loadGameState.setSaveSlot(menuCode);
                    this.isRunning = false;
                   /* //menucode가 0(slot1), 1(slot2), 2(slot3)

                    // 번호가 1, 4, 7이면 해당 slot의 데이터를 불러와서 게임 시작
                    // 1: slot1, 4: slot2, 7: slot3
                    if (starts.contains(start_or_delete)) {

                    }
                    // 번호가 2, 5, 8이면 해당 slot의 데이터를 삭제(0, 0, 0, 0, 0)
                    // 2: slot1, 5: slot2, 8: slot3
                    else {
                        // 슬롯에 세이브 파일이 존재
                        if (true){

                        }
                        // 슬롯에 세이브 파일이 존재하지 않음
                        else {

                        }*/


                }
//                else if (start_or_delete == 1) {
//                    // 해당 슬롯을 삭제하고
//                    // 세이브 파일을 0 0 0 0 0 으로 표기
//                }
//                else {
//                    // 해당 슬롯의 데이터를 불러와서
//                    // 게임 시작
//                }

                soundPlay.play(SoundType.menuClick);
                this.selectionCooldown.reset();
            }
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextRow() {
        if (menuCode == 3) {
            menuCode = 0;
            this.returnCode = 1;
        }
        else {
            menuCode++;
            this.returnCode = 5;
        }
        start_or_delete = 3 * menuCode;
        soundPlay.play(SoundType.menuSelect);
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void prevRow() {
        if (menuCode == 0)
            menuCode = 3;
        else
            menuCode--;
        start_or_delete = 3 * menuCode;
        soundPlay.play(SoundType.menuSelect);
    }

    private void nextCol(int row) {
        start_or_delete++;
        if (start_or_delete == 3)
            start_or_delete = 1;
        else if (start_or_delete == 6)
            start_or_delete = 4;
        else if (start_or_delete == 9)
            start_or_delete = 7;


        soundPlay.play(SoundType.menuSelect);
    }

    private void prevCol(int row) {
        if (0 < start_or_delete && start_or_delete <= 2
                || 3 < start_or_delete && start_or_delete <= 5
                || 6 < start_or_delete && start_or_delete <= 8)
            start_or_delete--;

        if (start_or_delete == 0)
            start_or_delete = 2;
        else if (start_or_delete == 3)
            start_or_delete = 5;
        else if (start_or_delete == 6)
            start_or_delete = 8;

        soundPlay.play(SoundType.menuSelect);
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSaveInfo(this, this.info1, this.info2, this.info3);

        drawManager.drawSaveSlots(this, this.menuCode);

        drawManager.drawSaveStartDelete(this, this.start_or_delete);

        drawManager.completeDrawing(this);
    }

    public int getSlotNum() {
        return menuCode;
    }
}
