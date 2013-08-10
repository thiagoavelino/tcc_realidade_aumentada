package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class CamRAPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage master;
	
	public CamRAPanel(BufferedImage master) {
		setMaster(master);
        Graphics2D g2d = getMaster().createGraphics();
        g2d.drawImage(getMaster(), 0, 0, this);
        g2d.dispose();
        setBounds(new Rectangle(700, 5, 640, 480));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(getMaster().getWidth(), getMaster().getHeight());
        return size;
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(getMaster(), 0, 0, this);
    }

	public BufferedImage getMaster() {
		return master;
	}

	public void setMaster(BufferedImage master) {
		this.master = master;
	}

}