package web_lab2.servlets;

import web_lab2.beans.EntriesBean;
import web_lab2.beans.Entry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        EntriesBean entries = (EntriesBean) req.getSession().getAttribute("entries");
        if (entries == null) entries = new EntriesBean();

        long startTime = System.nanoTime();
        String  xString = req.getParameter("x"),
                yString = req.getParameter("y").replace(",", "."),
                rString = req.getParameter("r");
        boolean isButton = "button".equals(req.getParameter("key"));
        boolean valid = validateXYR(xString, yString, rString, isButton);

        if (valid) {
            double x = Double.parseDouble(xString),
                    y = Double.parseDouble(yString),
                    r = Double.parseDouble(rString);
            boolean isHit = checkHit(x, y, r);

            String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

            double diff = (System.nanoTime() - startTime) / 1000000000d;
            String executionTIme = String.valueOf(diff);

            entries.getEntries().add(0, new Entry(x, y, r, currentTime, executionTIme, isHit));
            req.getSession().setAttribute("entries", entries);
        }

        PrintWriter out = resp.getWriter();
        out.println(generateTable(entries));
        out.close();
    }

    private String generateTable(EntriesBean entries) {
        String header = "<table id=\"result-table\">" +
                "<tr class=\"table-header\">" +
                "<th class=\"coords-col\">X</th>" +
                "<th class=\"coords-col\">Y</th>" +
                "<th class=\"coords-col\">R</th>" +
                "<th class=\"time-col\">Время запроса</th>" +
                "<th class=\"time-col\">Время исполнения</th>" +
                "<th class=\"hitres-col\">Попадание</th>" +
                "</tr>%s</table>";

        StringBuilder rows = new StringBuilder();
        if (entries != null) {
            for (Entry entry : entries.getEntries()) {
                rows.append(generateRowFromElem(entry));
            }
        }

        return String.format(header, rows);
    }

    private String generateRowFromElem(Entry elem) {
        return (elem.isHit() ? "<tr class=\"hit-yes\">" : "<tr class=\"hit-no\">") +
                "<td>" + elem.getX() + "</td>" +
                "<td>" + elem.getY() + "</td>" +
                "<td>" + elem.getR() + "</td>" +
                "<td>" + elem.getCurrentTime() + "</td>" +
                "<td>" + elem.getExecutionTime() + "</td>" +
                "<td>" + (elem.isHit() ? "Да" : "Нет") + "</td>";
    }

    private boolean validateX(String xString, boolean isButton) {
        try {
            if (isButton) {
                int xValue = Integer.parseInt(xString);
                return xValue >= -3 && xValue <= 5;
            } else {
                Double.parseDouble(xString);
                return true;
            }
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateY(String yString) {
        try {
            double yValue = Double.parseDouble(yString);
            return yValue >= -3 && yValue <= 3;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateR(String rString) {
        try {
            double rValue = Double.parseDouble(rString);
            double[] rRange = {1.0, 1.5, 2.0, 2.5, 3.0};
            for (double d: rRange) {
                if (d == rValue) {
                    return true;
                }
            }
            return false;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateXYR(String xString, String yString, String rString, boolean isButton) {
        return validateX(xString, isButton) && validateY(yString) && validateR(rString);
    }

    private boolean checkTriangle(double xValue, double yValue, double rValue) {
        return xValue <= 0 && yValue <= 0 && yValue >= -xValue / 2 - rValue / 2;
    }

    private boolean checkRectangle(double xValue, double yValue, double rValue) {
        return xValue >= 0 && yValue >= 0 && xValue <= rValue && yValue <= rValue;
    }

    private boolean checkCircle(double xValue, double yValue, double rValue) {
        return xValue >= 0 && yValue <= 0 && xValue * xValue + yValue * yValue <= rValue * rValue;
    }

    private boolean checkHit(double xValue, double yValue, double rValue) {
        return checkTriangle(xValue, yValue, rValue) || checkRectangle(xValue, yValue, rValue) ||
                checkCircle(xValue, yValue, rValue);
    }
}
