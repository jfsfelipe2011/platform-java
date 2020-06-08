package com.zegames.entities;

import com.zegames.main.Game;
import com.zegames.main.Sound;
import com.zegames.world.Camera;
import com.zegames.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    public boolean right, left;
    private double gravity = 2;
    public int dir = 1;
    public boolean jump = false;
    public boolean isJumping = false;
    public int jumpHeight = 32;
    public int jumpFrames = 0;
    private int framesAnimation = 0;
    private int maxFrames = 3;
    private int maxSprite = 4;
    private int curSprite = 0;
    private boolean moved = false;
    public static int currentCoins = 0;
    public static int maxCoins = 0;

    public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
        super(x, y, width, height,speed,sprite);
    }

    public void setIdle() {
        this.curSprite = 0;
    }

    public void tick() {
        depth = 2;
        moved = false;

        if (World.isFree(this.getX(), (int) (this.getY() + gravity)) && !isJumping) {
            y += gravity;

            if (y + gravity > Game.HEIGHT - 16) {
                World.restartGame();
                Sound.dieEffect.play();
            }

            for (int i = 0; i < Game.entities.size(); i++) {
                Entity entity = Game.entities.get(i);

                if (entity instanceof Enemy) {
                    if (Entity.isColidding(this, entity)) {
                        Sound.hurtEffect.play();
                        isJumping = true;
                        Game.entities.remove(entity);
                    }
                }
            }
        }

        if (right && World.isFree((int) (x + speed), this.getY())) {
            x += speed;
            dir = 1;
            moved = true;

        } else if (left && World.isFree((int) (x - speed), this.getY())) {
            if ((x - speed) < 0) {
                return;
            }

            x -= speed;
            dir = -1;
            moved = true;
        }

        if (jump) {
            if (!World.isFree(this.getX(), this.getY() + 1)) {
                Sound.jumpEffect.play();
                isJumping = true;
            } else {
                jump = false;
            }
        }

        if (isJumping) {
            if (y - 2 <= 0) {
                isJumping = false;
                jump = false;
                jumpFrames = 0;
            } else {
                if (World.isFree(this.getX(), (int) (this.getY() - 2))) {
                    y -= 2;
                    jumpFrames += 2;

                    if (jumpFrames == jumpHeight) {
                        isJumping = false;
                        jump = false;
                        jumpFrames = 0;
                    }
                } else {
                    isJumping = false;
                    jump = false;
                    jumpFrames = 0;
                }
            }
        }

        for (int i = 0; i < Game.entities.size(); i++) {
            Entity entity = Game.entities.get(i);

            if (entity instanceof Enemy) {
                if (Entity.isColidding(this, entity)) {
                    Sound.dieEffect.play();
                    World.restartGame();
                }
            } else if (entity instanceof Coin) {
                if (Entity.isColidding(this, entity)) {
                    Game.entities.remove(entity);
                    Player.currentCoins++;
                    Sound.coinEffect.play();
                }
            }
        }

        Camera.x = Camera.clamp(this.getX() - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
    }

    @Override
    public void render(Graphics g) {
        if (moved) {
            framesAnimation++;

            if (framesAnimation == maxFrames) {
                curSprite++;
                framesAnimation = 0;

                if (curSprite == maxSprite) {
                    curSprite = 0;
                }
            }
        }

        if (isJumping) {
            if (dir == 1) {
                sprite = Entity.PLAYER_SPRITE_JUMP_RIGHT;
            } else if (dir == -1) {
                sprite = Entity.PLAYER_SPRITE_JUMP_LEFT;
            }
        } else {
            if (dir == 1) {
                sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
            } else if (dir == -1) {
                sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
            }
        }

        super.render(g);
    }
}
