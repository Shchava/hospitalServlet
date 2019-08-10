package ua.training.servlet.hospital.entity.dto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Page<E> {
    List<E> content;
    int pageNumber;
    int pageSize;
    int numberOfElements;
    long totalElements;
    boolean first;
    boolean last;

    public Page() {
    }

    public Page(List<E> content, int pageNumber, int pageSize, int numberOfElements, long totalElements, boolean first, boolean last) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.numberOfElements = numberOfElements;
        this.totalElements = totalElements;
        this.first = first;
        this.last = last;
    }

    public List<E> getContent() {
        return content;
    }

    public void setContent(List<E> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
