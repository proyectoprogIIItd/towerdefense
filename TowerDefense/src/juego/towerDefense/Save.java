package juego.towerDefense;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Save {
	
	public void loadSave(File loadPath){
		
		//pasamos la informacion del fichero al array de 2 dimensiones
		try {
			Scanner loadScanner = new Scanner (loadPath);
			
			while(loadScanner.hasNext()){
				for(int y=0; y<Screen.room.bloque.length;y++){
					for(int x=0; x<Screen.room.bloque[0].length;x++){
						Screen.room.bloque[y][x].tierraID = loadScanner.nextInt();
					}
			//pasa de valor en valor en el fichero
				}
				for(int y=0; y<Screen.room.bloque.length;y++){
					for(int x=0; x<Screen.room.bloque[0].length;x++){
						Screen.room.bloque[y][x].aireID = loadScanner.nextInt();
					}
				}
			}
			loadScanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
