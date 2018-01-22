import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends Rectangle{
	public int xC, yC;
	
	public int enemySize = 64;
	public int enemyWalk = 0;
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
			if(Screen.room.block[y][0].groundID == Value.GROUND_ROAD || Screen.room.block[y][0].groundID == Value.GROUND_START){
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, enemySize, enemySize);
				xC = 0;
				yC = y;
			}
		}
		this.enemyID = enemyID;
		inGame = true;
	}
	
	public void deleteEnemy() {
		inGame = false;
	}
	
	public void looseHealth() {
		Screen.health-=1;
	}
	public int walkFrame = 0, walkSpeed = 5;
	
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
				if(Screen.room.block[yC+1][xC].groundID == Value.GROUND_ROAD || Screen.room.block[yC+1][xC].groundID == Value.GROUND_END){
					direction = downward;
				}
				}catch(Exception e){}
				}
				if(!hasDownward){
				try{
					if(Screen.room.block[yC-1][xC].groundID == Value.GROUND_ROAD|| Screen.room.block[yC-1][xC].groundID == Value.GROUND_END){
						direction = upward;
					}
					}catch(Exception e){}
				}
				if(!hasLeft){
				try{
					if(Screen.room.block[yC][xC+1].groundID == Value.GROUND_ROAD|| Screen.room.block[yC][xC+1].groundID == Value.GROUND_END){
						direction = right;
					}
					}catch(Exception e){}
				}
				if(!hasRight){
					try{
						if(Screen.room.block[yC][xC-1].groundID == Value.GROUND_ROAD|| Screen.room.block[yC][xC-1].groundID == Value.GROUND_END){
							direction = left;
						}
						}catch(Exception e){}
				}
				if(Screen.room.block[yC][xC].groundID == Value.GROUND_END) {
					deleteEnemy();
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
	public void draw (Graphics g){

			g.drawImage(Screen.enemy[enemyID], x, y, width, height, null);
	}
}
