package application;

import java.util.List;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class Loop extends Thread{
	
	private GamePanel gp;
	private int size;
	private Cell[][] grid;
	
	private static String encodingResource="encodings/just11";
	
	private static Handler handler;

	public Loop(GamePanel gp) {
		this.gp = gp;
		size=gp.getGridSize();
		grid=gp.getGrid();
		//Windows 64bit
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));

		//Linux 64bit
		//handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
						
		//MacOS 64bit
		//handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2-mac"));
				
		try {
			ASPMapper.getInstance().registerClass(Cell.class);
			ASPMapper.getInstance().registerClass(Seleziona.class);
		} catch (ObjectNotValidException | IllegalAnnotationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			InputProgram facts= new ASPInputProgram();
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					try {
						facts.addObjectInput(new Cell(i, j, grid[j][i].getType()));
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}			
			}
					
			//Aggiungiamo all'handler i fatti 
			handler.addProgram(facts);
							
			//Specifichiamo il programma logico tramite file
			InputProgram encoding= new ASPInputProgram();
			encoding.addFilesPath(encodingResource);
							
			//Aggiungiamo all'handler il programma logico
			handler.addProgram(encoding);
							
			//L'handler invoca DLV2 in modo SINCRONO dando come input il programma logico e i fatti
			Output o =  handler.startSync();
					
			//Analizziamo l'answer set 
			AnswerSets answersets = (AnswerSets) o;
					
			List<AnswerSet> optimal=answersets.getOptimalAnswerSets();
			System.out.println(optimal.size());
			for(AnswerSet a:optimal){
				try {
					System.out.println(a);
					for(Object obj:a.getAtoms()){
						if(!(obj instanceof Seleziona)) continue;
						System.out.println(obj);
						Seleziona cell= (Seleziona) obj;
						System.out.println("UPDATING:"+cell.getX()+","+cell.getY());
						//Thread.sleep(5000); //TODO DELETE LATER, TEST
						gp.updateGame(cell.getY(),cell.getX());
						System.out.println("UPDATED");
					}
				handler.removeAll();
				} catch (Exception e) {
					e.printStackTrace();
				} 
								
			}						
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
