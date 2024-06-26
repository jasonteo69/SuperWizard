import java.awt.*;
import java.awt.image.BufferedImage;

public class Boss {
    private int health;
    private int damage;
    private String imageFileName;
    private BufferedImage image;
    private Rectangle hitbox;
    private int x, y;
    private int WIDTH;
    private int HEIGHT;
    private boolean canMove;
    private Projectile[] projectile;
    private long nextShot = 0;
    private long delay = 1000;


    public Boss (String boss, int width, int height) {
        health = 15;
        damage = 5;
        this.imageFileName = "images/" + boss + ".png";
        this.image = Wizard.readImage(imageFileName);
        x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * .7292);
        y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * .602);
        WIDTH = width;
        HEIGHT = height;
        hitbox = new Rectangle(x + 100, y, WIDTH, HEIGHT);
        canMove = true;
        projectile = new Projectile[2];
        generateProjectiles();
    }


    public int getDamage() {
        return damage;
    }


    public void updateCoords() {
        hitbox.setBounds(x + 50, y, WIDTH, HEIGHT);
    }
    public void drawHealthBar(Graphics g) {
        g.setFont(new Font("TT Supermolot Neue", Font.PLAIN, 45));
        g.setColor(new Color(207, 3, 252));
        g.drawString("Health: ", x, y - 55);
        g.setColor(Color.red);
        g.fillRect(x + 150, y - 100, (int)(400 * health / 25), 50);
    }
    private void generateProjectiles() {
        projectile[0] = new Projectile("dragonfire", 100, 100);
        projectile[1] = new Projectile("dragonfire2", 290, 290);
    }
    public void shoot(int projNum) {
        projectile[projNum].setCanFire(true);
        projectile[projNum].setShow(true);
        if (System.currentTimeMillis() > nextShot) {
            nextShot = System.currentTimeMillis() + delay;
        }
    }

    public boolean isCanMove() {
        return canMove;
    }


    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }


    public int getHealth() {
        return health;
    }


    public void setHealth(int health) {
        this.health = health;
    }


    public BufferedImage getImage() {
        return image;
    }


    public Rectangle getHitbox() {
        return hitbox;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    public Projectile[] getProjectile() {
        return projectile;
    }

}

