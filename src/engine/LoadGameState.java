package engine;

import static engine.Core.getFileManager;

public class LoadGameState {

    private PermanentState permanentState = PermanentState.getInstance();
    GameState gameState;
    int saveSlotNum;

    int savedSlot;

    public LoadGameState(){
        this.gameState = null;
        //this.saveSlotNum = -1;
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
    }


    public GameState getGameState() {
        int slotNum = saveSlotNum;
        String save_info []  = getFileManager().loadInfo();


        permanentState.setCoin(0, slotNum);

        GameState gameState = new GameState(Integer.parseInt(save_info[slotNum * 5]),
                Integer.parseInt(save_info[slotNum * 5 + 1]),
                Integer.parseInt(save_info[slotNum * 5 + 2]),
                Integer.parseInt(save_info[slotNum * 5 + 3]),
                Integer.parseInt(save_info[slotNum * 5 + 4]));

        return gameState;
    }

}
