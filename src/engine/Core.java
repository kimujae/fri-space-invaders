package engine;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.ItemPool;
import screen.*;
import sound.SoundPlay;
import sound.SoundType;

/**
 * Implements core game logic.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public final class Core {

	/** Width of current screen. */
	private static final int WIDTH = 448;
	/** Height of current screen. */
	private static final int HEIGHT = 520;
	/** Max fps of current screen. */
	private static final int FPS = 60;

	/** Max lives. */
	private static final int MAX_LIVES = 3;
	/** Levels between extra life. */
	private static final int EXTRA_LIFE_FRECUENCY = 3;
	/** Total number of levels. */
	private static final int NUM_LEVELS = 8;
	/** Check click on Save & Exit button */
	private static boolean GO_MAIN;
	/** Difficulty settings for level 1. */
	private static final GameSettings SETTINGS_LEVEL_1 =

			new GameSettings(1, 5, 4, 60, 2000);
	/** Difficulty settings for level 2. */
	private static final GameSettings SETTINGS_LEVEL_2 =
			new GameSettings(2, 5, 5, 50, 2500);
	/** Difficulty settings for level 3. */
	private static final GameSettings SETTINGS_LEVEL_3 =
			new GameSettings(3, 6, 5, 40, 1500);
	/** Difficulty settings for level 4. */
	private static final GameSettings SETTINGS_LEVEL_4 =
			new GameSettings(4, 6, 6, 30, 1500);
	/** Difficulty settings for level 5. */
	private static final GameSettings SETTINGS_LEVEL_5 =
			new GameSettings(5, 7, 6, 20, 1000);
	/** Difficulty settings for level 6. */
	private static final GameSettings SETTINGS_LEVEL_6 =
			new GameSettings(6, 7, 7, 10, 1000);
	/** Difficulty settings for level 7. */
	private static final GameSettings SETTINGS_LEVEL_7 =
			new GameSettings(7,7, 8, 5, 500);

	/** add boss stage **/
	private static final GameSettings SETTINGS_Boss_Stage=
			new GameSettings(8, 3,3, 0, 200);

	/** Frame to draw the screen on. */
	private static Frame frame;
	/** Screen currently shown. */
	private static Screen currentScreen;
	/** Difficulty settings list. */
	private static List<GameSettings> gameSettings;
	/** Application logger. */
	private static final Logger LOGGER = Logger.getLogger(Core.class
			.getSimpleName());
	/** Logger handler for printing to disk. */
	private static Handler fileHandler;
	/** Logger handler for printing to console. */
	private static ConsoleHandler consoleHandler;

	private static GameScreen gameScreen;


	/**
	 * Test implementation.
	 *
	 * @param args
	 *            Program args, ignored.
	 */
	public static void main(final String[] args) {
		try {
			LOGGER.setUseParentHandlers(false);

			fileHandler = new FileHandler("log");
			fileHandler.setFormatter(new MinimalFormatter());

			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new MinimalFormatter());

			LOGGER.addHandler(fileHandler);
			LOGGER.addHandler(consoleHandler);
			LOGGER.setLevel(Level.ALL);

		} catch (Exception e) {
			// TODO handle exception
			e.printStackTrace();
		}

		frame = new Frame(WIDTH, HEIGHT);
		DrawManager.getInstance().setFrame(frame);
		int width = frame.getWidth();
		int height = frame.getHeight();

		gameSettings = new ArrayList<GameSettings>();
		gameSettings.add(SETTINGS_LEVEL_1);
		gameSettings.add(SETTINGS_LEVEL_2);
		gameSettings.add(SETTINGS_LEVEL_3);
		gameSettings.add(SETTINGS_LEVEL_4);
		gameSettings.add(SETTINGS_LEVEL_5);
		gameSettings.add(SETTINGS_LEVEL_6);
		gameSettings.add(SETTINGS_LEVEL_7);
		gameSettings.add(SETTINGS_Boss_Stage);

		GameState gameState = new GameState(0,0,0,0,0);
		PermanentState permanentState = PermanentState.getInstance();
		LoadGameState loadGameState = new LoadGameState();

		String[] OriginalData = getFileManager().loadInfo();
		loadGameState.initData(OriginalData);


		int returnCode = -1;
		do {

			switch (returnCode) {
				case -1:
					currentScreen = new SaveInfoScreen(loadGameState, width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " save info screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing save info screen.");
					gameState = loadGameState.getGameState();
					permanentState.setSlot(loadGameState.getSaveSlot());
					break;

				case 1:
					// Main menu.
//                    if(gameScreen != null && gameScreen.getInterrupt()){
//                        gameScreen = null;
//						gameState = loadGameState.getGameState();
//						System.out.println("if");
//                    }
					currentScreen = new TitleScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " title screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing title screen.");
					break;
				case 2:
					// Game & score.
					GO_MAIN = true;
					do {
						if(gameScreen != null && gameScreen.getInterrupt()){
							currentScreen = gameScreen;
							frame.setScreen(currentScreen);
						}
						else {
							// One extra live every few levels.
							boolean bonusLife = gameState.getLevel()
									% EXTRA_LIFE_FRECUENCY == 0
									&& gameState.getLivesRemaining() < MAX_LIVES;
							gameScreen = new GameScreen(gameState,
									gameSettings.get(gameState.getLevel()),
									bonusLife, width, height, FPS);
							currentScreen = gameScreen;
							LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
									+ " game screen at " + FPS + " fps.");
						}

						if (currentScreen.getIsInit() && frame.setScreen(currentScreen) == 11) {
							returnCode = 11;
							break;
						} else {

							LOGGER.info("Closing game screen.");
							gameState = ((GameScreen) currentScreen).getGameState();

							if (gameState.getScore() > 500)
								permanentState.setCoin(gameState.getScore() - 500); // earn coin


							if (gameState.getLivesRemaining() > 0) {
								currentScreen = new PauseScreen(width, height, FPS, gameState);
								returnCode = frame.setScreen(currentScreen);
							}

							if (gameState.getLivesRemaining() > 0 && gameState.getLevel() < NUM_LEVELS) {
								LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
										+ " game save screen at " + FPS + " fps.");
								currentScreen = new GameSaveScreen(gameState, width, height, FPS);
								returnCode = frame.setScreen(currentScreen);
								LOGGER.info("Closing game save screen.");
								if (returnCode == 2) {

									gameState = new GameState(
											gameState.getLevel() + 1,
											gameState.getScore(),
											gameState.getLivesRemaining(),
											gameState.getBulletsShot(),
											gameState.getShipsDestroyed()
									);
//									loadGameState.setGameState(new GameState(
//											gameState.getLevel() + 2,
//											gameState.getScore(),
//											gameState.getLivesRemaining(),
//											gameState.getBulletsShot(),
//											gameState.getShipsDestroyed()
//									));
									loadGameState.setGameState(gameState);
									System.out.println(gameState.getLevel());
									getFileManager().Savefile(gameState, loadGameState.getSaveSlot(), loadGameState.getData());

									LOGGER.info("Complete Save.");
									GO_MAIN = false;
//									gameState = new GameState(1, 0, MAX_LIVES, 0, 0);
//									System.out.println(gameState.getLevel());
//									System.out.println(gameState.getScore());
//									System.out.println(gameState.getLivesRemaining());
//									System.out.println(gameState.getBulletsShot());
//									System.out.println(gameState.getShipsDestroyed());
									returnCode = 1;
									break;
								}
							}
							gameState = new GameState(
									gameState.getLevel() + 1,
									gameState.getScore(),
									gameState.getLivesRemaining(),
									gameState.getBulletsShot(),
									gameState.getShipsDestroyed()
							);
						}
					} while (gameState.getLivesRemaining() > 0
							&& gameState.getLevel() <= NUM_LEVELS);
					if ((gameScreen != null && gameScreen.getInterrupt()) || !GO_MAIN)
						break;
