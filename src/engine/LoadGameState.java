package engine;

import static engine.Core.getFileManager;

public class LoadGameState{

    GameState gameState;
    int saveSlotNum;

    String[] data;

    public LoadGameState(){
        this.gameState = null;
        this.saveSlotNum = 0;
        this.data =
                new String[]
                        {"1", "0", "3", "0", "0",
                        "1", "0", "3", "0", "0",
                        "1", "0", "3", "0", "0",};
    }


    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }

    public int getSaveSlot(){
        return saveSlotNum;
    }

    public void setSaveSlot(int saveSlotNum){
        this.saveSlotNum = saveSlotNum;

        gameState = new GameState(
                Integer.parseInt(data[saveSlotNum * 5]),
                Integer.parseInt(data[saveSlotNum * 5 + 1]),
                Integer.parseInt(data[saveSlotNum * 5 + 2]),
                Integer.parseInt(data[saveSlotNum * 5 + 3]),
                Integer.parseInt(data[saveSlotNum * 5 + 4])
                );
    }

    public GameState getGameState() {
        return gameState;
    }

    public void initData(String[] og_data) {
        this.data = og_data;
    }

    public String[] getData() {
        return data;
    }

}