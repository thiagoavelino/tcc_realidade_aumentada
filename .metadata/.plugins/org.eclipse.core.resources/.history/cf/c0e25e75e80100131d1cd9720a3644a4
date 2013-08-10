package Model;


import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;


public class CamMonitor implements Runnable{
	private Cam cam;
	private int counter;
	
	public CamMonitor(){
		cam = new Cam();
		counter = 0;
	}

	@Override
	public void run() {
		while(true){
			//Webcam.setAutoOpenMode(true);
			//BufferedImage image = Webcam.getDefault().getImage();
			//cam.saveImage("test"+ counter, image);
			counter++;
			System.out.println("foto "+counter);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
