import javax.swing.*;
import java.awt.*;


public class board extends JPanel {

	public board() {
	
		this.setPreferredSize(new Dimension(1024,1024));
		stones wChecker1 = new stones('a', 1, true);
		stones wChecker2 = new stones('c', 1, true);
		stones wChecker3 = new stones('e', 1, true);
		stones wChecker4 = new stones('g', 1, true);
		stones wChecker5 = new stones('b', 2, true);
		stones wChecker6 = new stones('d', 2, true);
		stones wChecker7 = new stones('f', 2, true);
		stones wChecker8 = new stones('h', 2, true);
		stones wChecker9 = new stones('a', 3, true);
		stones wChecker10 = new stones('c', 3, true);
		stones wChecker11 = new stones('e', 3, true);
		stones wChecker12 = new stones('g', 3, true);
		
		stones bChecker1 = new stones('b', 8, false);
		stones bChecker2 = new stones('d', 8, false);
		stones bChecker3 = new stones('f', 8, false);
		stones bChecker4 = new stones('h', 8, false);
		stones bChecker5 = new stones('a', 7, false);
		stones bChecker6 = new stones('c', 7, false);
		stones bChecker7 = new stones('e', 7, false);
		stones bChecker8 = new stones('g', 7, false);
		stones bChecker9 = new stones('b', 6, false);
		stones bChecker10 = new stones('d', 6, false);
		stones bChecker11 = new stones('f', 6, false);
		stones bChecker12 = new stones('h', 6, false);
	
	}
	
	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		//Color light = new Color(149,122,76);
		//Color dark = new Color(230,219,241);
		
		//g2D.setPaint(new Color(149,122,76));
		//g2D.setStroke(new BasicStroke(15));
		//g2D.drawLine(0,0,256,256);
		//g2D.fillRect(256,256,256,256);
		boolean lightSquare = false;
		
		
		
		Image imgb = new ImageIcon("blackChecker.png").getImage();
		Image imgw = new ImageIcon("whiteChecker.png").getImage();
		Image wStone = imgw.getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH);
		Image bStone = imgb.getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH);
		Image imgwk = new ImageIcon("whitekingChecker.png").getImage();
		Image imgbk = new ImageIcon("blackKingChecker.png").getImage();
		Image wKing = imgwk.getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH);
		Image bKing = imgbk.getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH);
		
		
		for (int x = 0; x < 1024; x += 128) {
			for (int y = 0; y < 1024; y += 128) {
				
				if (lightSquare) {
					g2D.setPaint(new Color(149,122,176));
					g2D.fillRect(x,y,128,128);
					lightSquare =! lightSquare;
				} else {
					g2D.setPaint(new Color(230,219,241));
					g2D.fillRect(x,y,128,128);
					lightSquare =! lightSquare;
				}				
			}
			lightSquare =! lightSquare;
		}
		
		
		for (stones checker : stones.getSurvivors()) {
			
			if (checker.white) {
				if (checker.king) {
					g2D.drawImage(imgwk, checker.drawX, checker.drawY, null);
				} else {
					g2D.drawImage(imgw, checker.drawX, checker.drawY, null);
				}
			} else {
				if (checker.king) {
					g2D.drawImage(imgbk, checker.drawX, checker.drawY, null);
				} else {
					g2D.drawImage(imgb, checker.drawX, checker.drawY, null);
				}
			}
			
		}
	}
}