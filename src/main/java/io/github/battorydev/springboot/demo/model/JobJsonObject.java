package io.github.battorydev.springboot.demo.model;

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

    private String job;

    private String title;

    private String yearsAtEmployer;

    private String yearsOfExp;

    private String salary;

    /**
     * The currency of salary. Default to $ (CAD?).
     */
    //@JsonIgnore
    private String salaryCurrency = "$";

    private String signingBonus;

    private String annualBonus;

    private String annualStock;

    private String gender;

    private String additional;

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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public void setSigningBonus(String signingBonus) {
        this.signingBonus = signingBonus;
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

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
