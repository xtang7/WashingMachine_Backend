package entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class User {
	private String username;
	private long remainingTime;
	private boolean alarm; // true --> finish washing, go and get the clothes
	private Washer washer; // which washer will be used
	
	public User(String username) {
		this.username = username;
	}
	
	public String getName() {
		return username;
	}
	
	public boolean checkAvailability() {
		return washer.isAvailiable();
	}
	
	public void setWasher(Washer availableWasher) {
		washer = availableWasher;
	}
	
	public long getRemainingTime() { // if no machine available, it is waiting available time, else it is waiting finish washing time
		LocalDateTime from = LocalDateTime.now();
		if (checkAvailability() == true) { // Case 1: Finish washing
		    LocalDateTime to = washer.getEndTime();
		    remainingTime = ChronoUnit.MINUTES.between(from, to);
			return remainingTime;
		} else { // Case 2: Waiting to wash
			LocalDateTime to = washer.getEndTime().plusMinutes(5); 
                                   // 5 minutes for other user to pick up
			remainingTime = ChronoUnit.MINUTES.between(from, to);
			return remainingTime;
		}
	}
	
	// start washing, adding user info to washer side
	public String startWashing() { // if starting washing, return username to washer side.
		if (checkAvailability() == true) {
			return username;
		}
		return null;
	}
	
	public void setAlarm() {
		alarm = true;
	}
	
	public void sendNotification() {
		if (washer.getEndTime() == LocalDateTime.now()) { // finish washing, send notice to user
			setAlarm();
			// send notification
			System.out.println("Your clothes is FRESH now! Come and get them!!");
		}
	}
}
