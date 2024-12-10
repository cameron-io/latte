package io.netstacker.latte.domain.models;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    private String company;
    private String website;
    private String location;
    private String status;
    private List<String> skills;
    private String bio;
    private String githubusername;
    private final Date created_at = new Date(System.currentTimeMillis());

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id")
    private Experience experience;
    
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "education_id")
    private Education education;
    
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "social_id")
    private Social social;

    public Profile() {}

    public Profile(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setGithubusername(String githubusername) {
        this.githubusername = githubusername;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public void setSocial(Social social) {
        this.social = social;
    }

    public Long getId() {
        return id;
    }
    public Account getAccount() {
        return account;
    }

    public String getBio() {
        return bio;
    }

    public String getCompany() {
        return company;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Education getEducation() {
        return education;
    }
    
    public Experience getExperience() {
        return experience;
    }
    
    public String getGithubusername() {
        return githubusername;
    }
    
    public String getLocation() {
        return location;
    }
    
    public List<String> getSkills() {
        return skills;
    }
    
    public Social getSocial() {
        return social;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getWebsite() {
        return website;
    }
}
