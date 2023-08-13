package io.github.battorydev.springboot.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The model for keeping data from JSON file.
 * Each instance represents one job data record.
 */
@JsonFilter("AttributeFilter.ID")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobJsonObject {
    private String timestamp;

    private String Employer;

    private String location;

    @JsonAlias("Job Title")
    private String title;

    @JsonAlias("Years at Employer")
    private String yearsAtEmployer;

    @JsonAlias("Years of Experience")
    private String yearsOfExp;

    private String salary;

    /**
     * The currency of salary. Default to $ (CAD?).
     */
    //@JsonIgnore
    private String salaryCurrency = "$";

    @JsonAlias("Signing Bonus")
    private String signingBonus;

    @JsonAlias("Annual Bonus")

    private String annualBonus;

    @JsonAlias("Annual Stock Value/Bonus")

    private String annualStock;

    private String gender;

    @JsonAlias("Additional Comments")
    private String comment;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmployer() {
        return Employer;
    }

    public void setEmployer(String employer) {
        Employer = employer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYearsAtEmployer() {
        return yearsAtEmployer;
    }

    public void setYearsAtEmployer(String yearsAtEmployer) {
        this.yearsAtEmployer = yearsAtEmployer;
    }

    public String getYearsOfExp() {
        return yearsOfExp;
    }

    public void setYearsOfExp(String yearsOfExp) {
        this.yearsOfExp = yearsOfExp;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSigningBonus() {
        return signingBonus;
    }

    public void setSigningBonus(String signingBonus) {
        this.signingBonus = signingBonus;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public String getAnnualBonus() {
        return annualBonus;
    }

    public void setAnnualBonus(String annualBonus) {
        this.annualBonus = annualBonus;
    }

    public String getAnnualStock() {
        return annualStock;
    }

    public void setAnnualStock(String annualStock) {
        this.annualStock = annualStock;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonIgnore
    public Object getByName(String name) {
        switch (name) {
            case "yearsOfExp":
                return getYearsOfExp();
            case "timestamp":
                return getTimestamp();
            case "employer":
                return getEmployer();
            case "location":
                return getLocation();
            case "title":
                return getTitle();
            case "yearsOfEmployer":
                return getYearsAtEmployer();
            case "salary":
                return getSalary();
            case "signingBonus":
                return getSigningBonus();
            case "annualBonus":
                return getAnnualBonus();
            case "annualStock":
                return getAnnualStock();
            case "gender":
                return getGender();
            case "comment":
                return getComment();
            default:
                return null;
        }
    }
}
