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

package pl.edu.wat.wcy.rfgpplugin;

import javax.swing.JLabel;
import javax.swing.JTextField;
import org.gephi.lib.validation.PositiveNumberValidator;
import org.netbeans.validation.api.builtin.Validators;
import org.netbeans.validation.api.ui.ValidationGroup;
import org.netbeans.validation.api.ui.ValidationPanel;

public class RfGPPanel extends javax.swing.JPanel {

    /**
     * Creates new form RfGPPanel
     */
    public RfGPPanel() {
        initComponents();
    }
    
    public static ValidationPanel createValidationPanel(RfGPPanel innerPanel) {
        ValidationPanel validationPanel = new ValidationPanel();
        if (innerPanel == null) {
            innerPanel = new RfGPPanel();
        }
        validationPanel.setInnerComponent(innerPanel);

        ValidationGroup group = validationPanel.getValidationGroup();

        group.add(innerPanel.nodeField, Validators.REQUIRE_NON_EMPTY_STRING,
                new NumberOfNodesValidator());

        group.add(innerPanel.degreeField, Validators.REQUIRE_NON_EMPTY_STRING,
                new GraphDegreeValidator(innerPanel));

        return validationPanel;
    }

    public JTextField getDegreeField() {
        return degreeField;
    }

    public void setDegreeField(JTextField degreeField) {
        this.degreeField = degreeField;
    }

    public JLabel getDegreeLabel() {
        return degreeLabel;
    }

    public void setDegreeLabel(JLabel degreeLabel) {
        this.degreeLabel = degreeLabel;
    }

    public JTextField getNodeField() {
        return nodeField;
    }

    public void setNodeField(JTextField nodeField) {
        this.nodeField = nodeField;
    }

    public JLabel getNodeLabel() {
        return nodeLabel;
    }

    public void setNodeLabel(JLabel nodeLabel) {
        this.nodeLabel = nodeLabel;
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nodeLabel = new javax.swing.JLabel();
        degreeLabel = new javax.swing.JLabel();
        nodeField = new javax.swing.JTextField();
        degreeField = new javax.swing.JTextField();

        nodeLabel.setText(org.openide.util.NbBundle.getMessage(RfGPPanel.class, "RfGPPanel.nodeLabel.text")); // NOI18N

        degreeLabel.setText(org.openide.util.NbBundle.getMessage(RfGPPanel.class, "RfGPPanel.degreeLabel.text")); // NOI18N

        nodeField.setText(org.openide.util.NbBundle.getMessage(RfGPPanel.class, "RfGPPanel.nodeField.text")); // NOI18N

        degreeField.setText(org.openide.util.NbBundle.getMessage(RfGPPanel.class, "RfGPPanel.degreeField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nodeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(degreeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(degreeField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nodeLabel)
                    .addComponent(nodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(degreeLabel)
                    .addComponent(degreeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JTextField degreeField;
    private javax.swing.JLabel degreeLabel;
    protected javax.swing.JTextField nodeField;
    private javax.swing.JLabel nodeLabel;
    // End of variables declaration//GEN-END:variables
}