/*
 * Decompiled with CFR 0.150.
 */
package wordeotest;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class openClipWin {
    private static JFrame frame = new JFrame();

    public static void getMov(final String name, final String url) {
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                frame = new JFrame("Sentence with the word " + name);
                frame.setDefaultCloseOperation(2);
                frame.getContentPane().add((Component)openClipWin.getBrowser(url), "Center");
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/ytlogo.jpg"));
                frame.setSize(650, 650);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

            @Override
            public void run() {
                NativeInterface.close();
            }
        }));
    }

    public static JPanel getBrowser(final String url) {
        JPanel wbPanel = new JPanel(new BorderLayout());
        final JWebBrowser wb = new JWebBrowser(new NSOption[0]);
        wbPanel.add((Component)wb, "Center");
        wb.setBarsVisible(false);
        wb.navigate(url);
        JButton watchagain = new JButton(" Press Here to see the Sentence again");
        wbPanel.add((Component)watchagain, "Last");
        watchagain.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                wb.navigate(url);
                wb.revalidate();
                wb.repaint();
            }
        });
        return wbPanel;
    }
}

