package ua.training.servlet.hospital.controller.utilities;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;

public class PaginationUtility {
    private int page = 1;
    private int recordsPerPage = 5;

    public void setAttributes(HttpServletRequest request, long rows) {
        if (nonNull(request.getParameter("page"))) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (nonNull(request.getParameter("recordsPerPage"))) {
            recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
        }

        int numberOfPages = (int) Math.ceil(rows * 1.0 / recordsPerPage);

        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("page", page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("records", rows);
    }

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return (page - 1) * recordsPerPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

}
