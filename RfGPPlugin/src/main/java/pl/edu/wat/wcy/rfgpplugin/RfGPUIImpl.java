package pl.edu.wat.wcy.rfgpplugin;

import javax.swing.JPanel;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = RfGPUI.class)
public class RfGPUIImpl implements RfGPUI {

    private RfGPPanel panel;
    private RfGP rfgp;

    public RfGPUIImpl() {

    }

    @Override
    public JPanel getPanel() {
        if (panel == null) {
            panel = new RfGPPanel();
        }
        return panel;
    }

    @Override
    public void setup(Generator generator) {
        this.rfgp = (RfGP) generator;

        //Set UI
        if (panel == null) {
            panel = new RfGPPanel();
        }

        panel.nodeField.setText(String.valueOf(rfgp.getNumberOfNodes()));

        panel.degreeField.setText(String.valueOf(rfgp.getDegree()));

    }

    @Override
    public void unsetup() {
        rfgp.setNumberOfNodes(Integer.parseInt(panel.nodeField.getText()));
        rfgp.setDegree(Integer.parseInt(panel.degreeField.getText()));
        panel = null;
    }

}
