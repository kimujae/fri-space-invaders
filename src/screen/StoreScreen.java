package screen;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

import engine.*;
import sound.SoundPlay;
import sound.SoundType;

public class StoreScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    private static final int COST_SHAPE = 100;
    private static final int COST_COLOR = 100;
    private static final int COST_BULLET = 100;
    private static final int COST_BGM = 100;

    private static final int COST_ITEMBOX = 100;

    private static final int COST_POTION = 100;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private PermanentState permanentState = PermanentState.getInstance();

    private CashItemManager cashItemManager = CashItemManager.getInstance();

    private SoundPlay soundPlay = SoundPlay.getInstance();

    private int menuCode = 0;
    private int focusReroll = 0;
    private int cashitemCode =1;

    /* item Code는 아래와 같음
    BulletSpeedItem = 1;
    PointUpItem = 2;
    ShieldItem = 3;
    SpeedUpItem = 4;
    ExtraLifeItem = 5;
    MachineGunItem = 6;
    speedUpPotion = 7;
    invincibilityPotion = 8 ;
    */
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
    public StoreScreen (final int width, final int height, final int fps) {
        super(width, height, fps);

        this.returnCode = 1;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }
    // /*
    // 0 ship shape
    // 1 ship color
    // 2 bullet effect
    // 3 bgm
    // 4 item box
    // 5 potion
    // 6 exit
    // */
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
                if((menuCode == 4 || menuCode == 5) && focusReroll == 1){
                    prevCashItem();
                }
                if (focusReroll == 0)
                    previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                if((menuCode == 4 || menuCode == 5) && focusReroll == 1){
                    nextCashItem();
                }
                if (focusReroll == 0)
                    nextMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT) // 뽑기 버튼으로 가기
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                if (menuCode != 6) focusReroll = 1;
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT) // 메뉴 선택으로 되돌아가기
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                if (menuCode != 6) focusReroll = 0;
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)){
                if (menuCode == 6) //EXIT
                    this.isRunning = false;
                else {
                    if (focusReroll == 0)
                        focusReroll = 1;
                    else if(menuCode < 4) { // reroll
                        rerollItem();
                    }
                    else if (menuCode == 4) {
                        cashItemManager.buyItem(cashitemCode);
                        permanentState.setCoin(-COST_ITEMBOX);
                    }
                    else
                        cashItemManager.buyPotion(cashitemCode);
                        permanentState.setCoin(-COST_POTION);

                }
                soundPlay.play(SoundType.menuClick);
                this.selectionCooldown.reset();
            }
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextMenuItem() {
        if (menuCode == 6)
            menuCode = 0;
        else
            menuCode++;
        soundPlay.play(SoundType.menuSelect);
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (menuCode == 0)
            menuCode = 6;
        else
            menuCode--;
        soundPlay.play(SoundType.menuSelect);
    }

    private void nextCashItem(){
        if(menuCode == 4)
            if(cashitemCode == 6)
                cashitemCode = 1;
            else
                cashitemCode ++;
        else
            if(cashitemCode == 2)
                cashitemCode = 1;
            else
                cashitemCode++;
    }

    private void prevCashItem(){
        if(menuCode == 4)
            if(cashitemCode == 1)
                cashitemCode = 6;
            else
                cashitemCode --;
        else
            if(cashitemCode == 1)
                cashitemCode = 2;
            else
                cashitemCode--;
    }

    private void rerollItem() {
        if (menuCode == 0){ // ship shape
            if (permanentState.getCoin() >= COST_SHAPE) {
                int x = new Random().nextInt(3);
                while (permanentState.getShipShape() == x)
                    x = new Random().nextInt(3);

                permanentState.setShipShape(x);
                permanentState.setCoin(-COST_SHAPE);
            }
        }
        else if (menuCode == 1){ // ship color
            if (permanentState.getCoin() >= COST_COLOR) {
                int x = new Random().nextInt(3);
                while (permanentState.getShipColor() == x)
                    x = new Random().nextInt(3);

                permanentState.setShipColor(x);
                permanentState.setCoin(-COST_COLOR);
            }
        }
        else if (menuCode == 2){ // bullet effect
            if (permanentState.getCoin() >= COST_BULLET) {
                int x = new Random().nextInt(3) + 1;
                while (permanentState.getBulletSFX() == x)
                    x = new Random().nextInt(3) + 1;

                permanentState.setBulletSFX(x);
                permanentState.setCoin(-COST_BULLET);
            }
        }
        else if (menuCode == 3) { // BGM
            if (permanentState.getCoin() >= COST_BGM) {
                int x = new Random().nextInt(3) + 1;
                while (permanentState.getBGM() == x)
                    x = new Random().nextInt(3) + 1;
                permanentState.setBGM(x);
                permanentState.setCoin(-COST_BGM);
            }
        }
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawStoreTitle(this);
        drawManager.drawStoreMenu(this, menuCode, focusReroll);
        if (menuCode < 6)
            drawManager.drawStoreGacha(this, menuCode, focusReroll);
        drawManager.drawCoin(this, permanentState.getCoin());
        if(menuCode == 4 )
            drawManager.drawItemBox(this, cashitemCode, focusReroll);
        drawManager.completeDrawing(this);
        if(menuCode == 5) //Potion
            drawManager.drawPotion(this, cashitemCode, focusReroll);
        drawManager.completeDrawing(this);
    }
}
