package screen;
import engine.Cooldown;
import engine.Core;
import engine.GameState;
import sound.SoundPlay;
import sound.SoundType;
import java.awt.event.KeyEvent;

public class PauseStateScreen extends Screen{
        /** Milliseconds between changes in user selection. */
        private static final int SELECTION_TIME = 200;

        /** Time between changes in user selection. */
        private Cooldown selectionCooldown;

        private int menuCode;

        /** Current score. */
        private int score;
        /** Player lives left. */
        private int lives;

        private int level;
        /**
         * Constructor, establishes the properties of the screen.
         *
         * @param width  Screen width.
         * @param height Screen height.
         * @param fps    Frames per second, frame rate at which the game is run.
         */
        public PauseStateScreen(final int width, final int height, final int fps, final GameScreen gameScreen) {
            super(width, height, fps);
            SoundPlay.getInstance().stopBgm();
            SoundPlay.getInstance().play(SoundType.mainGameBgm);
            this.score = gameScreen.getScore();
            this.lives = gameScreen.getLives();
            this.level = gameScreen.getLevel();
            // Default is Continue Game.
            this.menuCode = 1;
            this.returnCode = 2;
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
                    previousMenuItem();
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                        || inputManager.isKeyDown(KeyEvent.VK_S)) {
                    nextMenuItem();
                    this.selectionCooldown.reset();
                }

                if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                    if (menuCode == 3) System.exit(0);
                    if (menuCode == 2) {
                        SoundPlay.getInstance().stopBgm();
                        this.isRunning = false;
                    }
                    if (menuCode == 1) this.isRunning = false;
                }
            }
        }

        /**
         * Shifts the focus to the next menu item.
         */
        private void nextMenuItem() {
//            if (this.menuCode == 1) {
//                menuCode++;
//                returnCode = 5;
//            }
//            else if (this.menuCode == 2) {
//                menuCode++;
//                returnCode = 1;
//            }
//            else if(this. menuCode ==3) {
//                menuCode++;
//            }
//            else if(this. menuCode ==4) {
//                menuCode = 1;
//                returnCode = 2;
//            }
            if (menuCode == 3) {
                menuCode = 1;
                returnCode = 2;
            }
            else if (menuCode == 1) {
                menuCode = 2;
                returnCode = 1;
            }
            else {
                menuCode = 3;
            }
        }

        private void previousMenuItem() {
//            if (this.menuCode == 1) {
//                menuCode = 4;
//
//            }
//            else if (this.menuCode == 2) {
//                menuCode--;
//                returnCode = 2;
//            }
//            else if(this.menuCode ==3) {
//                menuCode--;
//                returnCode =5;
//            }
//            else if(this.menuCode ==4) {
//                menuCode--;
//                returnCode = 1;
//            } 여기도 해주
//
//          세요
            if (menuCode == 3) {
                menuCode = 2;
                returnCode = 1;
            }
            else if (menuCode == 2) {
                menuCode = 1;
                returnCode = 2;
            }
            else {
                menuCode = 3;
            }
        }
        /**
         * Draws the elements associated with the screen.
         */
        private void draw() {
            drawManager.initDrawing(this);

            drawManager.drawHorizontalLine(this, 39);

            drawManager.drawCenteredBigString(this, "Pause state", this.getHeight() / 3);

            drawManager.drawPauseStateScreen(this, this.menuCode);

            drawManager.drawLevels(this, this.level);

            drawManager.drawScore(this, this.score);

            drawManager.drawLives(this, this.lives);

            drawManager.completeDrawing(this);
        }




    }

