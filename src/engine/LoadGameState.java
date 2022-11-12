package engine;

public class LoadGameState {

    GameState gameState;
    int saveSlotNum;

    public LoadGameState(){
        this.gameState = null;
        this. saveSlotNum = -1;
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


}
