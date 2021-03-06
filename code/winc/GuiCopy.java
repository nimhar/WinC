/*
 * Decompiled with CFR 0.150.
 */
package wordeotest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import wordeotest.logic;
import wordeotest.openClipWin;

public class GuiCopy {
    private static JPanel scan(final String text) throws SQLException {
        JPanel res = new JPanel();
        res.setLayout(new BoxLayout(res, 1));
        res.add(Box.createRigidArea(new Dimension(10, 10)));
        ArrayList<JPanel> bor = new ArrayList<JPanel>();
        ArrayList<JButton> opt = new ArrayList<JButton>();
        ArrayList<JLabel> lab = new ArrayList<JLabel>();
        logic trylog = new logic();
        trylog.scanIt(text);
        if (trylog.getDur() != null) {
            for (int i = 0; i < trylog.getDur().size(); ++i) {
                int start = trylog.getStart().get(i) - 5;
                int duration = start + trylog.getDur().get(i) + 10;
                String url = "https://www.youtube.com/embed/" + trylog.getUrl().get(i) + "?hl=en&cc_lang_pref=en&cc_load_policy=1&start=" + start + "&end=" + duration + "&autoplay=1&controls=1&autohide=1&showinfo=0&modestbranding=1&rel=0&cc_load_policy=1";
                opt.add(new JButton(String.valueOf(i)));
                ((JButton)opt.get(opt.size() - 1)).addActionListener((new ActionListener(){
                    private String useUrl;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Thread t = new Thread(new Runnable(){

                            @Override
                            public void run() {
                                openClipWin.getMov(text, useUrl);
                            }
                        });
                        t.start();
                    }

                    private ActionListener init(String urlFor) {
                        this.useUrl = urlFor;
                        return this;
                    }
                }).init(url));
                lab.add(new JLabel(String.valueOf(trylog.getText().get(i)) + " "));
                bor.add(new JPanel());
                ((JPanel)bor.get(bor.size() - 1)).setLayout(new BoxLayout((Container)bor.get(bor.size() - 1), 2));
                ((JPanel)bor.get(bor.size() - 1)).setAlignmentX(0.0f);
                ((JLabel)lab.get(lab.size() - 1)).setLabelFor((Component)opt.get(opt.size() - 1));
                ((JPanel)bor.get(bor.size() - 1)).add((Component)opt.get(opt.size() - 1), "Before");
                ((JPanel)bor.get(bor.size() - 1)).add(Box.createRigidArea(new Dimension(15, 0)));
                ((JPanel)bor.get(bor.size() - 1)).add((Component)lab.get(lab.size() - 1), "Center");
                res.add((Component)bor.get(bor.size() - 1));
                res.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            return res;
        }
        return null;
    }

    static void startGui() {
        final JFrame frame = new JFrame("Winc");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.jpg"));
        JPanel firstpanel = new JPanel(new FlowLayout());
        firstpanel.setBackground(new Color(247, 82, 79));
        firstpanel.setPreferredSize(new Dimension(580, 40));
        final JPanel result = new JPanel();
        JLabel firstlabel = new JLabel("ENTER WORD FOR SEARCH", 11);
        firstpanel.add(firstlabel);
        final JTextField textField = new JTextField(12);
        firstlabel.setLabelFor(textField);
        firstpanel.add(textField);
        JButton button = new JButton("Submit");
        firstpanel.add(new JLabel());
        firstpanel.add(button);
        final JPanel loadpanel = new JPanel();
        loadpanel.setBackground(Color.CYAN);
        ImageIcon imageIcon = new ImageIcon("img/spinner.gif");
        JLabel loading = new JLabel(imageIcon);
        loadpanel.add((Component)loading, "Last");
        loadpanel.setLayout(new BoxLayout(loadpanel, 3));
        loadpanel.setSize(200, 200);
        AbstractAction action = new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent e) {
                result.removeAll();
                result.revalidate();
                result.repaint();
                frame.getContentPane().remove(result);
                frame.setSize(580, 300);
                loadpanel.setOpaque(true);
                frame.getContentPane().add(loadpanel);
                frame.revalidate();
                frame.repaint();
                Thread t2 = new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            JPanel res = new JPanel();
                            res = GuiCopy.scan(textField.getText());
                            if (res != null) {
                                JScrollPane pan = new JScrollPane(res, 20, 30);
                                pan.setMinimumSize(new Dimension(530, 350));
                                pan.setPreferredSize(new Dimension(530, 350));
                                result.add((Component)pan, "West");
                                frame.setSize(570, 470);
                                frame.getContentPane().remove(loadpanel);
                                frame.getContentPane().add(result);
                                frame.revalidate();
                                frame.repaint();
                            } else {
                                String labels = "Word not Found";
                                JPanel p = new JPanel(null);
                                JLabel l = new JLabel(labels, 11);
                                p.add(l);
                                JButton button = new JButton("Quit");
                                p.add(new JLabel());
                                p.add(button);
                                p.setLayout(new FlowLayout());
                                button.addActionListener(new ActionListener(){

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.exit(0);
                                    }
                                });
                                frame.getContentPane().remove(loadpanel);
                                frame.getContentPane().add(result);
                                result.add(p);
                                p.setPreferredSize(new Dimension(530, 120));
                                frame.revalidate();
                                frame.repaint();
                            }
                        }
                        catch (SQLException e1) {
                            frame.getContentPane().remove(loadpanel);
                            e1.printStackTrace();
                            System.out.println("Problem has been occured");
                        }
                    }
                });
                t2.start();
            }
        };
        button.addActionListener(action);
        textField.addActionListener(action);
        frame.setDefaultCloseOperation(3);
        frame.setContentPane(firstpanel);
        frame.setSize(580, 60);
        frame.pack();
        frame.setVisible(true);
    }
}

