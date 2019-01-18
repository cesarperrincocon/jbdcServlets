/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author cperrinc
 */
@WebServlet(name = "ShowClientState", urlPatterns = {"/ShowClientState"})
public class ShowClientState extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String state = request.getParameter("state");
        DAO dao = new DAO(DataSourceFactory.getDataSource());
        try (PrintWriter out = response.getWriter()) {

            
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<style>");
                out.println("table, th, td {  border: 1px solid black;}");
                out.println("</style>");    
                out.println("<title>Clients d'un état</title>");
                out.println("</head>");
                out.println("<body>");
                try {
                if (state == null) {
                    throw new Exception("La paramètre customerID n'a pas été transmis");
                }
                List<CustomerEntity> myCustomers = dao.customersInState(state);
                if (myCustomers == null) {
                    throw new Exception("Pas de clients");
                }
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Name</th>");
                out.println("<th>Address</th>");
                out.println("</tr>");
                for(CustomerEntity ce : myCustomers) {
                    out.println("<tr>");
                    out.println("<td>" + ce.getCustomerId() + "</td>");
                    out.println("<td>" + ce.getName() + "</td>");
                    out.println("<td>" + ce.getAddressLine1() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception ex) {
                Logger.getLogger("servlet").log(Level.SEVERE, "Erreur de traitement", ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
