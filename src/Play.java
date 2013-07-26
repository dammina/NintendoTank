import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class Play {

    public void start() throws InterruptedException {
        Client client = new Client();
        client.sendMessage("JOIN#");
        
    }

    public void makeMove(String move) {
        Client client = new Client();
        client.sendMessage(move);
    }
    
     
}
