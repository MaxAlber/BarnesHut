import javax.swing.*;
public class Gui
{
    public Gui()
    {
        JFrame frame = new JFrame("Barnes Hut");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        JButton button = new JButton("Press");
        button.setBounds(100,100,100, 40);
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }
}
