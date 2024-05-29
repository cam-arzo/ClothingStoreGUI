/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ClothingStoreGUI.Panels;

import ClothingStoreGUI.Controller;
import ClothingStoreGUI.InteractivePanel;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author annek
 */
public class PanelCheckout extends javax.swing.JPanel implements InteractivePanel {

    /**
     * Creates new form PanelCheckout
     */
    public PanelCheckout(Controller controller) {
        initComponents();
        initConnections(controller);
        ReceiptTable.setEnabled(false);
    }

    public void initConnections(Controller controller) {
         ResetButton.addActionListener(e -> controller.resetButtonClicked());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Message = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ReceiptTable = new javax.swing.JTable();
        TotalPriceLabel = new javax.swing.JLabel();
        Title = new javax.swing.JLabel();
        ResetButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(600, 400));

        Message.setText("Thank you for your purchase! Here is a receipt of your order:");

        ReceiptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Size", "Qty", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ReceiptTable.getTableHeader().setResizingAllowed(false);
        ReceiptTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(ReceiptTable);
        if (ReceiptTable.getColumnModel().getColumnCount() > 0) {
            ReceiptTable.getColumnModel().getColumn(0).setResizable(false);
            ReceiptTable.getColumnModel().getColumn(0).setPreferredWidth(300);
            ReceiptTable.getColumnModel().getColumn(1).setResizable(false);
            ReceiptTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            ReceiptTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            ReceiptTable.getColumnModel().getColumn(3).setResizable(false);
            ReceiptTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        TotalPriceLabel.setText("Total price: $20.00");

        Title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Title.setText("Checkout");

        ResetButton.setText("Restart program");
        ResetButton.setInheritsPopupMenu(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TotalPriceLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Message)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(Title))
                    .addComponent(ResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(Message)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TotalPriceLabel)
                .addGap(18, 18, 18)
                .addComponent(ResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public JTable getReceiptTable() {
        return ReceiptTable;
    }

    public JLabel getTotalPriceLabel() {
        return TotalPriceLabel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Message;
    private javax.swing.JTable ReceiptTable;
    private javax.swing.JButton ResetButton;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel TotalPriceLabel;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
