package web_lab2.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntriesBean implements Serializable {
    private List<Entry> entries;

    public EntriesBean() {
        this(new ArrayList<Entry>());
    }

    public EntriesBean(List<Entry> entries) {
        this.entries = entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntriesBean that = (EntriesBean) o;

        return entries != null ? entries.equals(that.entries) : that.entries == null;
    }

    @Override
    public int hashCode() {
        return entries != null ? entries.hashCode() : 0;
    }
}
