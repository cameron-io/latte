package io.netstacker.latte.domain.models;

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
@Table(name = "social")
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_id")
    private Long id;
    private String youtube;
    private String twitter;
    private String facebook;
    private String linkedin;
    private String instagram;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void setId(Long id) {
        this.id = id;
    }
    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getFacebook() {
        return facebook;
    }

    public Long getId() {
        return id;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getLinkedin() {
        return linkedin;
    }
    
    public Profile getProfile() {
        return profile;
    }
    
    public String getTwitter() {
        return twitter;
    }
    
    public String getYoutube() {
        return youtube;
    }
}
