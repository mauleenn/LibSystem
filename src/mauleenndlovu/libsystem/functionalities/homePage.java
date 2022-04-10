
package mauleenndlovu.libsystem.functionalities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class homePage {

        public static void main(String[] args) throws IOException {
                new homePage();
        }

        public homePage() throws IOException {
                BufferedImage booksImage = ImageIO.read(new File("/Users/mauleenndlovu/Desktop/library.jpeg"));
                Image image = booksImage.getScaledInstance(700, 500, Image.SCALE_DEFAULT);


                ImageIcon icon = new ImageIcon(image);
                JFrame frame = new JFrame();
                frame.setSize(700,500);

                frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                                System.exit(0);
                        }
                });

                JLabel welcomeLabel = new JLabel( "Centered", SwingConstants.CENTER );
                frame.add(welcomeLabel, BorderLayout.CENTER);

                welcomeLabel.setText("Welcome to the Library");
                welcomeLabel.setIcon(icon);
                welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 48));
                welcomeLabel.setForeground(Color.BLACK);
                welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
                frame.add(welcomeLabel);


                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
}



