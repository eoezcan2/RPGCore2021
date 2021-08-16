package at.emreeocn.rpgcore.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import at.emreeocn.rpgcore.scoreboard.ServerScoreboard;
import at.emreeocn.rpgcore.task.Task;

public class TaskFinishedEvent extends Event {
	private Player player;
	private Task task;
	
	private boolean cancelled;
	private final HandlerList HANDLERS = new HandlerList();
	
	public TaskFinishedEvent(Player player, Task task) {
		this.setPlayer(player);
		this.setTask(task);
		
		if(!Task.didTask(player, task)) {
			Task.setTask(player, task);
			task.getReward().reward(player);
			player.sendMessage("§6§lTASK ABGESCHLOSSEN");
			player.sendMessage("§6Titel: §r§2[" + task.getTitle() + "]");
			player.sendMessage("§r§6Beschreibung: §a" + task.getDescription() + "");
			player.sendMessage("§6Belohnung:");
			for(String s : Task.rewardLores(task)) {
				player.sendMessage(s);
			}
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 4);
			ServerScoreboard sb = new ServerScoreboard(player);
			sb.display();
		}
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public HandlerList getHANDLERS() {
		return HANDLERS;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
