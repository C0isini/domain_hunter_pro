package burp;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TextAreaMenu extends JPopupMenu {

	PrintWriter stdout;
	PrintWriter stderr;
	TextAreaMenu(final String selectedText){

        try{
            stdout = new PrintWriter(BurpExtender.getCallbacks().getStdout(), true);
            stderr = new PrintWriter(BurpExtender.getCallbacks().getStderr(), true);
        }catch (Exception e){
            stdout = new PrintWriter(System.out, true);
            stderr = new PrintWriter(System.out, true);
        }

        List<String> selectedItems = Arrays.asList(selectedText.split(System.lineSeparator()));
		JMenuItem googleSearchItem = new JMenuItem(new AbstractAction("Google It (double click)") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (selectedItems.size() >=50) {
					return;
				}
				for (String item:selectedItems) {
					String url= "https://www.google.com/search?q=%22"+item+"%22";
					try {
						URI uri = new URI(url);
						Desktop desktop = Desktop.getDesktop();
						if(Desktop.isDesktopSupported()&&desktop.isSupported(Desktop.Action.BROWSE)){
							desktop.browse(uri);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});

		this.add(googleSearchItem);
		
		
		JMenuItem SearchOnGithubItem = new JMenuItem(new AbstractAction("Seach On Github") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (selectedItems.size() >=50) {
					return;
				}
				for (String item:selectedItems) {
					try {
						String url= "https://github.com/search?q=%22"+item+"%22+%22jdbc.url%22&type=Code";
						URI uri = new URI(url);
						Desktop desktop = Desktop.getDesktop();
						if(Desktop.isDesktopSupported()&&desktop.isSupported(Desktop.Action.BROWSE)){
							desktop.browse(uri);
						}
					} catch (Exception e2) {
						e2.printStackTrace(stderr);
					}
				}
			}
		});

		this.add(SearchOnGithubItem);
	}
}
