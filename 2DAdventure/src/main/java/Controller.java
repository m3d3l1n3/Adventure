import saving.Progress;

public class Controller implements Runnable {
    Panel panel;
    Thread gameThread;
    Progress saveProgress = new Progress();
    String playerName;
    //KeyHandler keyHandler;
    public Controller(Panel panel) {
        this.panel = panel;
      //  this.playerName = playerName;
        this.gameThread = new Thread(this);
        System.out.println("Game has started, look in the window. Details: 60FPS.");
        gameThread.start();

    }


    @Override
    public void run() { // game loop
        double drawInterval = 1000000000 / 60; // for calculating fps with nanoseconds, and the nr of fpes to be 60
        double nextDrawInterval = System.nanoTime() + drawInterval;
        while (gameThread.isAlive()) {
            System.out.println("Game running...");
            panel.update();
            panel.repaint();
            try {
                double remainingInterval = nextDrawInterval - System.nanoTime();
                remainingInterval /= 1000000;
                if (remainingInterval < 0) {
                    remainingInterval = 0;
                }
                Thread.sleep((long) remainingInterval);
                //saveProgress.saveProgressToDatabase(playerName,0,panel.player.getPositionX(),panel.player.getPositionY());
                nextDrawInterval += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