//					getFileManager().Savefile(new GameState(0, 0, 3, 0, 0), loadGameState.getSaveSlot());
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " score screen at " + FPS + " fps, with a score of "
							+ gameState.getScore() + ", "
							+ gameState.getLivesRemaining() + " lives remaining, "
							+ gameState.getBulletsShot() + " bullets shot and "
							+ gameState.getShipsDestroyed() + " ships destroyed.");
					currentScreen = new ScoreScreen(width, height, FPS, gameState);
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing score screen.");
					// 다 깼을 때
					if (gameState.getLivesRemaining() == 0 || gameState.getLevel() == NUM_LEVELS + 1){
						loadGameState.setGameState(new GameState(
								7,
								gameState.getScore(),
								gameState.getLivesRemaining(),
								gameState.getBulletsShot(),
								gameState.getShipsDestroyed())
						);
						gameState = loadGameState.getGameState();
					}

					break;
				case 3:
					// High scores.
					currentScreen = new HighScoreScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " high score screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing high score screen.");
					break;
				default:
					break;

				case 4:
					// Store
        
				currentScreen = new StoreScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " store screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing store screen.");
				break;

			case 5:
				break;
				// Load
				/*
				currentScreen = new SaveInfoScreen(loadGameState, width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " SaveInfo screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing SaveInfo screen.");
				gameState = loadGameState.getGameState();
				break;
				*/

				/*
				String save_info [] = getFileManager().loadInfo();
				gameState = new GameState(Integer.parseInt(save_info[0]), Integer.parseInt(save_info[1]), Integer.parseInt(save_info[2]), Integer.parseInt(save_info[3]), Integer.parseInt(save_info[4]));
				returnCode = 2;
				break;
				*/
				
			case 6:
				// Setting.
				currentScreen = new SettingScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " setting screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing setting screen.");
				break;
				
			case 7: //Help
				currentScreen = new HelpScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " setting screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing help screen.");
				break;
				
			case 8: //Volume //mainmenu 1014
				currentScreen = new VolumeScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " setting screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing help screen.");
				break;

				case 9:
					// Help(operation)
					currentScreen = new HelpOperationScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " operation screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing operation screen.");
					break;

				case 10:
					// Help(item description)
					currentScreen = new HelpItemDescriptionScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " item description screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing description screen.");
					break;



				case 11: //pauseStateScreen
				currentScreen = new PauseStateScreen(width, height, FPS, gameScreen);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " pause screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				if(returnCode ==2) {
					SoundPlay.getInstance().stopBgm();
					SoundPlay.getInstance().play(SoundType.inGameBGM);
				}
				else if(returnCode == 5){
					gameState = new GameState(
							gameScreen.getLevel(),
							gameScreen.getScore(),
							gameScreen.getLivesRemaining(),
							gameScreen.getBulletsShot(),
							gameScreen.getShipsDestroyed()
					);
				loadGameState.setGameState(gameState);
				System.out.println(gameState.getLevel());
				getFileManager().Savefile(gameState, loadGameState.getSaveSlot(), loadGameState.getData());
				returnCode = 1;
				}
				LOGGER.info("Closing pause screen.");
				break;
			}

		} while (returnCode != 0);

		loadGameState.setGameState(new GameState(
				gameState.getLevel() - 1,
				gameState.getScore(),
				gameState.getLivesRemaining(),
				gameState.getBulletsShot(),
				gameState.getBulletsShot()
		));
		getFileManager().Savefile(loadGameState.getGameState(),
				loadGameState.getSaveSlot(),
				loadGameState.getData());

		fileHandler.flush();
		fileHandler.close();
		System.exit(0);
	}

	/**
	 * Constructor, not called.
	 */
	private Core() {

	}

	/**
	 * Controls access to the logger.
	 *
	 * @return Application logger.
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Controls access to the drawing manager.
	 *
	 * @return Application draw manager.
	 */
	public static DrawManager getDrawManager() {
		return DrawManager.getInstance();
	}

	/**
	 * Controls access to the input manager.
	 *
	 * @return Application input manager.
	 */
	public static InputManager getInputManager() {
		return InputManager.getInstance();
	}

	/**
	 * Controls access to the file manager.
	 *
	 * @return Application file manager.
	 */
	public static FileManager getFileManager() {
		return FileManager.getInstance();
	}

	/**
	 * Controls creation of new cooldowns.
	 *
	 * @param milliseconds
	 *            Duration of the cooldown.
	 * @return A new cooldown.
	 */
	public static Cooldown getCooldown(final int milliseconds) {
		return new Cooldown(milliseconds);
	}

	/**
	 * Controls creation of new cooldowns with variance.
	 *
	 * @param milliseconds
	 *            Duration of the cooldown.
	 * @param variance
	 *            Variation in the cooldown duration.
	 * @return A new cooldown with variance.
	 */
	public static Cooldown getVariableCooldown(final int milliseconds,
											   final int variance) {
		return new Cooldown(milliseconds, variance);
	}
	}