package com.beuapp.view.menubar;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class MenuButton extends JButton{

    // Index of the button
    private int index;
    // If it has a sub menu
    private boolean subMenuAble;

    // Index of button in submenu
    private int subMenuIndex;
    // Length of sub menu
    private int length;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSubMenuAble() {
        return subMenuAble;
    }

    public void setSubMenuAble(boolean subMenuAble) {
        this.subMenuAble = subMenuAble;
    }

    public int getSubMenuIndex() {
        return subMenuIndex;
    }

    public void setSubMenuIndex(int subMenuIndex) {
        this.subMenuIndex = subMenuIndex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    private int rippleX = -1, rippleY = -1;  // Position of the ripple
    private float rippleRadius = 0;  // Radius of the ripple
    private Timer rippleTimer;  // Timer for the ripple animation

    private boolean isArrowFlipped = false; // Track if arrow is flipped
    private double currentAngle = 0; // Angle for smooth rotation
    private Timer rotationTimer; // Timer to handle the rotation animation

    // Constructor for buttons in menu(and sub menus)
    MenuButton(String name, int index, boolean subMenuAble){
        // Set text
        super(name);

        this.index = index;
        this.subMenuAble = subMenuAble;

        // Set up button
        setPreferredSize(new Dimension(240, 40));
        setContentAreaFilled(false);
        setForeground(Color.white);
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(new EmptyBorder(9, 9, 9, 10));
        setIconTextGap(10);
        setFont(new Font("Roboto Mono", Font.PLAIN, 18));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        if(index != 0 && !subMenuAble){
            setIcon(new ImageIcon(new BufferedImage(15, 10, BufferedImage.TYPE_INT_ARGB)));
        }

        // Animations for when clicked
        this.addActionListener(e -> {
            triggerRippleEffect(e);  // Trigger ripple effect
            toggleArrow();           // Trigger arrow flip
        });
    }

    // Initialize sub menu buttons
    public void initSubMenu(int subMenuIndex, int length){
        this.subMenuIndex = subMenuIndex;
        this.length = length;

        setContentAreaFilled(false);
        
    }

    @Override
    protected void paintComponent(Graphics grphcs){
        super.paintComponent(grphcs);

        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (rippleRadius > 0) {
            g2.setColor(new Color(255, 255, 255, (int) Math.max(255 - rippleRadius * 2, 0)));  // Fade out effect
            g2.fillOval(rippleX - (int) rippleRadius, rippleY - (int) rippleRadius, (int) rippleRadius * 2, (int) rippleRadius * 2);
        }

        if(length != 0){
            g2.setColor(new Color(3, 25, 38));
            if(subMenuIndex == 1 && length == 2){
                g2.drawLine(18, 0, 18, getHeight() / 2);
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            } else if(subMenuIndex == 1){
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            }else if(subMenuIndex == length - 1){
                g2.drawLine(18, 0, 18, getHeight() / 2);
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            }else{
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            }
        } else if (subMenuAble) {
            g2.setColor(getForeground());  // Set the color of the arrow
            int arrowWidth = 8;  // Width of the arrow
            int arrowHeight = 4; // Height of the arrow
        
            // Create a Path2D object for the arrow shape
            Path2D p = new Path2D.Double();
            p.moveTo(0, 0);  // Start at the left side of the arrow base
            p.lineTo(arrowWidth / 2, arrowHeight);  // Left point of the triangle
            p.lineTo(arrowWidth, 0);  // Right point of the triangle
        
            // Translate the shape to the desired location on the screen
            g2.translate(getWidth() - arrowWidth - 15, (getHeight() - arrowHeight) / 2);
        
            // Set rendering hints for better stroke quality
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(currentAngle), arrowWidth / 2, arrowHeight / 2); // Rotate around center
            g2.draw(transform.createTransformedShape(p));  // You can use fill() or draw() depending on your preference
        }

        g2.dispose();
    }

    // Trigger ripple effect on mouse click
    private void triggerRippleEffect(ActionEvent e) {
        // Get the position where the button was clicked
        Point clickPoint = e.getSource() instanceof MenuButton ? this.getMousePosition() : new Point(0, 0);
        if (clickPoint != null) {
            rippleX = clickPoint.x;
            rippleY = clickPoint.y;
            rippleRadius = 0;

            // Start the ripple animation
            if (rippleTimer != null && rippleTimer.isRunning()) {
                rippleTimer.stop();
            }

            rippleTimer = new Timer(15, ev -> {
                rippleRadius += 8;  // Increase the radius
                if (rippleRadius > getWidth()) {  // End the ripple when it exceeds the button size
                    rippleTimer.stop();
                }
                repaint();  // Repaint to show the growing ripple
            });
            rippleTimer.start();  // Start the ripple animation
        }
    }

    // Toggle the arrow rotation
    private void toggleArrow() {
        // If there's an existing rotation timer, stop it
        if (rotationTimer != null && rotationTimer.isRunning()) {
            rotationTimer.stop();
        }

        // Determine the target angle and start the rotation animation
        if (isArrowFlipped) {
            startArrowFlipAnimation(0);  // Rotate back to 0 degrees (up)
        } else {
            startArrowFlipAnimation(180);  // Rotate to 180 degrees (down)
        }
        isArrowFlipped = !isArrowFlipped;
    }

    // Start the animation to smoothly rotate the arrow
    private void startArrowFlipAnimation(int targetAngle) {
        rotationTimer = new Timer(5, e -> { // Reduced the interval to 5ms for faster updates
            // Smooth animation to the target angle
            double diff = targetAngle - currentAngle;
            
            // Stop animation if the difference is below a small threshold
            if (Math.abs(diff) < 1) {  // When the angle difference is small
                currentAngle = targetAngle;  // Directly set the target angle
                ((Timer) e.getSource()).stop(); // Stop timer when the angle is reached
                repaint(); // Trigger repaint to redraw the arrow
            } else {
                // Gradually reduce the step size as we get closer to the target
                double step = Math.signum(diff) * Math.min(Math.abs(diff), 15); // Adjust step size for smoothness
                currentAngle += step; // Incrementally update the angle
                repaint(); // Repaint to show the new arrow position
            }
        });
        rotationTimer.start(); // Start the animation
    }
}
