package logic;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import db.DBConnection;
import db.DBConnectionFactory;
import entity.User;
import entity.Washer;
import washerObject.washerInitialization;

public class LogicHandler {
	private static LogicHandler logicInstance = null;
	private boolean isinitialized;
	private DBConnection connection;
	private Map<String, Washer> washerMap;
	private PriorityQueue<Washer> jobs;
	private Timer t;
	private final long delay;
	private final long period;

	class checkTaskRegularly extends TimerTask {
		public void run() {
			if (!jobs.isEmpty()) {
				if (LocalDateTime.now().isAfter(jobs.peek().getEndTime())) {
					// Only for test purpose
					System.out.println(jobs.peek().getWasherId() + " is done !!!");
					System.out.println("dequeued at " + jobs.peek().getEndTime());
					// dequeue from the priority queue
					Washer cur = jobs.poll();
					// clear the data
					cur.dequeue();
					// update the mysql database
					if (connection.endWashingSQL(cur.getWasherId())) {
						// Probably we can send out the notification here
						// NEED IMPLEMENTATION
						System.out.println("update sql succeed!");
					}
				}
			}
		}
	}

	private LogicHandler() {
		delay = 2000; // 2s
		period = 15000; // 15s
		connection = DBConnectionFactory.getConnection();
		// Only initialize once
		createWasher();
		jobs = new PriorityQueue<>(washerMap.size(), (Washer a, Washer b) -> a.getEndTime().compareTo(b.getEndTime()));
		t = new Timer();
		// check within a certain time period
		t.scheduleAtFixedRate(new checkTaskRegularly(), delay, period);

	}

	private void createWasher() {
		// Initialize the washers
		if (!isinitialized) {
			isinitialized = true;
			washerInitialization init = new washerInitialization();
			washerMap = init.getAllWashers();
			// Insert into the mysql database
			for (String id : washerMap.keySet()) {
				if (!connection.createMachineSQL(id)) {
					System.out.print("washer exists!!!");
				}
			}
		}
	}

	public static boolean startJob(String machineId, String userId, String mode) {
		if (logicInstance == null) {
			logicInstance = new LogicHandler();
		}
		String name = logicInstance.connection.getFullName(userId);
		Washer curWasher = logicInstance.washerMap.get(machineId);
		// Washer is occupied
		if (!curWasher.isAvailiable()) {
			// Will try to update a reservation method
			return false;
		}
		// enqueue
		if (mode.length() != 0) {
			logicInstance.jobs.offer(curWasher.enqueue(new User(name), mode));
		} else {
			logicInstance.jobs.offer(curWasher.enqueue(new User(name)));
		}

		// Update mysql database
		logicInstance.connection.startWashingSQL(machineId, userId, curWasher.getStartTime().toString(),
				curWasher.getEndTime().toString());
		// Task succeed
		return true;
	}

	// test
	public static void main(String[] args) {
		LogicHandler.startJob("washer_3", "1111", "");
		LogicHandler.startJob("washer_2", "1111", "");
	}
}
