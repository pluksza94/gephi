/*
 * Copyright 2008-2017 Gephi
 * Authors : Paweł Łuksza
 * 
 * This file is part of Gephi.
 *
 * Gephi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gephi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */

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

        return RRfGPPanel.createValidationPanel(panel);
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
