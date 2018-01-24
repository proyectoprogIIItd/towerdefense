package juego.towerDefense;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends Rectangle{
	public int xC, yC;
	
	public int enemySize = 64;
	public int enemyWalk = 0;
	public int health;
	public int healthSpace = 3;
	public int healthHeight = 6;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	public int enemyID = Value.ENEMY_AIR;
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;
	
	public Enemy(){
		
	}
	public void SpawnEnemy(int enemyID){

		for(int  y = 0; y <Screen.room.block.length; y++){
			if(Screen.room.block[y][0].tierraID == Value.GROUND_ROAD || Screen.room.block[y][0].tierraID == Value.GROUND_START){
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, enemySize, enemySize);
				xC = 0;
				yC = y;
			}
		}
		this.enemyID = enemyID;
		this.health = enemySize;
		inGame = true;
	}
	public void deleteEnemyFueraMapa() {
		inGame = false;
		direction = right;
		enemyWalk = 0;
	}
	public void deleteEnemy() {
		inGame = false;
		direction = right;
		enemyWalk = 0;
		if(Bloque.enemigoDisparadoDinero == -1) {
			
		}else {
		Bloque.getMoney(Screen.enemies[Bloque.enemigoDisparadoDinero].enemyID);
		}
		Screen.enemyKilled += 1;
		
	}
	
	public void looseHealth() {
		Screen.health-=1;
	}
	public int walkFrame = 0, walkSpeed = 10;
	
	public void physic(){
		if(walkFrame >= walkSpeed){
			if(direction == right){
				x += 1;
			}else if (direction == upward){
				y-= 1;
			}else if(direction == downward){
				y+=1;
			}else if(direction == left){
				x-= 1;
			}
			
			enemyWalk +=1;
			if(enemyWalk == Screen.room.blockSize){ //acaba un bloque y comprueba el siguiente
				if(direction == right){
					xC += 1;
					hasRight = true;
				}else if (direction == upward){
					yC-= 1;
					hasUpward=true;
				}else if(direction == downward){
					yC+=1;
					hasDownward=true;
				}else if(direction == left){
					xC -= 1;
					hasLeft = true;
				}
				if(!hasUpward){
				try{
				if(Screen.room.block[yC+1][xC].tierraID == Value.GROUND_ROAD || Screen.room.block[yC+1][xC].tierraID == Value.GROUND_END){
					direction = downward;
				}
				}catch(Exception e){}
				}
				if(!hasDownward){
				try{
					if(Screen.room.block[yC-1][xC].tierraID == Value.GROUND_ROAD|| Screen.room.block[yC-1][xC].tierraID == Value.GROUND_END){
						direction = upward;
					}
					}catch(Exception e){}
				}
				if(!hasLeft){
				try{
					if(Screen.room.block[yC][xC+1].tierraID == Value.GROUND_ROAD|| Screen.room.block[yC][xC+1].tierraID == Value.GROUND_END){
						direction = right;
					}
					}catch(Exception e){}
				}
				if(!hasRight){
					try{
						if(Screen.room.block[yC][xC-1].tierraID == Value.GROUND_ROAD|| Screen.room.block[yC][xC-1].tierraID == Value.GROUND_END){
							direction = left;
						}
						}catch(Exception e){}
				}
				if(Screen.room.block[yC][xC].tierraID == Value.GROUND_END) {
					deleteEnemyFueraMapa();
					looseHealth();
				}
				
				hasUpward= false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				enemyWalk=0;
			}
			walkFrame = 0;
		}else{
			walkFrame +=1;
		}
		
	}
	
	public void enemyLooseHealth(int amo) {
		health -= amo;
		checkDeath();
	}
	
	public void checkDeath() {
		if(health <=0 ) {
			deleteEnemy();
		}
	}
	
	public boolean isDead() {
		if(inGame) {
			return false;
		}else {
			return true;
		}
	}
	public void draw (Graphics g){

			g.drawImage(Screen.enemy[enemyID], x, y, width, height, null);
			// HEALTH BAR
			g.setColor(new Color(180, 50, 50));
			g.fillRect(x,  y- (healthSpace + healthHeight ), width, healthHeight);
			
			g.setColor(new Color(50, 180, 50));
			g.fillRect(x,  y- (healthSpace + healthHeight ), health, healthHeight);
			g.setColor(new Color(0,0,0));
			Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(1));
			g.drawRect(x,  y- (healthSpace + healthHeight ), health-1, healthHeight-1 );
	}
}