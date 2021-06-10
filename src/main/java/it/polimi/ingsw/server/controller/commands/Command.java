package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;

import java.util.List;

/**
 * All commands implement this interface since they all share the same structure
 * @author Giulio Occhipinti
 */

public interface Command
{
	boolean run(List<String> command);
}
