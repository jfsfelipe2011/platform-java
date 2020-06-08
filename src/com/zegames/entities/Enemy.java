package com.zegames.entities;

import com.zegames.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    public boolean right = true;
    public boolean left = false;
    private int framesAnimation = 0;
    private int maxFrames = 10;
    private int maxSprite = 2;
    private int curSprite = 0;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    public void tick() {
        if (World.isFree(this.getX(), (this.getY() + 1))) {
            y += 1;
        }

        if (right) {
            x += speed;

            if (!World.isFree((int) (x-speed + 1), (int) y)) {
                right = false;
                left = true;
            }

            if (World.isFree((int) (x + 16), (int) y + 1)) {
                right = false;
                left = true;
            }
        } else if (left) {
            x -= speed;

            if (!World.isFree((int) (x-speed - 1), (int) y)) {
                right = true;
                left = false;
            }

            if (World.isFree((int) (x - 16), (int) y + 1)) {
                right = true;
                left = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        framesAnimation++;

        if (framesAnimation == maxFrames) {
            curSprite++;
            framesAnimation = 0;

            if (curSprite == maxSprite) {
                curSprite = 0;
            }
        }

        sprite = Entity.ENEMY1[curSprite];

        super.render(g);
    }
}
