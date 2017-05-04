package pl.edu.wat.wcy.rrfgpplugin;

import javax.swing.JPanel;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = RRfGPUI.class)
public class RRfGPUIImpl implements RRfGPUI {

    private RRfGPPanel panel;
    private RRfGP rrfgp;

    public RRfGPUIImpl() {

    }

    @Override
    public JPanel getPanel() {
        if (panel == null) {
            panel = new RRfGPPanel();
        }
        return panel;
    }

    @Override
    public void setup(Generator generator) {
        this.rrfgp = (RRfGP) generator;

        //Set UI
        if (panel == null) {
            panel = new RRfGPPanel();
        }

        panel.nodeField.setText(String.valueOf(rrfgp.getNumberOfNodes()));
        panel.degreeField.setText(String.valueOf(rrfgp.getDegree()));
        panel.numberOfStepsField.setText(String.valueOf(rrfgp.getNumberOfSteps()));

    }

    @Override
    public void unsetup() {
        rrfgp.setNumberOfNodes(Integer.parseInt(panel.nodeField.getText()));
        rrfgp.setDegree(Integer.parseInt(panel.degreeField.getText()));
        rrfgp.setNumberOfSteps(Integer.parseInt(panel.numberOfStepsField.getText()));
        
        panel = null;
    }

}
