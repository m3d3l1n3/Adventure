import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    Integer[] keyState = new Integer[4]; // state od the key - pressed/ releasead in order: W S A D
    String name;

    public Integer[] getKeyState() {
        return keyState;
    }

    public void setKeyState(Integer[] keyState) {
        this.keyState = keyState;
    }

    public void setInitialKeyState() {
        for (int i = 0; i < 4; i++)
            this.keyState[i] = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                keyState[0] = 1;
                System.out.println("Key pressed: W\n");
                name = "UP";
                break;
            case KeyEvent.VK_S:
                keyState[1] = 1;
                System.out.println("Key pressed: S\n");
                name = "DOWN";
                break;
            case KeyEvent.VK_A:
                keyState[2] = 1;
                System.out.println("Key pressed: A\n");
                break;
            case KeyEvent.VK_D:
                keyState[3] = 1;
                System.out.println("Key pressed: D\n");
                break;
        }
        //setKeyState(state);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                keyState[0] = 0;
                System.out.println("Key released: W\n");
                break;
            case KeyEvent.VK_S:
                keyState[1] = 0;
                System.out.println("Key released: S\n");
                break;
            case KeyEvent.VK_A:
                keyState[2] = 0;
                System.out.println("Key released: A\n");
                break;
            case KeyEvent.VK_D:
                keyState[3] = 0;
                System.out.println("Key released: D\n");
                break;
        }
    }
}
