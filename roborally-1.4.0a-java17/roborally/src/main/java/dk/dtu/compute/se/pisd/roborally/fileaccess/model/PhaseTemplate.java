package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

/**
 * @author Benjamin
 * @param PhaseTemplate
 * this class is a template for the phase and to save the phase in the json file
 */

public class PhaseTemplate {
    private String phase;

    public PhaseTemplate() {
    }

    public PhaseTemplate(String phase) {
        this.phase = phase;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
