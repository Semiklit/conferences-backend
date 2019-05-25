package responses;

import com.google.gson.annotations.SerializedName;
import model.Report;

import java.util.List;

public class ReportsResponse extends Response {

    @SerializedName("ReportsList")
    private List<Report> reports;

    @Override
    public ReportsResponse setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public ReportsResponse setReports(List<Report> reports) {
        this.reports = reports;
        return this;
    }

}
