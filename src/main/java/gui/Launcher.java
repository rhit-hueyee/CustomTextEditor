package gui;

public class Launcher {
    public static void main(String[] args){
        //UIManager.put("OptionsPane.isYesLast", true); //no idea what this does lmao
        App app = new App();
        app.launchMainUi();
    }

}
