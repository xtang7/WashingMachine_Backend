package entity;

import java.time.LocalDateTime;

public class Washer {
	
	private enum Cycle{
		DELICATE, RAPIDWASH, PERMANENTPRESS, NORMAL, HEAVYDUTY, DEFAULT
	}
	
	private String washerId;
	private boolean availability;
	private Mode mode;
	private LocalDateTime endTime;
	private LocalDateTime startTime;
	private User user;
	
	private Washer(WasherBuilder builder) {
		this.washerId = builder.washerId;
		this.mode = builder.mode;
		this.availability = true;
	}
	
	public LocalDateTime getEndTime() {
		return endTime;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	public int getDuration() {
		return mode.getDuration();
	}
	
	public boolean isAvailiable() {
		return availability;
	}
	
	public User occupation() {
		return user;
	}
	
	public String getWasherId() {
		return washerId;
	}
	
	public Washer enqueue(User user, String type) {
		if(!availability) return this;
		this.user = user;
		mode.washing(Cycle.valueOf(type));
		setStartTime();
		setEndTime();
		availability = false;
		return this;
	}
	
	//Default one, without specifying mode
	public Washer enqueue(User user) {
		if(!availability) return this;
		this.user = user;
		mode.washing();
		setStartTime();
		setEndTime();
		availability = false;
		return this;
	}
	
	private void setStartTime() {
		this.startTime = LocalDateTime.now();
	}
	
	private void setEndTime() {
		this.endTime = this.startTime.plusMinutes(mode.getDuration());
	}
	
	private void clearUser() {
		this.user = null;
	}
	
	private void clearStartTime() {
		this.startTime = null;
	}
	
	private void clearEndTime() {
		this.endTime = null;
	}
	
	public void dequeue() {
		//Need to set timer
		availability = true;
		mode.clear();
		clearUser();
		clearStartTime();
		clearEndTime();
	}
	
	public static class WasherBuilder{
		private String washerId;
		private Mode mode;
		
		public WasherBuilder setWasherId(String washerId){
			this.washerId = washerId;
			return this;
		}
		
		public WasherBuilder setDefaultMode(int time) {
			mode = new Mode(time);
			return this;
		}
		
		public WasherBuilder setDefaultMode(String modeName) {
			mode = new Mode(Cycle.valueOf(modeName));
			return this;
		}
		
		public Washer builder() {
			return new Washer(this);
		}
	}
	
	private static class Mode{
		
		private int duration;
		private int defaultDuration;
		private Cycle mode;
		private Cycle defaultMode;
		
		//Default mode can be any of the predefined mode
		private Mode(Cycle defaultMode) {
			this.defaultMode = defaultMode;
			mode = defaultMode;
		}
		//Default mode can also be a customized mode, e.g. user setup time
		private Mode(int defaultDuration) {
			setDefaultDuration(defaultDuration);
			this.defaultMode = Cycle.valueOf("DEFAULT");
			mode = defaultMode;
		}
		
		private void setDefaultDuration(int defaultDuration) {
			this.defaultDuration = defaultDuration;
		}
		
		private void washing(Cycle mode) {
			this.mode = mode;
			setDuration(mode);
		}
		
		//default one will be used here
		private void washing() {
			setDuration(mode);
		}
		
		private void clear() {
			this.duration = 0;
			//This could be null, or if user has specified one before, that should be the one
			this.mode = defaultMode;
		}
		
		//The duration for each mode is hard coded, we could dynamically set it up if needed
		private void setDuration(Cycle mode) {
			 switch (mode) 
		        { 
		        case DELICATE: 
		            this.duration = 20; //mins
		            break; 
		        case RAPIDWASH: 
		            this.duration = 22; //mins
		            break; 
		        case PERMANENTPRESS: 
		        	this.duration = 25; //mins
		        	break;
		        case HEAVYDUTY: 
		            this.duration = 30; //mins
		            break; 
		        case NORMAL:
		        	this.duration = 27; //mins
		        	break;
		        default: 
		            this.duration =  defaultDuration;
		            break; 
		        } 
		}
		
		private int getDuration() {
			return duration;
		}
	}
}
