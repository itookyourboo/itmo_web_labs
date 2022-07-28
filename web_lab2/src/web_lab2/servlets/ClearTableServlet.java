package web_lab2.servlets;

import web_lab2.beans.EntriesBean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ClearTableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        EntriesBean entries = (EntriesBean) req.getSession().getAttribute("entries");
        if (entries == null) entries = new EntriesBean();
        entries.getEntries().clear();
        req.getSession().setAttribute("entries", entries);

        PrintWriter writer = resp.getWriter();
        writer.println(generateEmptyTable());
        writer.close();
    }

    private String generateEmptyTable() {
        return "<table id=\"result-table\">" +
                "<tr class=\"table-header\">" +
                "<th class=\"coords-col\">X</th>" +
                "<th class=\"coords-col\">Y</th>" +
                "<th class=\"coords-col\">R</th>" +
                "<th class=\"time-col\">Время запроса</th>" +
                "<th class=\"time-col\">Время исполнения</th>" +
                "<th class=\"hitres-col\">Попадание</th>" +
                "</tr></table>";
    }
}
