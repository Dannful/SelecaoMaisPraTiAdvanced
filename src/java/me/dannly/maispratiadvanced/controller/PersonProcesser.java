/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package me.dannly.maispratiadvanced.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.dannly.maispratiadvanced.data.repository.PersonDAO;
import me.dannly.maispratiadvanced.domain.model.Person;

/**
 *
 * @author vinix
 */
public class PersonProcesser extends HttpServlet {

    private Long tryParseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private Double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private Short tryParseShort(String s) {
        try {
            return Short.parseShort(s);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private LocalDate tryParseDate(String s) {
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException dateTimeParseException) {
            return null;
        }
    }

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
        final PersonDAO personDAO = new PersonDAO();
        final Integer delete = tryParseInt(request.getParameter("delete"));
        if (delete != null) {
            System.out.println("Sucker! " + delete);
            personDAO.delete(new Person(delete, null));
            response.sendRedirect("index.jsp");
            return;
        }
        final Integer id = tryParseInt(request.getParameter("id"));
        final String name = request.getParameter("name");
        System.out.println(name);
        final Long phone = tryParseLong(request.getParameter("phone").replaceAll("[^0-9]++", ""));
        final LocalDate birth = tryParseDate(request.getParameter("birth"));
        final Short age = tryParseShort(request.getParameter("age"));
        final Double score = tryParseDouble(request.getParameter("score"));

        if (id == null) {
            final Person person = new Person(null, name, System.currentTimeMillis(), birth.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000, System.currentTimeMillis(), phone, age);
            personDAO.insert(score == null ? person : person.toStudent(score));
        } else {
            final Person saved = personDAO.getElementsByFilter("id = ?", id).get(0);
            if (name != null) {
                saved.setName(name);
            }
            if (phone != null) {
                saved.setPhone(phone);
            }
            if (birth != null) {
                saved.setBirth(birth.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
            }
            if (age != null) {
                saved.setAge(age);
            }
            saved.setLastModified(System.currentTimeMillis());
            personDAO.insert(score == null ? saved : saved.toStudent(score));
        }
        response.sendRedirect("index.jsp");
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
        response.sendRedirect("index.jsp");
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
