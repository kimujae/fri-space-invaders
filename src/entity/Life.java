package entity;

import engine.Core;
import engine.DrawManager;

import java.awt.*;

public class Life extends Entity{
    public Life(final int positionX, final int positionY) {
        super(positionX, positionY, 13 * 2, 8 * 2, Color.GREEN);
        this.spriteType = DrawManager.SpriteType.Life;
    }
}
