import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 
 * @author:gang.li
 * @date:2016年5月16日 下午5:09:00 
 * @version 1.0.0 
 */
public class SessionServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("SessionServlet--------service  test");
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.println("<html>");
		pw.println("<head><title>Session Servlet</title></head>");
		pw.println("<body>");
		HttpSession session = req.getSession(true);
		System.out.println("session id:"+session.getId());
		String value = req.getParameter("value");
		pw.println("<br> privious value:"+session.getAttribute("value")+"<br> current value :"+value);
		session.setAttribute("value", value);
		pw.println("<form>");
		pw.println("new value :<input name='value' />");
		pw.println("<input value='submit' type='submit'/>");
		pw.println("</form>");
		pw.println("</body>");
		pw.println("</html>");
		pw.flush();
	}


}
