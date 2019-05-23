package rpc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import logic.LogicHandler;
import db.DBConnection;
import db.DBConnectionFactory;
import entity.Machine;

/**
 * Servlet implementation class InitiateTask
 */
@WebServlet("/initiatetask")

public class InitiateTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitiateTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// url =  .../initiatetask?user_id=?&machine_id=?&mode=? 
		// if failed, return a JSON array with only one Json Obejct status = {status: "fail"}
		// if success, return a JSON array with two Json Object [status, machine] 

		String userId = request.getParameter("user_id");
		String machineId = request.getParameter("machine_id");
		String mode = request.getParameter("mode");
		// 6 modes: DELICATE, RAPIDWASH, PERMANENTPRESS, NORMAL, HEAVYDUTY, DEFAULT. If not sent in url, use "".
		mode = mode != null? mode : ""; 
		
		DBConnection conn = DBConnectionFactory.getConnection();
		JSONArray array = new JSONArray();
		JSONObject status = new JSONObject();
		 
		try {
			if (!LogicHandler.startJob(machineId, userId, mode)
					) {
				status.put("status", "fail");
				array.put(status);
			} else {
				status.put("status", "success");
				array.put(status);
				Set<Machine> machinesInUse = conn.getTaskStatus(userId);
				for (Machine mach : machinesInUse) {
					mach.printAllInfo();
					JSONObject obj = mach.toJSONObject();
					array.put(obj);
				}
			}

			RpcHelper.writeJsonArray(response, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
