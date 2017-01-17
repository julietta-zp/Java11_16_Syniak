package xyz.hedo.shop.controller;

import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.view.View;

public final class Controller {

    private final CommandProvider provider = new CommandProvider();
    private final char paramSeparator = '?';
    private View view;

    public void setView(View view){
        this.view = view;
    }

    public String executeTask(String request){

        String commandName;
        Command executionCommand;

        commandName = request.substring(0, request.indexOf(paramSeparator));
        executionCommand = provider.getCommand(commandName);

        String response;
        response = executionCommand.execute(request);

        return response;

    }
}
