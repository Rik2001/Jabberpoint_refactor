import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.*;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	private static final long serialVersionUID = 227L;
	
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";

	protected static final String TESTFILE = "testPresentation.xml";
	protected static final String SAVEFILE = "savedPresentation.xml";

	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
	protected static final String SAVEERR = "Save Error";

	public MenuController(Frame parent, Presentation presentation) {
		MenuItem menuItem;
		Menu fileMenu = new Menu(FILE);
		fileMenu.add(menuItem = mkMenuItem(OPEN));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				loadPresentation(presentation, parent);
			}
		} );

		fileMenu.add(menuItem = mkMenuItem(NEW));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				newPresentation(presentation, parent);
			}
		});

		fileMenu.add(menuItem = mkMenuItem(SAVE));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePresentation(presentation, parent);
			}
		});

		fileMenu.addSeparator();
		fileMenu.add(menuItem = mkMenuItem(EXIT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				exit(presentation);
			}
		});

		add(fileMenu);
		Menu viewMenu = new Menu(VIEW);
		viewMenu.add(menuItem = mkMenuItem(NEXT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				nextSlide(presentation);
			}
		});

		viewMenu.add(menuItem = mkMenuItem(PREV));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				previousSlide(presentation);
			}
		});

		viewMenu.add(menuItem = mkMenuItem(GOTO));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				goToSlide(presentation);
			}
		});

		add(viewMenu);
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(menuItem = mkMenuItem(ABOUT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				showAboutBox(parent);
			}
		});

		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
	}

//Creating a menu-item
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}

	private void loadPresentation(Presentation presentation, Frame parent){
		presentation.clear();
		XMLParser xmlParser = new XMLParser();
		try {
			xmlParser.loadFile(presentation, TESTFILE);
			presentation.setSlideNumber(0);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(parent, IOEX + exc,
					LOADERR, JOptionPane.ERROR_MESSAGE);
		}
		parent.repaint();
	}

	private void newPresentation(Presentation presentation, Frame parent){
		presentation.clear();
		parent.repaint();
	}

	private void savePresentation(Presentation presentation, Frame parent){
		XMLParser xmlParser = new XMLParser();
		try {
			xmlParser.saveFile(presentation, SAVEFILE);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(parent, IOEX + exc,
					SAVEERR, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void exit(Presentation presentation){
		presentation.exit(0);
	}

	private void nextSlide(Presentation presentation){
		presentation.nextSlide();
	}

	private void previousSlide(Presentation presentation){
		presentation.prevSlide();
	}

	private void goToSlide(Presentation presentation){
		String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
		int pageNumber = Integer.parseInt(pageNumberStr);

		//make sure user does not go to slide that doesn't exist
		if (pageNumber <= 0 || pageNumber > presentation.getSize()){
			JOptionPane.showMessageDialog(new JFrame(),"This slide does not exist!");
			return;
		}
		presentation.setSlideNumber(pageNumber - 1);
	}

	private void showAboutBox(Frame parent){
		JOptionPane.showMessageDialog(
				parent,
				"JabberPoint is a primitive slide-show program in Java(tm). It\n" +
						"is freely copyable as long as you keep this notice and\n" +
						"the splash screen intact.\n" +
						"Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
						"Adapted by Gert Florijn (version 1.1) and " +
						"Sylvia Stuurman (version 1.2 and higher) for the Open" +
						"University of the Netherlands, 2002 -- now.\n" +
						"Author's version available from http://www.darwinsys.com/",
				"About JabberPoint",
				JOptionPane.INFORMATION_MESSAGE
		);
	}
}
