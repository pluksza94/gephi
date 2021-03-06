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

package pl.edu.wat.wcy.randomwalk;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//pl.edu.wat.wcy.randomwalk//RandomWalkPanel//PL",
        autostore = false
)
@TopComponent.Description(
        preferredID = "RandomWalkPanelTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "pl.edu.wat.wcy.randomwalk.RandomWalkPanelTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_RandomWalkPanelAction",
        preferredID = "RandomWalkPanelTopComponent"
)
@Messages({
    "CTL_RandomWalkPanelAction=RandomWalkPanel",
    "CTL_RandomWalkPanelTopComponent=RandomWalkPanel Window"
})
public final class RandomWalkPanelTopComponent extends TopComponent {

    RandomWalk randomWalk;
    int numberOfSteps;
    List<Map<String, Double>> listOfProbabilities;

    public RandomWalkPanelTopComponent() {
        initComponents();
        setName("Spacer losowy");
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nextStepButton = new javax.swing.JButton();
        startNodeField = new javax.swing.JTextField();
        startNodeLabel = new javax.swing.JLabel();
        numberOfStepsLabel = new javax.swing.JLabel();
        prevButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        listOfProbabilitiesScrollPane = new javax.swing.JScrollPane();
        listOfProbabilitiesLabel = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(nextStepButton, org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.nextStepButton.text")); // NOI18N
        nextStepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextStepButtonActionPerformed(evt);
            }
        });

        startNodeField.setText(org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.startNodeField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(startNodeLabel, org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.startNodeLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(numberOfStepsLabel, org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.numberOfStepsLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(prevButton, org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.prevButton.text")); // NOI18N
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        listOfProbabilitiesLabel.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        listOfProbabilitiesLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(listOfProbabilitiesLabel, org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.listOfProbabilitiesLabel.text")); // NOI18N
        listOfProbabilitiesLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        listOfProbabilitiesScrollPane.setViewportView(listOfProbabilitiesLabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listOfProbabilitiesScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(prevButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nextStepButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(numberOfStepsLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(startNodeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startNodeField))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startNodeLabel)
                    .addComponent(startNodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevButton)
                    .addComponent(nextStepButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfStepsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listOfProbabilitiesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(RandomWalkPanelTopComponent.class, "RandomWalkPanelTopComponent.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void nextStepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextStepButtonActionPerformed
        try {
            if (randomWalk == null) {
                randomWalk = new RandomWalk(startNodeField.getText());

                listOfProbabilities = new LinkedList<>();
                numberOfSteps = 0;
            }
            numberOfSteps++;

            startNodeField.setEnabled(false);

            if (listOfProbabilities.size() < numberOfSteps) {
                Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Step: {0}", new Object[]{numberOfSteps});
                listOfProbabilities.add(randomWalk.nextStep());
            }

            numberOfStepsLabel.setText("Liczba wykonanych kroków: " + numberOfSteps);

            Map<String, Double> probabilityMap = listOfProbabilities.get(numberOfSteps - 1);

            String probabilityInfo = "";

            for (Map.Entry<String, Double> m : probabilityMap.entrySet()) {
                probabilityInfo += "[" + m.getKey() + "]: " + m.getValue() + "<br>";
            }
            listOfProbabilitiesLabel.setText("<html>" + probabilityInfo + "</html>");

            randomWalk.setColors(probabilityMap);
        } catch (UnsupportedOperationException e) {
            numberOfStepsLabel.setText("Nie ma takiego wierzchołka początkowego!");
        }

    }//GEN-LAST:event_nextStepButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        startNodeField.setEnabled(true);
        randomWalk = null;
        numberOfSteps = 0;
        numberOfStepsLabel.setText(" ");
        listOfProbabilitiesLabel.setText("");
        listOfProbabilities.clear();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        // TODO add your handling code here:
        if (numberOfSteps > 1) {
            numberOfSteps--;

            numberOfStepsLabel.setText("Liczba wykonanych kroków: " + numberOfSteps);

            Map<String, Double> probabilityMap = listOfProbabilities.get(numberOfSteps - 1);

            String probabilityInfo = "";

            for (Map.Entry<String, Double> m : probabilityMap.entrySet()) {
                probabilityInfo += "[" + m.getKey() + "]: " + m.getValue() + "<br>";
            }
            listOfProbabilitiesLabel.setText("<html>" + probabilityInfo + "</html>");

            randomWalk.setColors(probabilityMap);
        } else if (numberOfSteps == 1) {
            numberOfStepsLabel.setText("Cofnięto już do początku!");
        } else{
            numberOfStepsLabel.setText("Najpierw wykonaj następny krok!");
        }
    }//GEN-LAST:event_prevButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel listOfProbabilitiesLabel;
    private javax.swing.JScrollPane listOfProbabilitiesScrollPane;
    private javax.swing.JButton nextStepButton;
    private javax.swing.JLabel numberOfStepsLabel;
    private javax.swing.JButton prevButton;
    private javax.swing.JTextField startNodeField;
    private javax.swing.JLabel startNodeLabel;
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
