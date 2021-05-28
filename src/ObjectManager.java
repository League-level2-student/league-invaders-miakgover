import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {
	Rocketship rocket;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	Random random = new Random();
	int score = 0;
	ObjectManager(Rocketship rocket){
		this.rocket = rocket;
	}
	public void addProjectiles(Projectile p) {
		projectiles.add(p);
	}
	public void addAliens() {
		aliens.add(new Alien(random.nextInt(LeagueInvaders.WIDTH),0,50,50));
	}
	public int getScore() {
		return score;
		
	}
	public void checkCollision() {
		Alien current_alien;
		Projectile current_projectile;
		for (int i=0; i<aliens.size(); i++) {
			current_alien = aliens.get(i);
			for (int j=0; j<projectiles.size(); j++) {
				current_projectile = projectiles.get(j);
				if (current_projectile.collisionBox.intersects(current_alien.collisionBox)) {
					current_alien.isActive = false;
					current_projectile.isActive = false;
					score++;
				}
			}
			if (rocket.collisionBox.intersects(current_alien.collisionBox)) {
				rocket.isActive = false;
				current_alien.isActive = false;
			}
		}
	}
	public void update() {
		for (int i=0; i<aliens.size();i++) {
			aliens.get(i).update();
			if(aliens.get(i).y < 0 || aliens.get(i).y > LeagueInvaders.HEIGHT) {
				aliens.get(i).isActive = false;
			}
		}
		for (int i=0; i<projectiles.size();i++) {
			projectiles.get(i).update();
			if(projectiles.get(i).y < 0 || projectiles.get(i).y > LeagueInvaders.HEIGHT) {
				projectiles.get(i).isActive = false;
			}
		}
		checkCollision();
		purgeObjects();
	}
	public void draw(Graphics g) {
		rocket.draw(g);
		for(int i=0; i<aliens.size(); i++) {
			aliens.get(i).draw(g);
		}
		for(int i=0; i<projectiles.size(); i++) {
			projectiles.get(i).draw(g);
		}
	}
	public void purgeObjects() {
		for(int i=0; i<aliens.size(); i++) {
			if(!aliens.get(i).isActive) {
				aliens.remove(i);
			}
		}
		for(int i=0; i<projectiles.size(); i++) {
			if(!projectiles.get(i).isActive) {
				projectiles.remove(i);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		addAliens();
	}
}
