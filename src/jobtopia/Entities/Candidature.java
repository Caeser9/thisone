/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtopia.Entities;

/**
 *
 * @author HP
 */
public class Candidature {

    private int id;
    private String etatID, offreTitle, offreDescription, freelancerName, freelancerLName, freelancerEmail, freelancerProfession, offreCategorie;

    public Candidature() {

    }

    public Candidature(int id) {
        this.id = id;
    }

    public Candidature(String offreTitle) {
        this.offreTitle = offreTitle;
    }

    public Candidature(int id, String etatID, String offreTitle, String offreDescription, String offString) {

        this.id = id;
        this.etatID = etatID;
        this.offreTitle = offreTitle;
        this.offreDescription = offreDescription;
        this.freelancerName = offreCategorie;

    }

    public void setOffreCategorie(String offreCategorie) {
        this.offreCategorie = offreCategorie;
    }

    public String getOffreCategorie() {
        return offreCategorie;
    }

    public Candidature(int id, String etatID, String offreTitle, String offreDescription, String freelancerName, String freelancerLName, String freelancerEmail, String freelancerProfession) {
        this.id = id;
        this.etatID = etatID;
        this.offreTitle = offreTitle;
        this.offreDescription = offreDescription;
        this.freelancerName = freelancerName;
        this.freelancerLName = freelancerLName;
        this.freelancerEmail = freelancerEmail;
        this.freelancerProfession = freelancerProfession;
    }

    public Candidature(String etatID, String offreID, String freelancerID) {
        this.etatID = etatID;
        this.offreTitle = offreID;
        this.freelancerName = freelancerID;

    }

    public int getId() {
        return id;
    }

    public String getEtatID() {
        return etatID;
    }

    public String getOffreTitle() {
        return offreTitle;
    }

    public String getOffreDescription() {
        return offreDescription;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public String getFreelancerLName() {
        return freelancerLName;
    }

    public String getFreelancerEmail() {
        return freelancerEmail;
    }

    public String getFreelancerProfession() {
        return freelancerProfession;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEtatID(String etatID) {
        this.etatID = etatID;
    }

    public void setOffreTitle(String offreTitle) {
        this.offreTitle = offreTitle;
    }

    public void setOffreDescription(String offreDescription) {
        this.offreDescription = offreDescription;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public void setFreelancerLName(String freelancerLName) {
        this.freelancerLName = freelancerLName;
    }

    public void setFreelancerEmail(String freelancerEmail) {
        this.freelancerEmail = freelancerEmail;
    }

    public void setFreelancerProfession(String freelancerProfession) {
        this.freelancerProfession = freelancerProfession;
    }

    @Override
    public String toString() {
        return "Candidature{" + "id=" + id + ", etat=" + etatID + ", offreDescription=" + offreTitle + ", freeelancer=" + freelancerName + '}';
    }

    public String showID() {
        return "Candidature numero: " + id;
    }

}
