package rendering;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheetBuilder {
    private BufferedImage spriteSheet;
    private int rows, cols, spriteWidth, spriteHeight, spriteCount;

    public SpriteSheetBuilder withSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
        return this;
    }

    public SpriteSheetBuilder withRows(int row) {
        this.rows = row;
        return this;
    }

    public SpriteSheetBuilder withColumns(int col) {
        this.cols = col;
        return this;
    }

    public SpriteSheetBuilder withSpriteWidth(int spriteWidth, int spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        return this;
    }

    public SpriteSheetBuilder withSpriteCount(int spriteCount) {
        this.spriteCount = spriteCount;
        return this;
    }

    public int getSpriteCount() {
        return spriteCount;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public SpriteSheet build(int gridX, int gridY) {
        int count = getSpriteCount();
        int rows = getRows();
        int cols = getCols();
        if (count == 0) {
            count = rows * cols;
        }
        BufferedImage spriteSheet = getSpriteSheet();

        int width = getSpriteWidth();
        int height = getSpriteHeight();

        if (width == 0) {
            width = spriteSheet.getWidth() / cols;
        }
        if (height == 0) {
            height = spriteSheet.getHeight() / rows;
        }
        int x = gridX, y = gridY;
        List<BufferedImage> sprites = new ArrayList<BufferedImage>();
        for (int i = 0; i < count; i++) {
            sprites.add(spriteSheet.getSubimage(x, y, width, height));
            x += width;
            if (x >= width * cols) {
                x = 0;
                y += height;
            }
        }
        return new SpriteSheet(sprites);
    }
}
