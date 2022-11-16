package engine;

import static engine.Core.getFileManager;

public class LoadGameState{

    GameState gameState;
    int saveSlotNum;


    public LoadGameState(){
        this.gameState = null;
        this.saveSlotNum = 0;
    }


    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }

    public int getSaveSlot(){
        return saveSlotNum;
    }

    public void setSaveSlot(int saveSlotNum){
        this.saveSlotNum = saveSlotNum;

        String save_info []  = getFileManager().loadInfo();
        gameState = new GameState(Integer.parseInt(save_info[saveSlotNum * 5]),
                Integer.parseInt(save_info[saveSlotNum * 5 + 1]),
                Integer.parseInt(save_info[saveSlotNum * 5 + 2]),
                Integer.parseInt(save_info[saveSlotNum * 5 + 3]),
                Integer.parseInt(save_info[saveSlotNum * 5 + 4]));

    }


    public GameState getGameState() {
        return gameState;
    }

}