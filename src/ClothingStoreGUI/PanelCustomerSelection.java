package ClothingStoreGUI;

/**
 *
 * @author Cam
 */
public class PanelCustomerSelection extends javax.swing.JPanel implements InteractivePanel {

    /**
     * Creates new form PanelCustomerSelection
     */
    public PanelCustomerSelection(Controller controller) {
        initComponents();
        initConnections(controller);
    }
    
    public void initConnections(Controller controller) {
        // CustomerViewButton.addActionListener(e -> controller.customerViewButtonClicked());
        // !! add other connections in here
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Title = new javax.swing.JLabel();
        ProductNameLabel = new javax.swing.JLabel();
        BackButton = new javax.swing.JButton();
        SizeLabel = new javax.swing.JLabel();
        QtyLabel = new javax.swing.JLabel();
        SizeDropdown = new javax.swing.JComboBox<>();
        QtyPicker = new javax.swing.JSpinner();
        AddToCartButton = new javax.swing.JButton();

        Title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Title.setText("Customise your order");

        ProductNameLabel.setText("Placeholder product name - $20.00");

        BackButton.setText("Back");
        BackButton.setInheritsPopupMenu(true);
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        SizeLabel.setText("Size:");

        QtyLabel.setText("Quantity:");

        SizeDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        QtyPicker.setName(""); // NOI18N
        QtyPicker.setValue(1);

        AddToCartButton.setText("Add to cart");
        AddToCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToCartButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(187, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(AddToCartButton)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(QtyLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(QtyPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(SizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SizeDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ProductNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Title)
                            .addGap(18, 18, 18)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BackButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(BackButton)
                .addGap(18, 18, 18)
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(ProductNameLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SizeLabel)
                    .addComponent(SizeDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(QtyPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(QtyLabel))
                .addGap(18, 18, 18)
                .addComponent(AddToCartButton)
                .addContainerGap(105, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void AddToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToCartButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddToCartButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddToCartButton;
    private javax.swing.JButton BackButton;
    private javax.swing.JLabel ProductNameLabel;
    private javax.swing.JLabel QtyLabel;
    private javax.swing.JSpinner QtyPicker;
    private javax.swing.JComboBox<String> SizeDropdown;
    private javax.swing.JLabel SizeLabel;
    private javax.swing.JLabel Title;
    // End of variables declaration//GEN-END:variables
}
